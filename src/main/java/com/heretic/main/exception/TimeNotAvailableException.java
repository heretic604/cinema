package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class TimeNotAvailableException extends Exception{

    @Override
    public String getMessage() {
        return Values.TIME_NOT_AVAILABLE;
    }

}
