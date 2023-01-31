package com.example.todayisdiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TodayIsDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodayIsDiaryApplication.class, args);
    }

}
