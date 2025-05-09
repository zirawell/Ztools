package com.zlog.config.generator;

import com.zlog.config.generator.quanx.QuanXConfigGenerator;
import com.zlog.config.generator.surge.SurgeConfigGenerator;

/**
 * @Description: 生成所有配置文件
 * @Author: Dark Wang
 * @Create: 2024/8/23 11:22
 **/
public class AllConfigGenerator {

    public static void main(String[] args) {
        // QuanX配置生成
        QuanXConfigGenerator.generateConfigs();
        // Surge配置生成
        SurgeConfigGenerator.generateConfigs();
    }


}
