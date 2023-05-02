package com.heretic.main.service.movie;

import com.heretic.main.exception.MovieAlreadyExistException;
import com.heretic.main.model.movie.Movie;

import java.util.List;

/**
 * Movie interaction
 */
public interface MovieService {

    /**
     * Adds movie
     */
    boolean create(Movie movie) throws MovieAlreadyExistException;

    /**
     * Get all movies
     */
    List<Movie> getAll();

    /**
     * Get movie by id
     */
    Movie get(Long id);

    /**
     * Get movie by name
     */
    Movie get(String name);

    /**
     * Removes movie
     */
    Movie delete(Long id);

}
