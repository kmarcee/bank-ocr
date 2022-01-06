package com.kmarcee.bankocr;

import com.kmarcee.bankocr.business.FileScanner;
import com.kmarcee.bankocr.business.FileValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class ApplicationRunner implements CommandLineRunner {

    private final ApplicationContext ctx;
    private final FileScanner fileScanner;
    private final FileValidator fileValidator;

    @Autowired
    public ApplicationRunner(ApplicationContext ctx, FileScanner fileScanner, FileValidator fileValidator) {
        this.ctx = ctx;
        this.fileScanner = fileScanner;
        this.fileValidator = fileValidator;
    }

    @Override
    public void run(String... args) {
        try {
            //printInitializedComponents();
            executeApplicationLogic();
            log.info("Closing application...");
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
        }
    }

    private void executeApplicationLogic() {
        String fileContent = fileScanner.read();
        fileValidator.validate(fileContent);
    }

    private void printInitializedComponents() {
        StringBuilder beanListBuilder = new StringBuilder("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            beanListBuilder.append("\n").append(beanName);
        }
        log.info(beanListBuilder.toString());
    }
}
