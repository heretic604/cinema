package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class IncorrectEmailException extends Exception {

    @Override
    public String getMessage() {
        return Values.INCORRECT_EMAIL_MESSAGE;
    }

}
