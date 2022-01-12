package com.kmarcee.bankocr.business.service.validator;

import com.kmarcee.bankocr.business.exception.ApplicationException;

public interface FileValidator {

    /**
     * Validates the file content, both the file format (syntax) and contained characters.
     * @param fileContent the read lines
     * @throws ApplicationException if any validation fails (violation occurs)
     */
    void validate(String fileContent);

}
