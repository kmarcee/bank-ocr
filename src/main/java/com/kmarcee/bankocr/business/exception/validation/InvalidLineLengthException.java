package com.kmarcee.bankocr.business.exception.validation;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class InvalidLineLengthException extends ApplicationException {
    public InvalidLineLengthException(String message) {
        super(message);
    }
}
