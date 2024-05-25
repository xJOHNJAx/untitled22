package org.example.service;

import org.example.entity.Actor;
import org.example.entity.Director;
import org.example.repository.ActorRepository;
import org.example.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository, ActorRepository actorRepository) {
        this.directorRepository = directorRepository;
        this.actorRepository = actorRepository;
    }

    public List<Director> findAllDirectors() {
        return directorRepository.findAll();
    }

    public Director findDirectorById(Long id) {
        Optional<Director> director = directorRepository.findById(id);
        return director.orElse(null);
    }

    public Director saveDirector(Director director) {
        return directorRepository.save(director);
    }

    public Director updateDirector(Long id, Director newDirectorData) {
        return directorRepository.findById(id)
                .map(director -> {
                    director.setFullName(newDirectorData.getFullName());
                    director.setBirthDate(newDirectorData.getBirthDate());
                    return directorRepository.save(director);
                })
                .orElseGet(() -> {
                    newDirectorData.setId(id);
                    return directorRepository.save(newDirectorData);
                });
    }

    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }
    public List<Director> findDirectorsWhoAreAlsoActors() {
        List<Director> allDirectors = directorRepository.findAll();
        List<Actor> allActors = actorRepository.findAll();

        return allDirectors.stream()
                .filter(director -> allActors.stream()
                        .anyMatch(actor -> actor.getFullName().equals(director.getFullName())))
                .collect(Collectors.toList());
    }
}