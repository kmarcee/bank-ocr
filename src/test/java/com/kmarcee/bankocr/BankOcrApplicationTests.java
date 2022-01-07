package com.kmarcee.bankocr;

import com.kmarcee.bankocr.business.model.BankAccountNumber;
import com.kmarcee.bankocr.business.service.parser.NumberParser;
import com.kmarcee.bankocr.business.service.scanner.FileScanner;
import com.kmarcee.bankocr.business.service.validator.FileValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class BankOcrApplicationTests {

    @Autowired
    FileScanner fileScanner;

    @Autowired
    FileValidator fileValidator;

    @Autowired
    NumberParser numberParser;

    @MockBean
    private ApplicationRunner applicationRunner;

    @Test
    void runAcceptanceTests() {
        String fileContent = fileScanner.read();
        fileValidator.validate(fileContent);
        String output = numberParser.parse(fileContent)
                .stream()
                .map(BankAccountNumber::print)
                .collect(Collectors.joining("\n"));

        assertThat(
                output,
                is(
                        "000000000\n" +
                                "111111111\n" +
                                "222222222\n" +
                                "333333333\n" +
                                "444444444\n" +
                                "555555555\n" +
                                "666666666\n" +
                                "777777777\n" +
                                "888888888\n" +
                                "999999999\n" +
                                "123456789\n" +
                                "123456?89\n" +
                                "?????????\n" +
                                "?????????"
                )
        );
    }

}
