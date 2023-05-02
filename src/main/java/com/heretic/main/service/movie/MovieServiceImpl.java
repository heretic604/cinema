package com.heretic.main.service.movie;

import com.heretic.main.exception.MovieAlreadyExistException;
import com.heretic.main.model.movie.Movie;
import com.heretic.main.repository.movie.MovieRepository;
import lombok.Builder;

import java.util.List;

@Builder
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public boolean create(Movie movie) throws MovieAlreadyExistException {

        if (movieRepository.get(movie.getName()) == null) {
            return movieRepository.create(movie);
        } else throw new MovieAlreadyExistException();

    }

    @Override
    public List<Movie> getAll() {
        return movieRepository.getAll();
    }

    @Override
    public Movie get(Long id) {
        return movieRepository.get(id);
    }

    @Override
    public Movie get(String name) {
        return movieRepository.get(name);
    }

    @Override
    public Movie delete(Long id) {
        return movieRepository.delete(id);
    }

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

}
