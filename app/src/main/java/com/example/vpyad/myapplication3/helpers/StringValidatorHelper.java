package com.example.vpyad.myapplication3.helpers;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by vpyad on 07-Jan-18.
 */

public class StringValidatorHelper {

    private final static String LEGAL_CHARS_IN_FILE_NAME = "[a-zA-Zа-яА-Я0-9'{}':\",./?!@#$%&*()_+=-]*";
    private final static String ALL_LETTERS = "^[a-zA-Zа-яА-Я]+$";
    private final static String ALL_DIGITS = "^[0-9]+$";
    private final static String ALL_LETTERS_AND_DIGITS = "^[a-zA-Zа-яА-Я0-9]+$";

    public static boolean filenameLegal(String s) {
        return Pattern.matches(LEGAL_CHARS_IN_FILE_NAME, s);
    }

    public static boolean allLetters(String s){
        return Pattern.matches(ALL_LETTERS, s);
    }

    public static boolean allDigits(String s){
        return Pattern.matches(ALL_DIGITS, s);
    }

    public static boolean allLettersAndDigits(String s){
        return Pattern.matches(ALL_LETTERS_AND_DIGITS, s);
    }
}
