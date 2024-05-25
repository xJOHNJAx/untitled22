package org.example.service;

import org.example.entity.Actor;
import org.example.entity.Movie;
import org.example.repository.ActorRepository;
import org.example.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    public List<Movie> findRecentMovies(int years) {
        LocalDate start = LocalDate.now().minusYears(years);
        LocalDate end = LocalDate.now();
        return movieRepository.findMoviesReleasedBetween(start, end);
    }

    public List<Actor> findActorsByMovieTitle(String title) {
        return movieRepository.findActorsByMovieTitle(title);
    }

    public List<Actor> findActorsWithAtLeastNMovies(int count) {
        return actorRepository.findActorsWithAtLeastNMovies(count);
    }

    public List<Actor> findActorsWhoAreAlsoDirectors() {
        return actorRepository.findActorsWhoAreAlsoDirectors();
    }

    public void deleteOldMovies(int years) {
        LocalDate threshold = LocalDate.now().minusYears(years);
        movieRepository.deleteMoviesOlderThan(threshold);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, Movie newMovieData) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(newMovieData.getTitle());
                    movie.setReleaseDate(newMovieData.getReleaseDate());
                    movie.setActors(newMovieData.getActors());
                    return movieRepository.save(movie);
                })
                .orElseGet(() -> {
                    newMovieData.setId(id);
                    return movieRepository.save(newMovieData);
                });
    }

    public void deleteMoviesOlderThanYears(int years) {
        LocalDate cutoffDate = LocalDate.now().minusYears(years);
        actorRepository.findAll().forEach(actor -> {
            Set<Movie> recentMovies = actor.getMovies().stream()
                    .filter(movie -> movie.getReleaseDate().isAfter(cutoffDate))
                    .collect(Collectors.toSet());
            actor.setMovies(recentMovies);
            actorRepository.save(actor);
        });
    }
}
