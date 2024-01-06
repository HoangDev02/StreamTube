package com.StreamTube.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.StreamTube.dtos.ChannelDTO;
import com.StreamTube.models.Channel;
import com.StreamTube.services.UserService;


@Component
public class ChannelMapper {
	@Autowired
	private UserService userService;
	
	public ChannelDTO toDTO(Channel model) {
		ChannelDTO dto = new ChannelDTO();
		dto.setId(model.getId());
		dto.setName(model.getName());
		dto.setCreateDate(model.getCreateDate());
		dto.setDescription(model.getDescription());
		dto.setCreatorId(model.getCreator().getId());
		
		return dto;
	}
	public  List<ChannelDTO> toDTOList(List<Channel> models) {
		return models.stream().map(cat -> toDTO(cat)).collect(Collectors.toList());
	}
	public  Channel toModel(ChannelDTO dto) {
		Channel model = new Channel();
		model.setId(dto.getId());
		model.setName(dto.getName());
		model.setDescription(dto.getDescription());
		model.setCreateDate(dto.getCreateDate());
		model.setCreator(userService.getUserById(dto.getCreatorId()));
		return model;
	}
}
