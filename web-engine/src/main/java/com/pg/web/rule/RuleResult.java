package com.pg.web.rule;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@ToString
@SuperBuilder
public class RuleResult {
    private boolean pass;

    private List<ResultInfo> checkResult;

    @ToString
    @SuperBuilder
    public static class ResultInfo{
        private boolean pass;
        private String content;
    }
}
