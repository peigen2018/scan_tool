package com.pg.web.util;

import com.pg.web.common.exception.UnsupportedTypeException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CompareUtils {
    public static boolean compareNumber(String type, List<String> expects, String actual) {



        String lowerType = type.toLowerCase();
        String expect = expects.get(0);
        int compare = Double.valueOf(expect).compareTo(Double.valueOf(actual));

        if(StringUtils.isEmpty(expect) || StringUtils.isEmpty(actual)){
            return false;
        }

        switch (lowerType) {
            case "be":
                return Double.valueOf(actual).compareTo(Double.valueOf(expects.get(0))) >= 0
                        && Double.valueOf(actual).compareTo(Double.valueOf(expects.get(1))) <= 0;
            case "eq":
                return compare == 0;
            case "gt":
                return compare < 0;
            case "lt":
                return compare > 0;
            case "ge":
                return compare >= 0;
            case "le":
                return compare <= 0;
            default:
                throw new UnsupportedTypeException("un support type:"+lowerType);
        }
    }



    public static boolean compareString(String type, String expect, String actual) {
        String lowerType = type.toLowerCase();
        if(StringUtils.isEmpty(expect) || StringUtils.isEmpty(actual)){
            return false;
        }


        switch (lowerType) {
            case "eq":
                return actual.equals(expect);
            case "reg":
                return actual.matches(expect);
            default:
                throw new UnsupportedTypeException("un support type:" + lowerType);
        }
    }
}
