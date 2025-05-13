package com.zlog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Description:
 * @Author: Dark Wang
 * @Create: 2025/5/9 11:00
 **/
public class test {
    public static void main(String[] args) throws IOException {
        String path = "/Users/zirawell/Git/R-Store/Rule/QuanX/Adblock/All/rewrite/allAdRewrite.conf";
        //open the file
        FileReader file = new FileReader(path);
        BufferedReader buffer = new BufferedReader(file);
        //read the 1st line
        String line = buffer.readLine();
        //display the 1st line
        System.out.println(line);
    }
}
