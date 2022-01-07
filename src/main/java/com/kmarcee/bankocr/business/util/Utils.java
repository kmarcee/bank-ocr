package com.kmarcee.bankocr.business.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    private Utils() {}

    public static List<String> getContentAsLines(String fileContent) {
        List<String> lines = new LinkedList<>(Arrays.asList(fileContent.split("\n")));
        lines.add("");
        return lines;
    }
}
