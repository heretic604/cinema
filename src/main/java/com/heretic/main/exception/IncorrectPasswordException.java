package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class IncorrectPasswordException extends Exception {

    @Override
    public String getMessage() {
        return Values.INCORRECT_PASSWORD_MESSAGE;
    }

}
