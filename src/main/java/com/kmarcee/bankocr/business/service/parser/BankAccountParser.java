package com.kmarcee.bankocr.business.service.parser;

import com.kmarcee.bankocr.business.exception.parsing.LineNumberMismatchException;
import com.kmarcee.bankocr.business.exception.validation.IllegalMultiByteCharacterException;
import com.kmarcee.bankocr.business.exception.validation.InvalidLineLengthException;
import com.kmarcee.bankocr.business.model.BankAccountNumber;
import com.kmarcee.bankocr.business.model.MatrixFigure;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.kmarcee.bankocr.business.model.BankAccountNumber.DIGITS_IN_ACCOUNT_NUMBER;
import static com.kmarcee.bankocr.business.model.BankAccountNumber.fromMatrixFigures;
import static com.kmarcee.bankocr.business.model.MatrixFigure.DIGIT_HEIGHT;
import static com.kmarcee.bankocr.business.model.MatrixFigure.DIGIT_WIDTH;
import static com.kmarcee.bankocr.business.util.Utils.getContentAsLines;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Service
public class BankAccountParser implements NumberParser {

    public static final int LINES_PER_ENTRY = DIGIT_HEIGHT;
    public static final int LINES_PER_ENTRY_PLUS_BLANK_LINE = LINES_PER_ENTRY + 1;
    public static final int LINE_LENGTH = DIGITS_IN_ACCOUNT_NUMBER * DIGIT_WIDTH;

    @Override
    public List<BankAccountNumber> parse(String fileContent) {
        return parseBankAccountNumbers(getContentAsLines(fileContent));
    }

    private List<BankAccountNumber> parseBankAccountNumbers(List<String> lines) {
        List<BankAccountNumber> bankAccountNumbers = new LinkedList<>();
        List<String> linesForEntry = new LinkedList<>();
        int counter = 0;
        for (String actualLine : lines) {
            counter++;
            if (isEmpty(actualLine) && counter % LINES_PER_ENTRY_PLUS_BLANK_LINE == 0) {
                bankAccountNumbers.add(extractFromLines(linesForEntry));
                linesForEntry.clear();
            } else {
                linesForEntry.add(actualLine);
            }
        }
        return bankAccountNumbers;
    }

    private static BankAccountNumber extractFromLines(List<String> lines) {
        if (lines.size() != LINES_PER_ENTRY) {
            throw new LineNumberMismatchException();
        }

        return fromMatrixFigures(buildMatrixFigures(lines));
    }

    private static MatrixFigure[] buildMatrixFigures(List<String> lines) {
        MatrixFigure[] figures = initializeMatrixFigures();
        buildUpFiguresFromLines(lines, figures);
        return figures;
    }

    private static void buildUpFiguresFromLines(List<String> lines, MatrixFigure[] figures) {
        for (String line : lines) {
            byte[] bytes = getBytes(line);
            for (int col = 0; col < LINE_LENGTH; col++) {
                figures[col / DIGIT_WIDTH].addCharacter(bytes[col]);
            }
        }
    }

    private static MatrixFigure[] initializeMatrixFigures() {
        MatrixFigure[] figures = new MatrixFigure[DIGITS_IN_ACCOUNT_NUMBER];

        for (int i = 0; i < DIGITS_IN_ACCOUNT_NUMBER ; i++) {
            figures[i] = new MatrixFigure();
        }

        return figures;
    }

    private static byte[] getBytes(String line) {
        byte[] bytes = line.getBytes(UTF_8);
        checkLineLength(line);
        checkForExistenceOfMultiByteCharacters(bytes);
        return bytes;
    }

    private static void checkForExistenceOfMultiByteCharacters(byte[] bytes) {
        if (bytes.length != LINE_LENGTH) {
            throw new IllegalMultiByteCharacterException(Arrays.toString(bytes));
        }
    }

    private static void checkLineLength(String line) {
        if (line.length() != LINE_LENGTH) {
            throw new InvalidLineLengthException("Invalid line length.");
        }
    }
}
