package com.StreamTube.configs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.StreamTube.models.User;
import com.StreamTube.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> optionalUser = userRepository.findByUsername(username);

		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException("User not found");
		}

		User user = optionalUser.get();

		return new CustomUserDetails(user);
	}

}