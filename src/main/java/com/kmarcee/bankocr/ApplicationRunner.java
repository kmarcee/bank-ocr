package com.kmarcee.bankocr;

import com.kmarcee.bankocr.business.model.BankAccountNumber;
import com.kmarcee.bankocr.business.service.parser.NumberParser;
import com.kmarcee.bankocr.business.service.scanner.FileScanner;
import com.kmarcee.bankocr.business.service.validator.FileValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Slf4j
public class ApplicationRunner implements CommandLineRunner {

    private final FileScanner fileScanner;
    private final FileValidator fileValidator;
    private final NumberParser numberParser;

    @Autowired
    public ApplicationRunner(FileScanner fileScanner,
                             FileValidator fileValidator,
                             NumberParser numberParser) {
        this.fileScanner = fileScanner;
        this.fileValidator = fileValidator;
        this.numberParser = numberParser;
    }

    @Override
    public void run(String... args) {
        try {
            executeApplicationLogic();
            log.info("Closing application...");
        } catch (Throwable throwable) {
            log.error(throwable.getMessage());
        }
    }

    private void executeApplicationLogic() {
        String fileContent = fileScanner.read();
        fileValidator.validate(fileContent);
        log.info(
                "\nThe following bank account numbers have been extracted:\n{}",
                numberParser.parse(fileContent)
                        .stream()
                        .map(BankAccountNumber::print)
                        .collect(Collectors.joining("\n"))
        );
    }
}
