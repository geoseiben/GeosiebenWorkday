package com.geosieben.gsbworkday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GsbWorkDayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsbWorkDayApplication.class, args);
    }
}
