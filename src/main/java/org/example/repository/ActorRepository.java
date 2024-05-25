package org.example.repository;

import org.example.entity.Actor;
import org.example.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    @Query("SELECT a FROM Actor a WHERE SIZE(a.movies) >= :minMovies")
    List<Actor> findActorsWithAtLeastNМovies(@Param("minMovies") int minMovies);

    @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m.title = :title")
    List<Actor> findActorsByMovieTitle(@Param("title") String title);

    @Query("SELECT a FROM Actor a WHERE a.id IN (SELECT d.id FROM Director d)")
    List<Actor> findActorsWhoAreAlsoDirectors();

    @Query("SELECT m FROM Movie m WHERE m.releaseDate BETWEEN :start AND :end")
    List<Movie> findMoviesReleasedBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT m FROM Movie m WHERE m.releaseDate > :date")
    List<Movie> findMoviesReleasedAfter(@Param("date") LocalDate date);

    @Query("SELECT a FROM Actor a WHERE SIZE(a.movies) >= :minMovies")
    List<Actor> findActorsWithAtLeastNMovies(@Param("minMovies") int minMovies);
}