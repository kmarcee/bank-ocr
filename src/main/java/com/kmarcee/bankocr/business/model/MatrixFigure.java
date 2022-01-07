package com.kmarcee.bankocr.business.model;

import com.kmarcee.bankocr.business.exception.parsing.IllegalMatrixPositionAddressedException;
import com.kmarcee.bankocr.business.exception.parsing.IncompleteFigureException;

import java.util.Arrays;

import static com.kmarcee.bankocr.business.model.Digit.UNKNOWN;

public class MatrixFigure {
    public static final int DIGIT_HEIGHT = 3;
    public static final int DIGIT_WIDTH = 3;
    public static final int MAX_MATRIX_POSITIONS = DIGIT_HEIGHT * DIGIT_WIDTH;

    private final byte[] storedCharacters;
    private int index;

    public MatrixFigure() {
        storedCharacters = new byte[MAX_MATRIX_POSITIONS];
        index = 0;
    }

    public void addCharacter(byte parsedChar) {
        if (index >= 0 && index < MAX_MATRIX_POSITIONS) {
            storedCharacters[index++] = parsedChar;
        } else {
            throw new IllegalMatrixPositionAddressedException(index);
        }
    }

    public Digit toDigit() {
        if (index != MAX_MATRIX_POSITIONS) {
            throw new IncompleteFigureException();
        }

        return Arrays.stream(Digit.values())
                .filter(digit -> Arrays.equals(digit.getMatrixRepresentation(), storedCharacters))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
