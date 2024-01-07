package com.StreamTube.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.StreamTube.configs.SecurityUtils;
import com.StreamTube.dtos.VideoDTO;
import com.StreamTube.dtos.VideoSummaryDTO;
import com.StreamTube.mappers.VideoMapper;
import com.StreamTube.models.User;
import com.StreamTube.models.Video;
import com.StreamTube.services.ChannelService;
import com.StreamTube.services.VideoService;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/api/videos")
public class VideoController {

	@Autowired
	private VideoMapper videoMapper;

	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ChannelService channelService;

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

	@GetMapping("/{id}")
	public VideoDTO getVideoById(@PathVariable("id") Integer id) {
		return videoMapper.toDTO(videoService.getVideoById(id));
	}
	
	@GetMapping
	public List<VideoSummaryDTO> getAllVideos() {
		return videoMapper.toSummaryDTOList(videoService.getAllVideos());
	}
	@GetMapping("/channels/{channelId}")
	public List<VideoSummaryDTO> getVideosByChannels(@PathVariable("channelId") Integer channelId) {
		Stream<Video> videosStream = videoService.getVideosByChannelAsStream(channelId);
		List<VideoSummaryDTO> videoSummaryDTOs = videosStream
				.map(video -> videoMapper.toSummaryDTO(video)).collect(Collectors.toList());
		return videoSummaryDTOs;
	}
	@PutMapping("/{id}")
	public ResponseEntity<VideoDTO> updateVideo(@PathVariable("id") Integer id, @RequestBody VideoDTO videoDTO) {
		User currentUser = SecurityUtils.getCurrentUser();
//		User currentUser = currentUserDetails.getUser();
		Integer currentUserId = currentUser.getId();

		if (videoService.isOwner(id, currentUserId)) {

			Video updatedVideo = videoService.updateVideo(id, videoMapper.toModel(videoDTO));
			VideoDTO updatedVideoDTO = videoMapper.toDTO(updatedVideo);
			return ResponseEntity.ok(updatedVideoDTO);
		} else {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteVideo(@PathVariable("id") Integer id) {
		User currentUser = SecurityUtils.getCurrentUser();
		Integer currentUserId = currentUser.getId();

		if (videoService.isOwner(id, currentUserId)) {

			videoService.deleteVideo(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	
}
