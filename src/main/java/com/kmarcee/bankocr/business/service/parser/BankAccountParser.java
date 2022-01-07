package com.kmarcee.bankocr.business.service.parser;

import com.kmarcee.bankocr.business.exception.parsing.LineNumberMismatchException;
import com.kmarcee.bankocr.business.exception.validation.InvalidLineLengthException;
import com.kmarcee.bankocr.business.model.BankAccountNumber;
import com.kmarcee.bankocr.business.model.MatrixFigure;
import org.springframework.stereotype.Service;

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
    public static final int LINE_LENGTH = DIGITS_IN_ACCOUNT_NUMBER * DIGIT_WIDTH;

    @Override
    public List<BankAccountNumber> parse(String fileContent) {
        List<BankAccountNumber> bankAccountNumbers = new LinkedList<>();
        List<String> linesForEntry = new LinkedList<>();

        List<String> lines = getContentAsLines(fileContent);

        for (String actualLine : lines) {
            if (!isEmpty(actualLine)) {
                linesForEntry.add(actualLine);
            } else {
                bankAccountNumbers.add(extractFromLines(linesForEntry));
                linesForEntry.clear();
            }
        }

        return bankAccountNumbers;
    }

    public static BankAccountNumber extractFromLines(List<String> lines) {
        if (lines.size() != LINES_PER_ENTRY) {
            throw new LineNumberMismatchException();
        }

        return fromMatrixFigures(buildMatrixFigures(lines));
    }

    private static MatrixFigure[] buildMatrixFigures(List<String> lines) {
        MatrixFigure[] figures = new MatrixFigure[DIGITS_IN_ACCOUNT_NUMBER];

        for (int i = 0; i < DIGITS_IN_ACCOUNT_NUMBER ; i++) {
            figures[i] = new MatrixFigure();
        }

        for (String line : lines) {
            if (line.length() != LINE_LENGTH) {
                throw new InvalidLineLengthException("Invalid line length.");
            }
            byte[] bytes = line.getBytes(UTF_8);
            for (int col = 0; col < LINE_LENGTH; col++) {
                figures[col / DIGIT_WIDTH].addCharacter(bytes[col]);
            }
        }
        return figures;
    }
}
