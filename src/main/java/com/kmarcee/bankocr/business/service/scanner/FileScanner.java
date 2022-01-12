package com.kmarcee.bankocr.business.service.scanner;

import com.kmarcee.bankocr.business.exception.scanning.FileReadingException;

public interface FileScanner {

    /**
     * Reads the content of the input text file.
     * Note: the path of the input file is specified in the application settings (app configuration file)
     * @throws FileReadingException if reading the file fails for any reason
     */
    String read();

}
