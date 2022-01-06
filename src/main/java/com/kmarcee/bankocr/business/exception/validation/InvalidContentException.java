package com.kmarcee.bankocr.business.exception.validation;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class InvalidContentException extends ApplicationException {
    public InvalidContentException(String message) {
        super(message);
    }
}
