package org.example.controller;

import org.example.entity.Actor;
import org.example.entity.Movie;
import org.example.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/recent/{years}")
    @ResponseBody
    public List<Movie> getRecentMovies(@PathVariable int years) {
        return movieService.findRecentMovies(years);
    }

    @GetMapping("/actors/{title}")
    @ResponseBody
    public List<Actor> getActorsByMovieTitle(@PathVariable String title) {
        return movieService.findActorsByMovieTitle(title);
    }

    @GetMapping("/actors/movies/{count}")
    @ResponseBody
    public List<Actor> getActorsWithAtLeastNMovies(@PathVariable int count) {
        return movieService.findActorsWithAtLeastNMovies(count);
    }

    @GetMapping("/actors/directors")
    @ResponseBody
    public List<Actor> getActorsWhoAreAlsoDirectors() {
        return movieService.findActorsWhoAreAlsoDirectors();
    }

    @DeleteMapping("/delete/{years}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOldMovies(@PathVariable int years) {
        movieService.deleteOldMovies(years);
    }
}
