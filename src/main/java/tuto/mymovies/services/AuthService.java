package tuto.mymovies.services;

import tuto.mymovies.entities.AppRole;
import tuto.mymovies.entities.AppUser;

public interface AuthService {
	
	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username, String rolename);
	public AppUser findUserByUserName(String username);

}
