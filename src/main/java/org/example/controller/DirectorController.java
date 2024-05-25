package org.example.controller;

import org.example.entity.Director;
import org.example.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorService directorService;

    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/also-actors")
    public ResponseEntity<List<Director>> getDirectorsWhoAreAlsoActors() {
        List<Director> directors = directorService.findDirectorsWhoAreAlsoActors();
        return ResponseEntity.ok(directors);
    }

    @GetMapping
    public ResponseEntity<List<Director>> getAllDirectors() {
        List<Director> directors = directorService.findAllDirectors();
        return ResponseEntity.ok(directors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable Long id) {
        Director director = directorService.findDirectorById(id);
        return ResponseEntity.ok(director);
    }

    @PostMapping
    public ResponseEntity<Director> createDirector(@RequestBody Director director) {
        Director savedDirector = directorService.saveDirector(director);
        return ResponseEntity.ok(savedDirector);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable Long id, @RequestBody Director director) {
        Director updatedDirector = directorService.updateDirector(id, director);
        return ResponseEntity.ok(updatedDirector);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable Long id) {
        directorService.deleteDirector(id);
        return ResponseEntity.ok().build();
    }
}
