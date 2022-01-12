package com.kmarcee.bankocr.business.service.parser;

import com.kmarcee.bankocr.business.exception.ApplicationException;
import com.kmarcee.bankocr.business.model.BankAccountNumber;

import java.util.List;

public interface NumberParser {

    /**
     * Extracts bank account number entries and account numbers from the resulting entries from the input file content.
     * @param fileContent the content of the input file
     * @return the extracted bank account numbers
     * @throws ApplicationException if extractions fails for any reason
     */
    List<BankAccountNumber> parse(String fileContent);

}
