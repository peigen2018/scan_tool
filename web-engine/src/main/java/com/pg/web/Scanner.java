package com.pg.web;

import com.pg.web.rule.RuleHandler;
import com.pg.web.rule.RuleInfo;
import com.pg.web.rule.RuleLoader;
import com.pg.web.util.HttpUtils;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class Scanner {


    public static RuleHandler initScanQueue(CloseableHttpClient closeableHttpClient) throws Exception {

        List<RuleInfo> ruleInfos = new RuleLoader().loadByFile("E:\\\\src\\\\my\\\\scan_tool\\\\web-engine\\\\src\\\\main\\\\resources\\\\rule.json");


        RuleHandler ruleHandler = new RuleHandler(ruleInfos.get(0), closeableHttpClient);

        RuleHandler next;
        for (int i = 1; i < ruleInfos.size(); i++) {
            next = new RuleHandler(ruleInfos.get(i), closeableHttpClient);
            ruleHandler.setNext(next);
        }
        return ruleHandler;

    }

    public static void main(String[] args) throws Exception {

        try (CloseableHttpClient closeableHttpClient = HttpUtils.wrapClient(true)) {
            RuleHandler ruleHandler = initScanQueue(closeableHttpClient);
            ruleHandler.check();
            ruleHandler.printResult();
        }
    }
}
