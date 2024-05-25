package org.example.repository;

import org.example.entity.Movie;
import org.example.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findMoviesByReleaseYear(int year);

    @Query("SELECT m FROM Movie m WHERE m.releaseDate >= :start AND m.releaseDate <= :end")
    List<Movie> findMoviesReleasedBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m.title = :title")
    List<Actor> findActorsByMovieTitle(@Param("title") String title);

    @Query("SELECT a FROM Actor a WHERE SIZE(a.movies) >= :count")
    List<Actor> findActorsWithAtLeastNMovies(@Param("count") int count);

    @Query("SELECT a FROM Actor a WHERE a.id IN (SELECT d.id FROM Director d)")
    List<Actor> findActorsWhoAreAlsoDirectors();

    @Modifying
    @Transactional
    @Query("DELETE FROM Movie m WHERE m.releaseDate < :threshold")
    void deleteMoviesOlderThan(@Param("threshold") LocalDate threshold);
}
