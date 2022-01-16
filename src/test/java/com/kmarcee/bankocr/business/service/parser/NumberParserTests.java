package com.kmarcee.bankocr.business.service.parser;

import com.kmarcee.bankocr.business.exception.parsing.LineNumberMismatchException;
import com.kmarcee.bankocr.business.exception.validation.IllegalCharacterException;
import com.kmarcee.bankocr.business.exception.validation.IllegalMultiByteCharacterException;
import com.kmarcee.bankocr.business.exception.validation.InvalidLineLengthException;
import com.kmarcee.bankocr.business.model.BankAccountNumber;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberParserTests {

    NumberParser numberParser = new BankAccountParser();

    @Test
    void parse_contentIsNull_emptyListReturned() {
        List<BankAccountNumber> bankAccountNumbers = numberParser.parse(null);

        assertThat(bankAccountNumbers, empty());
    }

    @Test
    void parse_contentIsEmpty_emptyListReturned() {
        List<BankAccountNumber> bankAccountNumbers = numberParser.parse("");

        assertThat(bankAccountNumbers, empty());
    }

    @Test
    void parse_contentHasInsufficientLinesForABankAccountEntry_emptyResult() {
        List<BankAccountNumber> bankAccountNumbers = numberParser.parse("|\n");

        assertThat(bankAccountNumbers, empty());
    }

    @Test
    void parse_contentHasOneValidEntryAndASecondEntryWithEmptyLineWithinEntry_onlyTheFirstIsParsed() {
        List<BankAccountNumber> bankAccountNumbers = numberParser.parse(
                "    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|\n\n" +
                        "    _  _     _  _  _  _  _ \n\n\n  ||_  _|  | _||_|  ||_| _|\n\n"
        );

        assertThat(bankAccountNumbers, hasSize(1));
        assertThat(bankAccountNumbers.get(0).print(), is("123456789"));
    }

    @Test
    void parse_contentHasNotBlankDelimiterLine_exceptionThrown() {
        assertThrows(
                LineNumberMismatchException.class,
                () -> numberParser.parse(
                "    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|\n" +
                        "something" +
                        "    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|\n\n"
                )
        );
    }

    @Test
    void parse_contentHasInsufficientLineLengthForABankAccountEntry_exceptionThrown() {
        assertThrows(InvalidLineLengthException.class, () -> numberParser.parse("|_\n  \n_|\n\n"));
    }

    @Test
    void parse_contentHasIllegalCharacter_exceptionThrown() {
        assertThrows(
                IllegalCharacterException.class,
                () -> numberParser.parse(
                        "A   _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|\n\n"
                )
        );
    }

    @Test
    void parse_contentHasIllegalMultiByteCharacter_exceptionThrown() {
        assertThrows(
                IllegalMultiByteCharacterException.class,
                () -> numberParser.parse(
                        "Ł   _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|\n\n"
                )
        );
    }

    @Test
    void parse_hasParseableContentWithNonDigitFigures_unknownFiguresAreExtracted() {
        List<BankAccountNumber> bankAccountNumbers = numberParser.parse(
                "|||||||||_________|||||||||\n|||||||||_________|||||||||\n|||||||||_________|||||||||\n\n"
        );

        assertThat(bankAccountNumbers, hasSize(1));
        assertThat(bankAccountNumbers.get(0).print(), is("?????????"));
    }

    @Test
    void parse_hasParseableContentWithValidDigitFigures_figuresAreExtracted() {
        List<BankAccountNumber> bankAccountNumbers = numberParser.parse(
                "    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|\n\n"
        );

        assertThat(bankAccountNumbers, hasSize(1));
        assertThat(bankAccountNumbers.get(0).print(), is("123456789"));
    }

    @Test
    void parse_hasTwoValidEntries_figuresAreExtracted() {
        List<BankAccountNumber> bankAccountNumbers = numberParser.parse(
                "    _  _     _  _  _  _  _ \n  | _| _||_||_ |_   ||_||_|\n  ||_  _|  | _||_|  ||_| _|\n\n" +
                        " _  _  _     _  _  _  _  _ \n| | _| _||_||_ |_   ||_||_|\n|_||_  _|  | _||_|  ||_| _|\n\n"
        );

        assertThat(bankAccountNumbers, hasSize(2));
        assertThat(bankAccountNumbers.get(0).print(), is("123456789"));
        assertThat(bankAccountNumbers.get(1).print(), is("023456789"));
    }
}
