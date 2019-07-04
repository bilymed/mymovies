package tuto.mymovies.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tuto.mymovies.entities.Movie;
import tuto.mymovies.repositories.MovieRepository;

@RestController
public class MovieRestController {

	private MovieRepository movieRepository;

	public MovieRestController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@GetMapping("/movies")
	public List<Movie> findAll() {
		return movieRepository.findAll();
	}
	
	@PostMapping("/movies")
	public Movie save(@RequestBody  Movie movie) {
		return movieRepository.save(movie);
	}

}
