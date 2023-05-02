package com.heretic.main.model.movie;

import com.heretic.main.util.Values;

import java.util.Objects;

public class Movie {

    private Long id;
    private final String name;
    private final double IMDb;
    private final int ageLimit;
    private final String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format(Values.SAMPLE_MOVIE, name, IMDb, ageLimit, id);
    }

    public Movie(Long id, String name, double IMDb, int ageLimit, String description) {
        this.id = id;
        this.name = name;
        this.IMDb = IMDb;
        this.ageLimit = ageLimit;
        this.description = description;
    }

    public Movie(String name, double IMDb, int ageLimit, String description) {
        this.name = name;
        this.IMDb = IMDb;
        this.ageLimit = ageLimit;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getIMDb() {
        return IMDb;
    }

    public String getDescription() {
        return description;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

}
