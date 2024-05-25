package org.example.controller;

import org.example.entity.Actor;
import org.example.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/by-movie/{title}")
    public ResponseEntity<List<Actor>> getActorsByMovieTitle(@PathVariable String title) {
        List<Actor> actors = actorService.findActorsByMovieTitle(title);
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/with-at-least-n-movies/{number}")
    public ResponseEntity<List<Actor>> getActorsWithAtLeastNMovies(@PathVariable int number) {
        List<Actor> actors = actorService.findActorsWithAtLeastNMovies(number);
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/also-directors")
    public ResponseEntity<List<Actor>> getActorsWhoAreAlsoDirectors() {
        List<Actor> actors = actorService.findActorsWhoAreAlsoDirectors();
        return ResponseEntity.ok(actors);
    }
}