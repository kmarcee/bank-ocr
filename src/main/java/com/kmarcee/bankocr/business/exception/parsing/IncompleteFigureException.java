package com.kmarcee.bankocr.business.exception.parsing;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class IncompleteFigureException extends ApplicationException {
    public IncompleteFigureException() {
        super("The matrix figure is not yet complete to be converted to a digit.");
    }
}
