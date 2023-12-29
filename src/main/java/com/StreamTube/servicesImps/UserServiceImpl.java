package com.StreamTube.servicesImps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.StreamTube.models.Roles;
import com.StreamTube.models.User;
import com.StreamTube.models.UserRoleId;
import com.StreamTube.models.UserRoles;
import com.StreamTube.repositories.RolesRepository;
import com.StreamTube.repositories.UserRepository;
import com.StreamTube.services.UserRolesService;
import com.StreamTube.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private UserRolesService userRolesService;

	@Override
	public User getUserById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) {
		String userName = user.getUsername();
		boolean exists = userRepository.existsByUsername(userName);

		if (exists) {
			throw new RuntimeException("Username already exists");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("USER");
		user.setEnabled(true);
		User savedUser = userRepository.save(user);

		UserRoles userRoles = new UserRoles();
		UserRoleId userRoleId = new UserRoleId();
		userRoleId.setUserId(savedUser.getId());

		Roles role = rolesRepository.findByName("USER");
		userRoleId.setRoleId(role.getId());

		userRoles.setId(userRoleId);
		userRoles.setUser(savedUser);
		userRoles.setRole(role);

		userRolesService.createUserRoles(userRoles);

		return savedUser;
	}

	@Override
	public User updateUser(Integer id, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public User getFullUserDetails(User targetUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getSafeUserDetails(User targetUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOwner(Integer userId, Integer targetUserId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getIdUserByUsername(String username) {
		// TODO Auto-generated method stub
		return 0;
	}

}
