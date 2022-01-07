package com.kmarcee.bankocr.business.service.validator;

import com.kmarcee.bankocr.business.exception.validation.EmptyFileException;
import com.kmarcee.bankocr.business.exception.validation.InvalidContentException;
import com.kmarcee.bankocr.business.exception.validation.InvalidFileSyntaxException;
import com.kmarcee.bankocr.business.exception.validation.InvalidLineLengthException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles(profiles = "test")
public class FileValidatorTests {

    FileValidator fileValidator = new InputFileValidator();

    @Test
    void validate_contentIsNull_exceptionThrown() {
        assertThrows(EmptyFileException.class, () -> fileValidator.validate(null));
    }

    @Test
    void validate_contentIsEmpty_exceptionThrown() {
        assertThrows(EmptyFileException.class, () -> fileValidator.validate(""));
    }

    @Test
    void validate_contentHasLineWithIncorrectLength_exceptionThrown() {
        assertThrows(InvalidLineLengthException.class, () -> fileValidator.validate(" "));
    }

    @Test
    void validate_contentHasLineWithInvalidCharacter_exceptionThrown() {
        assertThrows(
                InvalidContentException.class,
                () -> fileValidator.validate("___|||___  _|-|_  ___|||___")
        );
    }

    @Test
    void validate_contentHasCorrectLineLengthAndContentButInvalidFileSyntaxNewLinesOnly_exceptionThrown() {
        assertThrows(
                InvalidFileSyntaxException.class,
                () -> fileValidator.validate("\n\n\n\n")
        );
    }

    @Test
    void validate_contentHasCorrectLineLengthAndContentButInvalidFileSyntaxIncompleteEntry_exceptionThrown() {
        assertThrows(
                InvalidFileSyntaxException.class,
                () -> fileValidator.validate("___|||___  _|||_  ___|||___")
        );
    }

    @Test
    void validate_contentHasCorrectLineLengthAndContentButInvalidFileSyntaxDueToExtraNewlineAtBeginning_exceptionThrown() {
        assertThrows(
                InvalidFileSyntaxException.class,
                () -> fileValidator.validate(
                        "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "\n"
                )
        );
    }

    @Test
    void validate_contentHasCorrectLineLengthAndContentButInvalidFileSyntaxDueToExtraNewlineAtEnd_exceptionThrown() {
        assertThrows(
                InvalidFileSyntaxException.class,
                () -> fileValidator.validate(
                        "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "\n" +
                                "\n"
                )
        );
    }

    @Test
    void validate_contentHasCorrectLineLengthAndContentButInvalidFileSyntaxDueToExtraNewlineWithinAnEntry_exceptionThrown() {
        assertThrows(
                InvalidFileSyntaxException.class,
                () -> fileValidator.validate(
                        "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "\n"
                )
        );
    }

    @Test
    void validate_contentHasValidContentAndValidFileSyntaxWithOneEntry_noExceptionThrown() {
        assertDoesNotThrow(
                () -> fileValidator.validate(
                        "___|||___  _|||_  ___|||___" + "\n" +
                        "___|||___  _|||_  ___|||___" + "\n" +
                        "___|||___  _|||_  ___|||___" + "\n" +
                        "\n"
                )
        );
    }

    @Test
    void validate_contentHasValidContentAndValidFileSyntaxWithTwoEntries_noExceptionThrown() {
        assertDoesNotThrow(
                () -> fileValidator.validate(
                        "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "___|||___  _|||_  ___|||___" + "\n" +
                                "\n"
                )
        );
    }
}
