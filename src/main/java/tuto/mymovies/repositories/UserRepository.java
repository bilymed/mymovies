package tuto.mymovies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tuto.mymovies.entities.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
	
	public AppUser findByUsername(String username);

}
