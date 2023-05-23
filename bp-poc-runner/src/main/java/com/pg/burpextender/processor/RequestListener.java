package com.pg.burpextender.processor;

import burp.IBurpExtenderCallbacks;
import burp.IInterceptedProxyMessage;
import burp.IProxyListener;
import com.pg.burpextender.view.MainView;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;

public class RequestListener implements IProxyListener {

    private MainView view;
    private IBurpExtenderCallbacks callbacks;

    public RequestListener(MainView view, IBurpExtenderCallbacks callbacks) {
        this.view = view;
        this.callbacks = callbacks;
    }


    @Override
    public void processProxyMessage(boolean messageIsRequest, IInterceptedProxyMessage message) {


        if (!ScanHolder.getScanningFlag()) {
            return;
        }
        URL url = callbacks.getHelpers().analyzeRequest(message.getMessageInfo()).getUrl();

        String[] subPaths = url.getPath().split("/");

        StringBuilder subPath = new StringBuilder();

        for (String path : subPaths) {
            if (StringUtils.isEmpty(path)) {
                continue;
            }
            //逐层添加路径
            subPath.append("/")
                    .append(path);
            ScanHolder.putUrl(url.getProtocol() + "://" + url.getHost() + ":" + url.getPort() + subPath);
        }
    }
}
