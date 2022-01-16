package com.kmarcee.bankocr.business.model;

import com.kmarcee.bankocr.business.exception.parsing.IllegalMatrixPositionAddressedException;
import com.kmarcee.bankocr.business.exception.parsing.IncompleteFigureException;
import com.kmarcee.bankocr.business.exception.validation.IllegalCharacterException;

import java.util.Arrays;

import static com.kmarcee.bankocr.business.model.Digit.UNKNOWN;

public class MatrixFigure {
    public static final int DIGIT_HEIGHT = 3;
    public static final int DIGIT_WIDTH = 3;
    public static final int MAX_MATRIX_POSITIONS = DIGIT_HEIGHT * DIGIT_WIDTH;
    private static final byte PIPE_CHAR = '|';
    private static final byte UNDERSCORE_CHAR = '_';
    private static final byte SPACE_CHAR = ' ';

    private final byte[] storedCharacters;
    private int index;

    public MatrixFigure() {
        storedCharacters = new byte[MAX_MATRIX_POSITIONS];
        index = 0;
    }

    /**
     * Builds a digit from the stored bytes in the respective positions.
     * If the bytes result in an invalid (arabic) digit, an unknown figure is returned.
     * @return A valid digit or an unknown figure.
     * @throws IncompleteFigureException if the digit is not yet ready to render (bytes are missing).
     */
    public Digit toDigit() {
        if (index != MAX_MATRIX_POSITIONS) {
            throw new IncompleteFigureException();
        }

        return Arrays.stream(Digit.values())
                .filter(digit -> Arrays.equals(digit.getMatrixRepresentation(), storedCharacters))
                .findFirst()
                .orElse(UNKNOWN);
    }

    /**
     * Adds a byte character to the matrix figure being built, if the character is allowed.
     * @param parsedChar the byte representation of the character (single byte)
     */
    public void addCharacter(byte parsedChar) {
        checkIfCharacterIsAllowed(parsedChar);
        checkIfMatrixPositionIsAllowed();
        storedCharacters[index++] = parsedChar;
    }

    private void checkIfCharacterIsAllowed(byte parsedChar) {
        if (!(parsedChar == SPACE_CHAR || parsedChar == PIPE_CHAR || parsedChar == UNDERSCORE_CHAR)) {
            throw new IllegalCharacterException(parsedChar);
        }
    }

    private void checkIfMatrixPositionIsAllowed() {
        if (index < 0 || index >= MAX_MATRIX_POSITIONS) {
            throw new IllegalMatrixPositionAddressedException(index);
        }
    }
}
