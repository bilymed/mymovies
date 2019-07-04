package tuto.mymovies;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import tuto.mymovies.entities.AppRole;
import tuto.mymovies.entities.AppUser;
import tuto.mymovies.entities.Movie;
import tuto.mymovies.repositories.MovieRepository;
import tuto.mymovies.repositories.UserRepository;
import tuto.mymovies.services.AuthService;

@SpringBootApplication
public class MymoviesApplication implements CommandLineRunner {

	@Autowired
	MovieRepository movieRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(MymoviesApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {

		authService.saveUser(new AppUser(null, "omar", "password", null));
		authService.saveUser(new AppUser(null, "anas", "password", null));
		authService.saveRole(new AppRole(null, "ADMIN"));
		authService.saveRole(new AppRole(null, "USER"));
		authService.addRoleToUser("omar", "ADMIN");
		authService.addRoleToUser("omar", "USER");
		authService.addRoleToUser("anas", "USER");

		Stream.of("Movie1", "Movie2", "Movie3").forEach(name -> {
			movieRepository.save(new Movie(null, name));
		});

		movieRepository.findAll().forEach(movie -> {
			System.out.println(movie.getName());
		});

		userRepository.findAll().forEach(user -> {
			System.out.println(user.getUsername() + " " + user.getPassword());
		});

	}

}
