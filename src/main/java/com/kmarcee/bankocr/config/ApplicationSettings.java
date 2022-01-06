package com.kmarcee.bankocr.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix="settings")
@Getter
@Setter
public class ApplicationSettings {
    private InputSource inputSource;

    @Getter
    @Setter
    public static class InputSource {
        private String rootDir;
        private String dir;
        private String file;

        public String getFilePath() {
            return getRootDir() + File.separator + getDir() + File.separator + getFile();
        }
    }
}
