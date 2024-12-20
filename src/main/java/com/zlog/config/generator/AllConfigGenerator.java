package com.zlog.config.generator;

/**
 * @Description: 生成所有配置文件
 * @Author: Dark Wang
 * @Create: 2024/8/23 11:22
 **/
public class AllConfigGenerator {

    public static void main(String[] args) {
        QuanXConfigGenerator.generateConfigs();
        SurgeConfigGenerator.generateConfigs();

    }


}
