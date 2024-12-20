package com.zlog.config.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 配置文件生成器工具类
 * @Author: Dark Wang
 * @Create: 2024/12/20 09:27
 **/
public class ConfigGeneratorUtils {
    private static final Logger logger = LoggerFactory.getLogger("ConfigGeneratorUtils.class");
    /**
     * @Description: 获取用户HOME路径
     * @return Home路径
     */
    public static String getHomeDirectory() {
        StringBuilder sbTmp = new StringBuilder();
        String output;
        // 定义要执行的命令
        String command = "echo $HOME";
        try {
            int exitCode = executeShellCommand(sbTmp, command);
            if (exitCode != 0) {
                System.out.println("getHomeDirectory: Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        output = sbTmp.toString();
        output = output.replaceAll("\n", "");
        return output;
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return String 首字母大写的字符串
     */
    public static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 执行shell命令
     * @param sbTmp 输出内容
     * @param command shell命令
     * @return Integer
     * @throws IOException IO异常
     * @throws InterruptedException 线程中断异常
     */
    public static Integer executeShellCommand(StringBuilder sbTmp, String command) throws IOException, InterruptedException {
        // 使用 ProcessBuilder 构建进程
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("zsh", "-c", command);
        // 启动进程
        Process process = processBuilder.start();
        // 获取命令执行的输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            sbTmp.append(line).append(System.lineSeparator());
        }
        // 等待命令执行完成
        return process.waitFor();
    }

    /**
     * 将内容写入文件
     *
     * @param file    目标文件
     * @param content 要写入的内容
     * @param appendFlag  是否追加内容
     * @throws IOException IO异常
     */
    public static void writeToFile(File file, String content, boolean appendFlag) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, appendFlag))) {
            writer.write(content);
        }
    }

    /**
     * 获取QuanX Rewrite关键字
     * @return HashSet<String>
     */
    public static HashSet<String> getQuanXRewriteKeywordSet() {
        String path = "/Users/zirawell/Git/R-Store/Rule/QuanX/Adblock/All/rewrite/allAdRewrite.conf";
        HashSet<String> keySet = new HashSet<>();
        //读取文件
        try (BufferedReader reader = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if(!line.trim().startsWith("#") && !line.trim().isEmpty() && !line.trim().startsWith("hostname")){
                    String key = line.split(" ")[2];
                    keySet.add(key);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return keySet;
    }


    /**
     * 正则替换
     * @param regex1 匹配原字符串的正则表达式
     * @param regex2 输出字符串的正则表达式
     * @param input 原有字符串
     * @return String 按照regex2格式生成的字符串
     */
    public static String scriptRegexReplace(String regex1,String regex2,String input) {
        // 创建Pattern和Matcher
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(input);
        // 执行替换
        return matcher.replaceAll(regex2);

    }



}
