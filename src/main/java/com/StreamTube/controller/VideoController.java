package com.StreamTube.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.StreamTube.configs.CustomUserDetails;
import com.StreamTube.configs.SecurityUtils;
import com.StreamTube.dtos.VideoDTO;
import com.StreamTube.mappers.VideoMapper;
import com.StreamTube.models.User;
import com.StreamTube.models.Video;
import com.StreamTube.services.ChannelService;
import com.StreamTube.services.VideoService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/video")
public class VideoController {
	
	@Autowired
	private VideoMapper videoMapper;
	
	@Autowired
	private VideoService videoService;
	@Autowired
	private ChannelService channelService;
	@GetMapping
	public List<VideoDTO> getAllVideo() {
		return videoMapper.toDTOList(videoService.getAllVideos());
		
	}
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadVideo(@RequestPart("file") MultipartFile file,
			@RequestPart("videoDTO") VideoDTO videoDTO) throws IOException {
		User currentUser = SecurityUtils.getCurrentUser();
//		User currentUser = currentUserDetails.getUser();
		Integer currentUserId = currentUser.getId();
//		Channel channel = channelService.getChannelById(videoDTO.getChannelId());
		if (channelService.checkOwnership(videoDTO.getChannelId(), currentUserId)) {
			Video updatedMetadata = videoService.uploadVideo(file, videoDTO);
			if (updatedMetadata != null) {
				return ResponseEntity.ok("Video uploaded successfully");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload video");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You no upload channel");
		}
	}
}
