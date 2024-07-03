package com.threedollar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.threedollar"})
public class CommunityApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityApiApplication.class, args);
    }

}
