package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class IncorrectUsernameException extends Exception {

    @Override
    public String getMessage() {
        return Values.INCORRECT_USERNAME_MESSAGE;
    }

}
