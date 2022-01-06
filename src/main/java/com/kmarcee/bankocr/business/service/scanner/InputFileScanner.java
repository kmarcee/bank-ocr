package com.kmarcee.bankocr.business.service.scanner;

import com.kmarcee.bankocr.business.exception.parsing.FileReadingException;
import com.kmarcee.bankocr.config.ApplicationSettings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang.StringUtils.isBlank;

@Service
@Qualifier("inputFileScanner")
@Primary
@Slf4j
public class InputFileScanner implements FileScanner {

    private static final String CLOSING_NEWLINE = "\n";

    private final ApplicationSettings applicationSettings;

    @Autowired
    public InputFileScanner(ApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    @Override
    public String read() {
        if (isBlank(applicationSettings.getInputSource().getFilePath())) {
            log.warn("Cannot scan unspecified input file.");
            return null;
        }

        try (Stream<String> lines = Files.lines(
                new File(applicationSettings.getInputSource().getFilePath()).getCanonicalFile().toPath())) {
            log.debug(
                    "Scanning input file {}",
                    new File(applicationSettings.getInputSource().getFilePath()).getCanonicalPath()
            );
            String content = lines.collect(Collectors.joining("\n"));
            return StringUtils.isBlank(content) ? content : content + CLOSING_NEWLINE;
        } catch (IOException ioException) {
            throw new FileReadingException(ioException);
        }
    }
}
