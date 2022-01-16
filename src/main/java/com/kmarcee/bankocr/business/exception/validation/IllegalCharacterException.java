package com.kmarcee.bankocr.business.exception.validation;

public class IllegalCharacterException extends InvalidContentException {
    public IllegalCharacterException(byte character) {
        super("Illegal character found: " + character);
    }
}
