package com.StreamTube.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.StreamTube.dtos.VideoDTO;
import com.StreamTube.models.Video;


public interface VideoService {
	List<Video> getAllVideos();
	Video uploadVideo(MultipartFile file, VideoDTO videoDTO);
	Video updateVideo(Integer id, Video video);
	void deleteVideo(Integer id);
	Video getVideoById(int videoId);

}
