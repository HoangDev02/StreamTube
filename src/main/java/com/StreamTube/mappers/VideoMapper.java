package com.StreamTube.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.StreamTube.dtos.ChannelBasicInfoDTO;
import com.StreamTube.dtos.VideoDTO;
import com.StreamTube.dtos.VideoSummaryDTO;
import com.StreamTube.models.Channel;
import com.StreamTube.models.Video;
import com.StreamTube.services.ChannelService;

@Component
public class VideoMapper {
	@Autowired
	private ChannelService channelService;

	public VideoDTO toDTO(Video model) {
		VideoDTO dto = new VideoDTO();
		dto.setId(model.getId());
		dto.setTitle(model.getTitle());
		dto.setDescription(model.getDescription());
		dto.setFileName(model.getFilename());
		dto.setUploadDate(model.getUploadDate());
		dto.setDuration(model.getDuration());
		dto.setVideoUrl(model.getVideoUrl());
		dto.setChannelId(model.getChannel().getId());
		return dto;
	}

	public List<VideoDTO> toDTOList(List<Video> models) {
		return models.stream().map(cat -> toDTO(cat)).collect(Collectors.toList());
	}

	public Video toModel(VideoDTO dto) {
		Video model = new Video();
		model.setId(dto.getId());
		model.setTitle(dto.getTitle());
		model.setDescription(dto.getDescription());
		model.setFilename(dto.getFileName());
		model.setUploadDate(dto.getUploadDate());
		model.setDuration(dto.getDuration());
		model.setVideoUrl(dto.getVideoUrl());
		model.setChannel(channelService.getChannelById(dto.getChannelId()));
		return model;
	}

	public VideoSummaryDTO toSummaryDTO(Video model) {

		VideoSummaryDTO dto = new VideoSummaryDTO();

		dto.setId(model.getId());

		dto.setTitle(model.getTitle());

		dto.setDescription(model.getDescription());

		dto.setDuration(model.getDuration());

		Channel channel = model.getChannel();
		ChannelBasicInfoDTO channelBasicInfoDTO = new ChannelBasicInfoDTO();
		channelBasicInfoDTO.setId(channel.getId());
		channelBasicInfoDTO.setName(channel.getName());
		dto.setChannel(channelBasicInfoDTO);

		return dto;
	}

	public List<VideoSummaryDTO> toSummaryDTOList(List<Video> models) {

		return models.stream().map(cat -> toSummaryDTO(cat)).collect(Collectors.toList());

	}
}
