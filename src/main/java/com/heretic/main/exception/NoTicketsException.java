package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class NoTicketsException extends Exception{

    @Override
    public String getMessage() {
        return Values.TICKET_NOT_FOUND;
    }

}
