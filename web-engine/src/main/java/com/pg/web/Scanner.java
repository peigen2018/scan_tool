package com.pg.web;

import com.pg.web.rule.RuleHandler;

import java.text.MessageFormat;

public class Scanner {

    public static String mkUrl(String protocol,String ip,String path){
        return MessageFormat.format("{0}://{1}/{2}",protocol,ip,path);
    }

    public static RuleHandler initScanQueue(){

        RuleHandler ruleHandler = new RuleHandler();


        ruleHandler.setNext(new RuleHandler());
        return  ruleHandler;
    }
    public static void main(String[] args) throws Exception {


        RuleHandler ruleHandler = initScanQueue();

        ruleHandler.check();

        ruleHandler.printResult();

//        HttpGet request = new HttpGet(mkUrl("https","10.92.2.196",""));
//
//
//        CloseableHttpClient closeableHttpClient = HttpUtils.wrapClient(true);
//
//        int statusCode = closeableHttpClient.execute(request).getStatusLine().getStatusCode();
//
//        System.out.println(statusCode);
//
//
//        HttpGet request2 = new HttpGet("https://10.92.2.196/authn/base/LOCAL_UP");
//        statusCode = closeableHttpClient.execute(request2).getStatusLine().getStatusCode();
//
//        System.out.println(statusCode);
//
//        closeableHttpClient.close();
//
//        Queue<RuleHandler> queue = new LinkedBlockingQueue();
//
//        queue.add(new RuleHandler());



    }
}
