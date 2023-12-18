package com.zj.union;

import cn.xuyanwu.spring.file.storage.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableFileStorage
@SpringBootApplication
@MapperScan("com.zj.union.mapper")
public class UnionApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnionApplication.class, args);
    }

}
