package com.jobvxs.bl.tools;

import java.util.Random;

/**
 * Class to Generate Code for the software
 */
public class GenerateCode {

    /**
     * Generate code for check register
     * @return String code
     */
    public static String generate() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
