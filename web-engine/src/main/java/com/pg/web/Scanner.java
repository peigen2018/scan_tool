package com.pg.web;

import com.pg.web.rule.RuleHandler;
import com.pg.web.rule.RuleInfo;
import com.pg.web.rule.RuleLoader;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class Scanner {


    public static RuleHandler initScanQueue() throws IOException {

        List<RuleInfo> ruleInfos = new RuleLoader().loadByFile("E:\\project\\myproject\\scan_tool\\web-engine\\src\\main\\resources\\rule.json");

        RuleHandler ruleHandler = new RuleHandler(ruleInfos.get(0));

        RuleHandler next;
        for (int i = 1; i < ruleInfos.size(); i++) {
            next = new RuleHandler(ruleInfos.get(i));
            ruleHandler.setNext(next);
        }

        return ruleHandler;
    }

    public static void main(String[] args) throws Exception {
        RuleHandler ruleHandler = initScanQueue();
        ruleHandler.check();
        ruleHandler.printResult();

    }
}
