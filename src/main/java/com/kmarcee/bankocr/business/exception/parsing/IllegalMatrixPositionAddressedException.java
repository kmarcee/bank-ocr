package com.kmarcee.bankocr.business.exception.parsing;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public class IllegalMatrixPositionAddressedException extends ApplicationException {
    public IllegalMatrixPositionAddressedException(int position) {
        super("Could not address position " + position + " in the character matrix.");
    }
}
