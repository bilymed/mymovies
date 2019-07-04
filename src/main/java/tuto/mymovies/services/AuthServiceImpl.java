package tuto.mymovies.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tuto.mymovies.entities.AppRole;
import tuto.mymovies.entities.AppUser;
import tuto.mymovies.repositories.RoleRepository;
import tuto.mymovies.repositories.UserRepository;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncode;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;

	@Override
	public AppUser saveUser(AppUser user) {
		String password = bCryptPasswordEncode.encode(user.getPassword());
		user.setPassword(password);
		return userRepository.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user = userRepository.findByUsername(username);		
		AppRole role = roleRepository.findByRoleName(roleName);		
		user.getRoles().add(role);	
	}

	@Override
	public AppUser findUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}

}
