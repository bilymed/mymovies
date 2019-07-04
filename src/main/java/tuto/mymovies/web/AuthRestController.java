package tuto.mymovies.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tuto.mymovies.entities.AppUser;
import tuto.mymovies.services.AuthService;

@RestController
@CrossOrigin
public class AuthRestController {

	@Autowired
	AuthService authService;

	@PostMapping("/signup")
	public AppUser signUp(@RequestBody AppUser user) {
		if (!user.getPassword().equals(user.getConfirmPassword()))
			throw new RuntimeException("Passwords do not matches ");
		AppUser userdb = authService.findUserByUserName(user.getUsername());
		if (userdb != null)
			throw new RuntimeException("User already Exists");
		authService.saveUser(user);
		authService.addRoleToUser(user.getUsername(), "USER");
		/*
		 * AppUser user2 = new AppUser(user.get); user2 = user;
		 * authService.addRoleToUser(user2.getUsername(), "User");
		 */
		return user;
	}
}
