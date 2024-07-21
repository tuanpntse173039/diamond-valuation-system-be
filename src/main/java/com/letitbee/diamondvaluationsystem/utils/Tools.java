package com.letitbee.diamondvaluationsystem.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    public static String generateId(int length) {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < length; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public static String extractNumber(String input) {
        Pattern pattern = Pattern.compile("-?\\$?\\d+(,\\d{3})*(\\.\\d+)?");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group().replaceAll("[^\\d.]", "");
        }

        return null;
    }

}
