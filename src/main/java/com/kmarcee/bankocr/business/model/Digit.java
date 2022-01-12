package com.kmarcee.bankocr.business.model;

import static java.nio.charset.StandardCharsets.UTF_8;

public enum Digit {
    ZERO(0,    " _ " +
                                     "| |" +
                                     "|_|"),
    ONE(1,     "   " +
                                     "  |" +
                                     "  |"),
    TWO(2,     " _ " +
                                     " _|" +
                                     "|_ "),
    THREE(3,    " _ " +
                                      " _|" +
                                      " _|"),
    FOUR(4,     "   " +
                                      "|_|" +
                                      "  |"),
    FIVE(5,     " _ " +
                                      "|_ " +
                                      " _|"),
    SIX(6,      " _ " +
                                      "|_ " +
                                      "|_|"),
    SEVEN(7,    " _ " +
                                      "  |" +
                                      "  |"),
    EIGHT(8,    " _ " +
                                      "|_|" +
                                      "|_|"),
    NINE(9,     " _ " +
                                      "|_|" +
                                      " _|"),
    UNKNOWN(10, "   " +
                                      "| |" +
                                      "|_|");

    private final int value;
    private final String matrixRepresentation;

    Digit(int value, String matrixRepresentation) {
        this.value = value;
        this.matrixRepresentation = matrixRepresentation;
    }

    /**
     * Returns the respective arabic number (numeric value) of this digit as text.
     * @return the respective arabic number (numeric value) of this digit as text
     */
    public String getNumericValue() {
        return value == 10 ? "?" : String.valueOf(value);
    }

    /**
     * Returns the byte array representing this digit.
     * @return the byte array representing this digit
     */
    public byte[] getMatrixRepresentation() {
        return matrixRepresentation.getBytes(UTF_8);
    }
}
