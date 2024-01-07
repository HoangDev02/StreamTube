package com.StreamTube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.StreamTube.configs.CustomUserDetails;
import com.StreamTube.dtos.ChannelDTO;
import com.StreamTube.mappers.ChannelMapper;
import com.StreamTube.models.Channel;
import com.StreamTube.models.User;
import com.StreamTube.services.ChannelService;

import jakarta.persistence.criteria.CriteriaBuilder.In;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {
	@Autowired
	private ChannelService channelService;

	@Autowired
	private ChannelMapper channelMapper;

	@GetMapping
	public List<ChannelDTO> getAllChannel() {
		return channelMapper.toDTOList(channelService.getAllChannel());
	}

	private User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails currentUserDetails = (CustomUserDetails) authentication.getPrincipal();
		return currentUserDetails.getUser();
	}

	@PostMapping("/create")
	public ResponseEntity<String> createChannel(@RequestBody ChannelDTO channelDTO) {
		if (channelDTO == null) {
			return ResponseEntity.badRequest().body("Channel data is missing");
		}
		try {
			User currentUser = getCurrentUser();
			Channel channel = channelMapper.toModel(channelDTO);
			channel.setCreator(currentUser);
			channelService.createChannel(channel);
			return ResponseEntity.ok("Create channel Success");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating channel");
		}
	}

	@GetMapping("/user/{userId}")
	public List<ChannelDTO> getAllChannelsByUserId(@PathVariable("userId") Integer userId) {
		User currentUser = getCurrentUser();
		Integer currentUserId = currentUser.getId();
		List<ChannelDTO> channelDTOs = channelMapper.toDTOList(channelService.findByCreatorId(userId));
		for (ChannelDTO channelDTO : channelDTOs) {
			channelDTO.setOwner(userId.equals(currentUserId));
		}
		return channelDTOs;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ChannelDTO> getChannelbyId(@PathVariable("id") Integer id) {
		User currentUser = getCurrentUser();
		Integer userId = currentUser.getId();
		boolean isOwner = channelService.checkOwnership(id, userId);
		ChannelDTO channelDTO = channelMapper.toDTO(channelService.getChannelById(id));
		channelDTO.setOwner(isOwner);
		return ResponseEntity.ok(channelDTO);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ChannelDTO> updatecategory(@PathVariable("id") Integer id,
			@RequestBody ChannelDTO channelDTO) {
//		User currentUser = getCurrentUser();
		Channel updateChannel = channelService.updateChannel(id, channelMapper.toModel(channelDTO));
		ChannelDTO updateDTO = channelMapper.toDTO(updateChannel);
		return ResponseEntity.ok(updateDTO);
	}
}
