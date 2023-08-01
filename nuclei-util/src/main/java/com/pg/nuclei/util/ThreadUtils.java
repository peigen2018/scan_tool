package com.pg.nuclei.util;

public class ThreadUtils {

    public static void sleep(int time){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
