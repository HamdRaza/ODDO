package com.tom.chef.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaUtilities {

    public static boolean passwordCharValidation(String passwordEd) {
        Matcher matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z]).{4,20})").matcher(passwordEd);
        return matcher.matches();
    }

}
