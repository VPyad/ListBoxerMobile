package com.example.vpyad.myapplication3.helpers;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by vpyad on 07-Jan-18.
 */

public class StringValidatorHelper {

    private final static String LEGAL_CHARS_IN_FILE_NAME = "[a-zA-Zа-яА-Я0-9'{}':\",./?!@#$%&*()_+=-]*";

    public static boolean filenameLegal(String s) {
        return Pattern.matches(LEGAL_CHARS_IN_FILE_NAME, s);
    }
}
