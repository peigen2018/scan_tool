package com.pg.web.rule;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class ResultInfo {
    private Boolean pass;
    private Integer statusCode;
    private String responseBody;
}
