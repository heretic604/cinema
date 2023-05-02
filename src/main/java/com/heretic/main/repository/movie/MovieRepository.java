package com.heretic.main.repository.movie;

import com.heretic.main.model.movie.Movie;

import java.util.List;

/**
 * Movie database interaction
 */
public interface MovieRepository {

    /**
     * Adds movie to database
     */
    boolean create(Movie movie);

    /**
     * View all movies
     */
    List<Movie> getAll();

    /**
     * Get movie by id
     */
    Movie get(Long id);

    /**
     * Get movie by name (unique field)
     */
    Movie get(String name);

    /**
     * Removes movie from database
     */
    Movie delete(Long id);

}
