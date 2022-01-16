package com.kmarcee.bankocr.business.exception.validation;

public class IllegalMultiByteCharacterException extends InvalidContentException {
    public IllegalMultiByteCharacterException(String line) {
        super("The line contains illegal multi-byte characters: " + line);
    }
}
