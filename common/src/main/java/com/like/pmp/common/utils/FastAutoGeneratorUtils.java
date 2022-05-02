package com.like.pmp.common.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author like
 * @date 2022年04月26日 15:16
 */
public class FastAutoGeneratorUtils {
    public static void main(String[] args) {
        //装表用
        List<String> list = new ArrayList<>();
        list.add("sys_config");
        list.add("sys_dept");
        list.add("sys_dict");
        list.add("sys_log");
        list.add("sys_menu");
        list.add("sys_post");
        list.add("sys_role");
        list.add("sys_role_dept");
        list.add("sys_role_menu");
        list.add("sys_user");
        list.add("sys_user_post");
        list.add("sys_user_role");
        FastAutoGenerator.create("jdbc:mysql://110.40.194.40:3306/pmp?characterEncoding=utf-8&useSSL=false", "root", "191110")
                .globalConfig(builder -> {
                    builder.author("like") // 设置作者
                            // .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("/Users/like/mybatis-generator"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.like.pmp") // 设置父包名
                            .moduleName("model") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/Users/like/mybatis-generator")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(list) // 设置需要生成的表名
                            .addTablePrefix("", ""); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
