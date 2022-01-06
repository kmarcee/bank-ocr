package com.kmarcee.bankocr.business.exception.validation;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class InvalidFileSyntaxException extends ApplicationException {
    public InvalidFileSyntaxException() {
        super("Invalid file syntax. Although each line seems to have valid length and content, " +
                "the structure of bank account entries may not follow the expected pattern. " +
                "Please check for extra or missing lines, delimiting new lines, etc.");
    }
}
