package com.kmarcee.bankocr.business.service.parser;

import com.kmarcee.bankocr.business.model.BankAccountNumber;

import java.util.List;

public interface NumberParser {
    List<BankAccountNumber> parse(String fileContent);
}
