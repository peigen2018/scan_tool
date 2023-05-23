package com.pg.web.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

@AllArgsConstructor
@Getter
public enum MethodEnum {
    GET("get"), POST("post"), PUT("put"), DELETE("delete");
    private String key;

    public static MethodEnum from(String name) {

        for (MethodEnum methodEnum : values()) {
            if (methodEnum.getKey().equals(name.toLowerCase())) {
                return methodEnum;
            }
        }
        return GET;
    }
}
