package org.example.repository;

import org.example.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DirectorRepository extends JpaRepository<Director, Long> {

    @Query("SELECT d FROM Director d WHERE d.movies IS NOT EMPTY")
    List<Director> findDirectorsWithMovies();

    @Query("SELECT d FROM Director d JOIN d.movies m WHERE m.releaseDate BETWEEN :start AND :end")
    List<Director> findDirectorsByMovieReleaseDate(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT d FROM Director d WHERE d.fullName = :fullName")
    List<Director> findDirectorsByName(@Param("fullName") String fullName);

    @Query("SELECT d FROM Director d WHERE d.birthDate = :birthDate")
    List<Director> findDirectorsByBirthDate(@Param("birthDate") LocalDate birthDate);

}