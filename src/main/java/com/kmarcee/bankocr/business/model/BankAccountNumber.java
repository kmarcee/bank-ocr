package com.kmarcee.bankocr.business.model;

import static com.kmarcee.bankocr.business.model.Digit.UNKNOWN;

public class BankAccountNumber {
    public static final int DIGITS_IN_ACCOUNT_NUMBER = 9;

    private final Digit[] digits;

    private BankAccountNumber() {
        digits = new Digit[9];
    }

    /**
     *
     * @param figures
     * @return
     */
    public static BankAccountNumber fromMatrixFigures(MatrixFigure[] figures) {
        BankAccountNumber bankAccountNumber = new BankAccountNumber();
        for (int i = 0; i < DIGITS_IN_ACCOUNT_NUMBER; i++) {
            bankAccountNumber.digits[i] = figures == null ? UNKNOWN : figures[i].toDigit();
        }
        return bankAccountNumber;
    }

    /**
     *
     * @return
     */
    public String print() {
        StringBuilder printedBankAccountNumberBuilder = new StringBuilder();
        for (Digit digit: digits) {
            printedBankAccountNumberBuilder.append(digit.getNumericValue());
        }
        return printedBankAccountNumberBuilder.toString();
    }
}
