package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class AgeLimitException extends Exception{

    @Override
    public String getMessage() {
        return Values.AGE_LIMIT_MESSAGE;
    }

}
