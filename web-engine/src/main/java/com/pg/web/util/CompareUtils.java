package com.pg.web.util;

import java.util.List;

public class CompareUtils {
    public static boolean compareNumber(String type, List<String> expects, String actual) {

//        EQ|GT|LT|GE|LE|BE|REG

        if ("BE".equalsIgnoreCase(type)) {
            return Double.valueOf(actual).compareTo(new Double(expects.get(0))) >= 0
                    && Double.valueOf(actual).compareTo(new Double(expects.get(0))) <= 0;
        }

        String expect = expects.get(0);
        int compare = Double.valueOf(expect).compareTo(Double.valueOf(actual));
        if ("EQ".equalsIgnoreCase(type)) {
            return compare == 0;
        }

        if ("GT".equalsIgnoreCase(type)) {
            return compare > 0;
        }
        if ("LT".equalsIgnoreCase(type)) {
            return compare < 0;
        }
        if ("GE".equalsIgnoreCase(type)) {
            return compare <= 0;
        }
        if ("LE".equalsIgnoreCase(type)) {
            return compare >= 0;
        }


        return false;
    }
}