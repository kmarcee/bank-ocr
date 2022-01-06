package com.kmarcee.bankocr.business.exception.validation;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class EmptyFileException extends ApplicationException {
    public EmptyFileException() {
        super("File is empty.");
    }
}
