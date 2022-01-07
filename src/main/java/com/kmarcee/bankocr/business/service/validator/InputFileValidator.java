package com.kmarcee.bankocr.business.service.validator;

import com.kmarcee.bankocr.business.exception.validation.EmptyFileException;
import com.kmarcee.bankocr.business.exception.validation.InvalidContentException;
import com.kmarcee.bankocr.business.exception.validation.InvalidFileSyntaxException;
import com.kmarcee.bankocr.business.exception.validation.InvalidLineLengthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static com.kmarcee.bankocr.business.service.parser.BankAccountParser.LINE_LENGTH;
import static com.kmarcee.bankocr.business.util.Utils.getContentAsLines;
import static java.util.regex.Pattern.MULTILINE;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Service
@Slf4j
public class InputFileValidator implements FileValidator {

    private static final Pattern ACCOUNT_FILE_PATTERN = Pattern.compile("^((([| _]{27}\\n){3}\\n)+)$", MULTILINE);
    private static final Pattern ACCOUNT_FILE_LINE_PATTERN = Pattern.compile("[| _]{27}");

    @Override
    public void validate(String fileContent) {
        checkIfNotEmpty(fileContent);
        checkByLine(fileContent);
        checkIfHasValidSyntax(fileContent);
    }

    private void checkByLine(String fileContent) {
        int lineNumber = 0;
        List<String> lines = getContentAsLines(fileContent);

        for (String line : lines) {
            lineNumber++;
            if (!(ACCOUNT_FILE_LINE_PATTERN.matcher(line).matches() || line.isEmpty())) {
                if (line.length() != LINE_LENGTH) {
                    throw new InvalidLineLengthException(
                            "Invalid file content. First error was detected in line " +
                                    lineNumber + "/" + lines.size() +
                                    " [The length of the line does not match the expectation.]: " +
                                    line
                    );
                } else {
                    throw new InvalidContentException(
                            "Invalid file content. First error was detected in line " +
                                    lineNumber + "/" + lines.size() +
                                    " [Some characters are not allowed.]: " +
                                    line
                    );
                }
            }
        }
    }

    private void checkIfHasValidSyntax(String fileContent) {
        if (!ACCOUNT_FILE_PATTERN.matcher(fileContent).matches()) {
            throw new InvalidFileSyntaxException();
        }
    }

    private void checkIfNotEmpty(String fileContent) {
        if (isEmpty(fileContent)) {
            throw new EmptyFileException();
        }
    }
}
