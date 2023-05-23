package com.pg.web.rule;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RuleLoader {
    public List<RuleInfo> loadByFile(String jsonFilePath) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get(jsonFilePath));


        String content = new String(bytes, StandardCharsets.UTF_8);


        List<RuleInfo> ruleInfos = JSON.parseArray(content, RuleInfo.class);

        System.out.println(ruleInfos);
        return ruleInfos;
    }


    public static void main(String[] args) throws IOException {

    }
}
