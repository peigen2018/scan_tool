package com.pg.web.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CompareUtilsTests {
    @Test
    public void test_compare_utils(){

        List<String> ex = new ArrayList<>();
        ex.add("200");

        Assertions.assertTrue(CompareUtils.compareNumber("eq",ex,"200"));
        Assertions.assertFalse(CompareUtils.compareNumber("eq",ex,"201"));
    }


    @Test
    public void test_compare_utils_bt(){

        List<String> ex = new ArrayList<>();
        ex.add("200");
        ex.add("202");

        Assertions.assertTrue(CompareUtils.compareNumber("be",ex,"201"));
    }
}
