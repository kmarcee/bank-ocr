package com.kmarcee.bankocr;

import com.kmarcee.bankocr.config.ApplicationSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationSettings.class)
public class BankOcrApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankOcrApplication.class, args);
    }

}
