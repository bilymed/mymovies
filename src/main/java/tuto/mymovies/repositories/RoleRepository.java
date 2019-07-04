package tuto.mymovies.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tuto.mymovies.entities.AppRole;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
	
	public AppRole findByRoleName(String roleName);

}
