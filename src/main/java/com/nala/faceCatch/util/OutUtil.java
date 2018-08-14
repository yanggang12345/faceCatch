package com.nala.faceCatch.util;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * create by lizenn
 * create date 2018/7/28
 * description
 */
public class OutUtil {

    public static void Out() {
        try {

            PrintStream print = new PrintStream("/Users/lizengqi/Documents/log/log.txt");  //写好输出位置文件；
            System.setOut(print);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
