package com.sap.cc.movies;

import java.util.List;
import java.util.Optional;

public class MovieStorageaStub implements MovieStorage{
    @Override
    public Movie save(Movie movie) {
        return null;
    }

    @Override
    public Optional<Movie> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Movie> getAll() {
        return MovieFixtures.MOVIES;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(Long id) {

    }
}
