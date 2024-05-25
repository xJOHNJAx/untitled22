package org.example.service;

import org.example.entity.Actor;
import org.example.entity.Movie;
import org.example.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> findActorsByMovieTitle(String title) {
        return actorRepository.findAll().stream()
                .filter(actor -> actor.getMovies().stream()
                        .anyMatch(movie -> movie.getTitle().equals(title)))
                .collect(Collectors.toList());
    }

    public List<Actor> findActorsWithAtLeastNMovies(int minMovies) {
        return actorRepository.findAll().stream()
                .filter(actor -> actor.getMovies().size() >= minMovies)
                .collect(Collectors.toList());
    }

    public List<Actor> findActorsWhoAreAlsoDirectors() {
        return actorRepository.findAll().stream()
                .filter(actor -> actor.getDirectedMovies() != null && !actor.getDirectedMovies().isEmpty())
                .collect(Collectors.toList());
    }

    public void deleteMoviesOlderThanYears(int years) {
        LocalDate cutoffDate = LocalDate.now().minusYears(years);
        actorRepository.findAll().forEach(actor -> {
            List<Movie> recentMovies = actor.getMovies().stream()
                    .filter(movie -> movie.getReleaseDate().isAfter(cutoffDate))
                    .collect(Collectors.toList());
            actor.setMovies((Set<Movie>) recentMovies);
            actorRepository.save(actor);
        });
    }

    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public Actor updateActor(Long id, Actor newActorData) {
        return actorRepository.findById(id)
                .map(actor -> {
                    actor.setFullName(newActorData.getFullName());
                    actor.setBirthDate(newActorData.getBirthDate());
                    return actorRepository.save(actor);
                })
                .orElseGet(() -> {
                    newActorData.setId(id);
                    return actorRepository.save(newActorData);
                });
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}