package com.kmarcee.bankocr.business.util;

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.emptyList;

public class Utils {

    private Utils() {}

    public static List<String> getContentAsLines(String fileContent) {
        List<String> lines;
        if (StringUtils.isEmpty(fileContent)) {
            lines = emptyList();
        } else {
            lines = new LinkedList<>(Arrays.asList(fileContent.split("\n")));
            lines.add("");
        }
        return lines;
    }
}
