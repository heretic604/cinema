package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class MovieAlreadyExistException extends Exception{

    @Override
    public String getMessage() {
        return Values.MOVIE_EXIST;
    }

}
