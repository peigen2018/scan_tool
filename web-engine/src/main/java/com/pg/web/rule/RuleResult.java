package com.pg.web.rule;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@ToString
@SuperBuilder
@Getter
public class RuleResult {
    private boolean pass;

    private List<ResultInfo> checkResult;

    @ToString
    @SuperBuilder
    @Getter
    public static class ResultInfo{
        private boolean pass;
        private String content;
    }
}
