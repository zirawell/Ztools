package com.zlog.config.generator.utils;

import com.zlog.config.generator.constants.ConfigGeneratorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
     */
    public static void writeToFile(File file, String content, boolean appendFlag) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, appendFlag))) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 替换文件中指定行范围的旧内容为新内容
     * @param filePath          文件路径
     * @param startLine         起始行号（从1开始）
     * @param numLinesToReplace 要替换的行数（若超出文件行数，则替换到末尾）
     * @param newContent        新内容（行数可自由设定）
     */
    public static void replaceLines(String filePath, int startLine, int numLinesToReplace, List<String> newContent) {
        // 参数校验
        if (startLine < 1) {
            throw new IllegalArgumentException("起始行号必须 ≥ 1");
        }
        if (numLinesToReplace < 0) {
            throw new IllegalArgumentException("替换行数不能为负数");
        }
        Path path = Paths.get(filePath);
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            // 检查起始行号是否超出文件范围
            if (startLine > lines.size()) {
                throw new IllegalArgumentException("起始行号超过文件总行数（文件共 " + lines.size() + " 行）");
            }
            // 转换为0-based索引
            int startIndex = startLine - 1;
            int endIndex = Math.min(startIndex + numLinesToReplace, lines.size());
            // 删除旧行并插入新内容
            lines.subList(startIndex, endIndex).clear();
            lines.addAll(startIndex, newContent);
            // 写回文件
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 替换文件中指定行范围的旧内容为新内容
     * @param filePath          文件路径
     * @param replaceStringSign         起始行号标记位
     * @param numLinesToReplace 要替换的行数（若超出文件行数，则替换到末尾）
     * @param newContent        新内容（行数可自由设定）
     */
    public static void replaceLines(String filePath, String replaceStringSign, int numLinesToReplace, List<String> newContent) {
        List<String> lines;
        Path path = Paths.get(filePath);
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            // 转换为0-based索引
            int startIndex = lines.indexOf(replaceStringSign);
            int endIndex = Math.max(startIndex + numLinesToReplace, lines.size());
            // 删除旧行并插入新内容
            lines.subList(startIndex, endIndex).clear();
            lines.addAll(startIndex, newContent);
            // 写回文件
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取QuanX Rewrite的所有关键字集合
     * @return HashSet<String>
     */
    public static HashSet<String> getQuanXRewriteKeywordSet() {
        String path = "/Users/zirawell/Git/R-Store/Rule/QuanX/Adblock/All/rewrite/allAdRewrite.conf";
        HashSet<String> keySet = new HashSet<>();
        //读取文件
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
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
     * 获取QuanX Rewrite的单行的关键字
     * @return String
     */
    public static String getQuanXRewriteKeywordForLine(String line) {
        String key = null;
        if(!line.trim().startsWith("#") && !line.trim().isEmpty() && !line.trim().startsWith("hostname")){
            key = line.split(" ")[2];
        }
        return key;
    }


    /**
     * 正则替换
     * @param regex1 匹配原字符串的正则表达式
     * @param regex2 输出字符串的正则表达式
     * @param input 原有字符串
     * @return String 按照regex2格式生成的字符串
     */
    public static String scriptRegexReplace(String regex1, String regex2, String input) {
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String matchedString = matcher.group();
            String[] parts = matchedString.split("\\s+");
            if ("hostname".equals(parts[0])) {
                parts = new String[]{parts[0], parts[1], String.join(" ", Arrays.copyOfRange(parts, 2, parts.length))};
            }
            return replacePlaceholders(regex2, parts);
        } else {
            return null;
        }
    }

    /**
     * 替换占位符
     * @param template 带有占位符的字符串
     * @param replacements 替换的数组
     * @return String 替换完的字符串
     */
    private static String replacePlaceholders(String template, String[] replacements) {
        StringBuilder result = new StringBuilder(template);
        // 查找$后面的数字并进行替换
        for (int i = 0; i < replacements.length; i++) {
            String placeholder = "$" + i;
            int index = result.indexOf(placeholder);
            while (index != -1) {
                result.replace(index, index + placeholder.length(), replacements[i]);
                index = result.indexOf(placeholder, index + replacements[i].length());
            }
        }
        return result.toString();
    }


    /**
     * 获取文件名
     * @param filePath 文件路径
     * @return 文件名称
     */
    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf(ConfigGeneratorConstants.FILE_SEPARATOR)+1,filePath.lastIndexOf(ConfigGeneratorConstants.DOT_SIGN));
    }

    /**
     * 获取Git分支
     *
     * @return Git分支
     */
    public static String getBranchName() {
        StringBuilder sbTmp = new StringBuilder();
        String output;
        // 定义要执行的命令
        String command = "cd " + ConfigGeneratorConstants.PROJECT_BASE_DIRECTORY + "&& git branch --show-current";
        try {
            int exitCode = ConfigGeneratorUtils.executeShellCommand(sbTmp, command);
            if (exitCode != 0) {
                System.out.println("getBranchName: Command failed with exit code: " + exitCode);
            }
            System.out.println("Git分支：" + sbTmp);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        output = sbTmp.toString().replace("\n", "");
        return output;
    }

    public static String convertFilePathToRawUrl(String filePath) {
        return filePath.replace(ConfigGeneratorConstants.HOME_DIRECTORY
                                    + ConfigGeneratorConstants.FILE_SEPARATOR
                                    + ConfigGeneratorConstants.GIT_SIGN,
                        ConfigGeneratorConstants.GITHUB_RAW_URL_PREFIX)
                .replace(ConfigGeneratorConstants.PROJECT_NAME_SIGN,
                        ConfigGeneratorConstants.PROJECT_NAME_SIGN
                                  + ConfigGeneratorConstants.FILE_SEPARATOR
                                  + ConfigGeneratorConstants.GIT_BRANCH);
    }

    /**
     * 检查目录是否修改
     * @param dirPath 需要判定的路径
     * @return boolean 存在修改返回true，不存在修改返回false
     */
    public static boolean checkIsChanged(String dirPath){
        StringBuilder sbTmp;
        sbTmp = new StringBuilder();
        boolean result;
        // 定义要执行的命令
        String command = "cd " + dirPath + "&& git status ." ;
        try {
            int exitCode = ConfigGeneratorUtils.executeShellCommand(sbTmp, command);
            if (exitCode != 0) {
                System.out.println("getBranchName: Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        result = sbTmp.toString().contains("nothing to commit, working tree clean");
        return !result;
    }

    public static String getFirstLine(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String firstLine = reader.readLine();
            reader.close();
            return firstLine;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

    }



}
