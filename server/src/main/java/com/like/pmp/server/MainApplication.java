package com.like.pmp.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author like
 * @date 2022年04月25日 22:09
 */
@SpringBootApplication
@MapperScan("com.like.pmp.model.mapper")
@EnableScheduling
public class MainApplication {
     public static void main(String[] args) {
          SpringApplication.run(MainApplication.class, args);
     }
}
