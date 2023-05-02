package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class AccountBlockedException extends Exception {

    @Override
    public String getMessage() {
        return Values.ACCOUNT_BLOCKED_MESSAGE;
    }

}
