package com.pg.burpextender.processor;

import com.pg.burpextender.util.HttpUtils;
import com.pg.burpextender.util.ThreadUtils;
import com.pg.burpextender.view.MainView;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScanHolder {

    private final static ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final static ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<>();

    private final static Set<String> hashUrl = new HashSet<>();
    private static Thread scanThread = null;
    private static AtomicBoolean scanningFlag = new AtomicBoolean();

    private static final String[] checkPathList = new String[]{
            "/actuator", "/actuator/info", "/actuator/env", "/actuator/conditions",
            "/actuator/configprops", "/actuator/loggers", "/actuator/threaddum", "/actuator/mappings"
    };

    public static void putUrl(String url) {

        if (!scanningFlag.get()) {
            return;
        }
        //filter the repeat url
        if (hashUrl.add(Md5Crypt.md5Crypt(url.getBytes()))) {
            urlQueue.add(url);
        }
    }


    public static void start(MainView mainView) {
        scanningFlag.set(true);

        scanThread = new Thread(() -> {

            while (scanningFlag.get()) {

                ThreadUtils.sleep(100);

                if (urlQueue.isEmpty()) {
                    continue;
                }
                executorService.submit(new SpringScanner(mainView));
            }
        });


        scanThread.start();
    }


    public static void stop() {
        scanningFlag.set(false);
        hashUrl.clear();

        scanThread.interrupt();
    }


    private static class SpringScanner implements Runnable {
        private final MainView mainView;


        public SpringScanner(MainView mainView) {
            this.mainView = mainView;
        }

        @Override
        public void run() {
            String url = urlQueue.poll();
            if (url == null) {
                return;
            }


            try (CloseableHttpClient client = HttpUtils.wrapClient(url.startsWith("https"))) {
                for (String path : checkPathList) {
                    String checkUrl = url + path;

                    HttpGet request = new HttpGet(checkUrl);

                    try (CloseableHttpResponse response = client.execute(request)) {
                        int statusCode = response.getStatusLine().getStatusCode();
                        if (statusCode == HttpStatus.SC_OK) {
                            mainView.writeMsg("status:" + statusCode + " " + checkUrl);
                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean getScanningFlag() {
        return scanningFlag.get();
    }
}
