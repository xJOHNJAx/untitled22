package org.example.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "actors")
    private Set<Movie> movies = new HashSet<>();

    @OneToMany(mappedBy = "director")
    private Set<Movie> directedMovies = new HashSet<>();

    public Actor() {
    }

    public Actor(String fullName, LocalDate birthDate) {
        this.fullName = fullName;
        this.birthDate = birthDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    public Set<Movie> getDirectedMovies() {
        return directedMovies;
    }

    public void setDirectedMovies(Set<Movie> directedMovies) {
        this.directedMovies = directedMovies;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getActors().add(this);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getActors().remove(this);
    }

    public void addDirectedMovie(Movie movie) {
        this.directedMovies.add(movie);
        if (this instanceof Director) {
            movie.setDirector((Director) this);
        }
    }

    public void removeDirectedMovie(Movie movie) {
        this.directedMovies.remove(movie);
        if (movie.getDirector().equals(this)) {
            movie.setDirector(null);
        }
    }
}