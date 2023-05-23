package com.pg.web.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CompareUtilsTests {


    @Test
    public void textCompareNum() {
        List<String> strings = new ArrayList<>();
        strings.add("200");
        strings.add("210");
        Assertions.assertTrue(CompareUtils.compareNumber("eq", strings, "200"));

    }
}
