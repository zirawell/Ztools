package com.zlog.config.generator;

import org.junit.Test;

import java.util.HashSet;

/**
 * @Description: 配置文件生成器工具类的测试类
 * @Author: Dark Wang
 * @Create: 2025/1/17 18:06
 **/
public class ConfigGeneratorUtilsTest {
    @Test
    public void getQuanXRewriteKeywordSetTest(){
        HashSet<String> keywordSet = ConfigGeneratorUtils.getQuanXRewriteKeywordSet();
        for (String keyword : keywordSet){
            System.out.println(keyword);
        }
    }
}
