package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class UsernameNotAvailableException extends Exception {

    @Override
    public String getMessage() {
        return Values.USERNAME_NOT_AVAILABLE_MESSAGE;
    }

}
