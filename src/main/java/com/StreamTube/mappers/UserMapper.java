package com.StreamTube.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.StreamTube.dtos.UserBasicInfor;
import com.StreamTube.dtos.UserDTO;
import com.StreamTube.models.User;

@Component
public class UserMapper {

	public UserDTO toDTO(User model) {
		UserDTO dto = new UserDTO();
		dto.setId(model.getId());
		dto.setUsername(model.getUsername());
		dto.setPassword(model.getPassword());
		dto.setEmail(model.getEmail());
		dto.setJoinDate(model.getJoinDate());
		dto.setRole(model.getRole());
		dto.setEnabled(model.isEnabled());
		return dto;
	}

	public UserBasicInfor toBasicDTO(User model) {
		UserBasicInfor dto = new UserBasicInfor();

		dto.setUsername(model.getUsername());
		dto.setEmail(model.getEmail());
		dto.setJoinDate(model.getJoinDate());

		return dto;
	}

	public List<UserDTO> toDTOList(List<User> models) {
		return models.stream().map(cat -> toDTO(cat)).collect(Collectors.toList());
	}

	public User toModel(UserDTO dto) {
		User model = new User();
		model.setId(dto.getId());
		model.setUsername(dto.getUsername());
		model.setPassword(dto.getPassword());
		model.setEmail(dto.getEmail());
		model.setJoinDate(dto.getJoinDate());
		model.setRole(dto.getRole());
		model.setEnabled(dto.isEnabled());
		return model;
	}
}