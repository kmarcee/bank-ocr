package com.kmarcee.bankocr;

import com.kmarcee.bankocr.business.FileScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class ApplicationRunner implements CommandLineRunner {

    private final ApplicationContext ctx;
    private final FileScanner fileScanner;

    public ApplicationRunner(ApplicationContext ctx, FileScanner fileScanner) {
        this.ctx = ctx;
        this.fileScanner = fileScanner;
    }

    @Override
    public void run(String... args) {
        try {
            printInitializedComponents();
            executeApplicationLogic();
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private void executeApplicationLogic() {
        fileScanner.read();
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
