package com.kmarcee.bankocr.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Qualifier("inputFileScanner")
@Primary
@Slf4j
public class InputFileScanner implements FileScanner {
    @Override
    public void read() {
        String fileName = "testData.txt";
        log.debug("Scanning input file {}", fileName);
    }
}
