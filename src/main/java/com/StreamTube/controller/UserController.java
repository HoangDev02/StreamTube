package com.StreamTube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StreamTube.configs.CustomUserDetails;
import com.StreamTube.dtos.UserDTO;
import com.StreamTube.mappers.UserMapper;
import com.StreamTube.models.User;
import com.StreamTube.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
		User currentUser = currentUserDetails.getUser();
		
		System.out.println(id);

		System.out.println(currentUser.getId());
		User targetUser = userService.getUserById(id);

		if (userService.isOwner(targetUser.getId(), currentUser.getId())) {
			UserDTO userDTO = userMapper.toDTO(userService.getFullUserDetails(targetUser));
			userDTO.setOwner(true);
			return ResponseEntity.ok(userDTO);
		} else {
			UserDTO userDTO = userMapper.toDTO(userService.getSafeUserDetails(targetUser));
			userDTO.setOwner(false);
			return ResponseEntity.ok(userDTO);
		}
	}

}
