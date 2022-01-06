package com.kmarcee.bankocr.business;

import com.kmarcee.bankocr.config.ApplicationSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ActiveProfiles(profiles = "test")
public class FileScannerTests {

    FileScanner fileScanner;

    @Mock
    ApplicationSettings applicationSettings;

    @Spy
    ApplicationSettings.InputSource inputSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileScanner = new InputFileScanner(applicationSettings);
        when(applicationSettings.getInputSource()).thenReturn(inputSource);
        doReturn("data").when(inputSource).getRootDir();
        doReturn("test").when(inputSource).getDir();
    }

    @Test
    void read_inputFileNotExists_exceptionThrown() {
        doReturn("nonExistingInputData.txt").when(inputSource).getFile();

        assertThrows(NoSuchFileException.class, () -> fileScanner.read());
    }

    @Test
    void read_emptyInputFileExists_fileContentReturned() throws IOException {
        doReturn("emptyTestData.txt").when(inputSource).getFile();

        String fileContent = fileScanner.read();

        assertThat(fileContent, emptyOrNullString());
    }

    @Test
    void read_inputFileWithSomeContentExists_fileContentReturned() throws IOException {
        doReturn("someContent.txt").when(inputSource).getFile();

        String fileContent = fileScanner.read();
        List<String> lines = Arrays.asList(fileContent.split("\n"));

        assertThat(fileContent, not(emptyOrNullString()));
        assertThat(lines, hasSize(8));
    }
}
