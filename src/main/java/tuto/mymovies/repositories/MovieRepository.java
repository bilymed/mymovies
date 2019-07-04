package tuto.mymovies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tuto.mymovies.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

}
