package com.heretic.main.model.session;

import com.heretic.main.model.movie.Movie;
import com.heretic.main.util.Values;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private Long id;
    private LocalDateTime time;
    private Movie movie;
    private int price;

    @Override
    public String toString() {
        String date = time.toLocalDate().toString();
        String hours = time.toLocalTime().toString();
        return String.format(Values.SAMPLE_SESSION, date, hours, movie.getName(), movie.getAgeLimit(), price, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Session(Long id, LocalDateTime time, Movie movie, int price) {
        this.id = id;
        this.time = time;
        this.movie = movie;
        this.price = price;
    }

    public Session(LocalDateTime time, Movie movie, int price) {
        this.time = time;
        this.movie = movie;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
