package com.StreamTube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StreamTube.dtos.UserDTO;
import com.StreamTube.mappers.UserMapper;
import com.StreamTube.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users/{id}")
	public UserDTO adminGetUserById(@PathVariable("id") Integer id) {
	    return UserMapper.toDTO(userService.getUserById(id));
	}

	
	@Secured("ROLE_ADMIN")
	@GetMapping("/users")
	public List<UserDTO> adminGetAllUser() {
		return UserMapper.toDTOList(userService.getAllUsers());
	}
}
