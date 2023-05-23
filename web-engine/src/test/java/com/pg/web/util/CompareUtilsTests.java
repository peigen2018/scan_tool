package com.pg.web.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CompareUtilsTests {
    @Test
    public void test(){

        List<String> ex = new ArrayList<>();
        ex.add("200");

        Assertions.assertTrue(CompareUtils.compareNumber("eq",ex,"200"));
        Assertions.assertFalse(CompareUtils.compareNumber("eq",ex,"201"));
    }
}
