package com.kmarcee.bankocr.business.exception.scanning;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class FileReadingException extends ApplicationException {

    public FileReadingException(String message) {
        super(message);
    }

    public FileReadingException(Throwable cause) {
        super("Could not read input file.", cause);
    }
}
