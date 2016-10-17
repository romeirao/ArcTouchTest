package br.com.italoromeiro.arctouchtest.utils;

/**
 * Created by italo on 09/08/16.
 */
public class FormatterUtils {
    public static String cleanString(String str) {
        if (str == null) {
            return "";
        }

        str = str.replaceAll("\\s{2,}", " ");
        return str.trim();
    }
}
