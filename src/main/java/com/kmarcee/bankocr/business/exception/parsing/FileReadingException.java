package com.kmarcee.bankocr.business.exception.parsing;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class FileReadingException extends ApplicationException {
    public FileReadingException(Throwable cause) {
        super("Could not read input file.", cause);
    }
}
