package com.heretic.main.exception;

import com.heretic.main.util.Values;

public class NoMovieException extends Exception {

    @Override
    public String getMessage() {
        return Values.NO_MOVIE_MESSAGE;
    }

}
