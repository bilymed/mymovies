package tuto.mymovies.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tuto.mymovies.entities.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AuthService authService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = authService.findUserByUserName(username);
		if (user == null)
			throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> autorities = new ArrayList<>();
		user.getRoles().forEach(role->{
			autorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});

		return new User(user.getUsername(), user.getPassword(), autorities);
	}

}
