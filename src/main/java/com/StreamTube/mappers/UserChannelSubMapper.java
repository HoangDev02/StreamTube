package com.StreamTube.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.StreamTube.dtos.UserChannelSubDTO;
import com.StreamTube.models.UserChannelSub;
import com.StreamTube.services.ChannelService;
import com.StreamTube.services.UserService;



@Component
public class UserChannelSubMapper {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ChannelService channelService;
	
	public  UserChannelSubDTO toDTO(UserChannelSub model) {
		UserChannelSubDTO dto = new UserChannelSubDTO();
		dto.setId(model.getId());
		dto.setUserId(model.getUser().getId());
		dto.setChannelId(model.getChannel().getId());
		dto.setSubscribeDate(model.getSubscribeDate());
		return dto;
	}

	public  List<UserChannelSubDTO> toDTOList(List<UserChannelSub> models) {
		return models.stream().map(cat -> toDTO(cat)).collect(Collectors.toList());
	}
	
	public  UserChannelSub toModel(UserChannelSubDTO dto) {
		UserChannelSub model = new UserChannelSub();
		model.setId(dto.getId());
		model.setUser(userService.getUserById(dto.getUserId()));
		model.setChannel(channelService.getChannelById(dto.getChannelId()));
		model.setSubscribeDate(dto.getSubscribeDate());
		return model;
	}
}