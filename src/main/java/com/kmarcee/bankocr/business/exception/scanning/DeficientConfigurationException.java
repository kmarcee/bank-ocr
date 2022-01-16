package com.kmarcee.bankocr.business.exception.scanning;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class DeficientConfigurationException extends ApplicationException {

    public DeficientConfigurationException(String message) {
        super(message);
    }
}
