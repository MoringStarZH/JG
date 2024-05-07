package com.example.jg.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @title StringUtil
 * @Author: ZKY
 * @CreateTime: 2024-05-06  11:03
 * @Description: TODO
 */
public class StringUtil {
    public static String parse(String text) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matcher = pattern.matcher(text);
        StringBuilder stringBuilder = new StringBuilder();

        while (matcher.find()) {
            stringBuilder.append(matcher.group());
        }
        return stringBuilder.toString();
    }
}
