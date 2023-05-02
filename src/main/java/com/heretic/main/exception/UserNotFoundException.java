package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class UserNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return Values.USER_NOT_FOUND_MESSAGE;
    }

}
