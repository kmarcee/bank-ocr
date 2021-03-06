package com.kmarcee.bankocr.business.service.scanner;

import com.kmarcee.bankocr.business.exception.scanning.DeficientConfigurationException;
import com.kmarcee.bankocr.business.exception.scanning.FileReadingException;
import com.kmarcee.bankocr.config.ApplicationSettings;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
        doReturn("test.txt").when(inputSource).getFile();
        cleanUpFiles();
    }

    @AfterEach
    void tearDown() {
        cleanUpFiles();
    }

    private void cleanUpFiles() {
        File targetFile = new File(inputSource.getFilePath());
        targetFile.delete();
    }

    private void createFileWithTextContent(String content) throws IOException {
        ensureTargetDirectoryExists();
        writeToFile(content);
    }

    private void ensureTargetDirectoryExists() throws IOException {
        File rootDir = new File(inputSource.getRootDir());
        File nestedDir = new File(rootDir, inputSource.getDir());
        if (!nestedDir.exists() && !nestedDir.mkdirs()) {
            throw new IOException("Cannot create directories.");
        }
    }

    private void writeToFile(String content) throws IOException {
        FileOutputStream fos = new FileOutputStream(inputSource.getFilePath());
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        if (StringUtils.isNotEmpty(content)) {
            outStream.writeUTF(content);
        }
        outStream.close();
    }

    @Test
    void read_inputFileNotExists_exceptionThrown() {
        assertFalse(Files.exists(Paths.get(inputSource.getFilePath())));
        assertThrows(FileReadingException.class, () -> fileScanner.read());
    }

    @Test
    void read_inputFilePathNotSpecifiedOrEmpty_nullFileContentReturned() throws IOException {
        doReturn("").when(inputSource).getFilePath();

        assertThrows(DeficientConfigurationException.class, () -> fileScanner.read());
    }

    @Test
    void read_emptyInputFileExists_fileContentReturned() throws IOException {
        createFileWithTextContent(null);

        String fileContent = fileScanner.read();

        assertThat(fileContent, emptyOrNullString());
    }

    @Test
    void read_inputFileWithSomeContentExists_fileContentReturned() throws IOException {
        createFileWithTextContent(
                "abcdef\n" +
                "ghijkl\n" +
                "mnopqr\n" +
                "\n" +
                "apple\n" +
                "pie\n" +
                "\n" +
                "END"
        );

        String fileContent = fileScanner.read();
        List<String> lines = Arrays.asList(fileContent.split("\n"));

        assertThat(fileContent, not(emptyOrNullString()));
        assertThat(lines, hasSize(8));
    }
}
