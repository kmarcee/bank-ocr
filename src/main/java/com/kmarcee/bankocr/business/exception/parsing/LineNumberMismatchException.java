package com.kmarcee.bankocr.business.exception.parsing;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class LineNumberMismatchException extends ApplicationException {
    public LineNumberMismatchException() {
        super("Illegal number of lines provided for bank account number extraction.");
    }
}
