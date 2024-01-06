package com.StreamTube.servicesImps;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.StreamTube.dtos.VideoDTO;
import com.StreamTube.mappers.VideoMapper;
import com.StreamTube.models.Channel;
import com.StreamTube.models.Video;
import com.StreamTube.repositories.VideoRepository;
import com.StreamTube.services.ChannelService;
import com.StreamTube.services.VideoService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;

@Service
public class VideoServiceImp implements VideoService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private Bucket storageClient;
	@Autowired
	private ChannelService channelService;
	@Override
	public List<Video> getAllVideos() {
		return videoRepository.findAll();
	}

	@Override
	public Video updateVideo(Integer id, Video video) {
		return videoRepository.findById(id)
			.map(e -> {
			e.setTitle(video.getTitle());
			e.setDescription(video.getDescription());
			e.setTags(video.getTags());
			return videoRepository.save(e);
		}).orElse(null);
	}

	@Override
	public void deleteVideo(Integer id) {
		videoRepository.deleteById(id);
	}
	public Video createVideo(Video video) {
		return videoRepository.save(video);
	}

	@Override
	public Video uploadVideo(MultipartFile file,VideoDTO metadata) {
		String videoName = UUID.randomUUID().toString();
		Blob blob;
		try {
			blob = storageClient.create(videoName, file.getBytes(), file.getContentType());
			String videoUrl = blob.signUrl(365, TimeUnit.DAYS).toString();
			long duration;
			try {
				duration = getVideoDuration(videoUrl);
				metadata.setVideoUrl(videoUrl);
				metadata.setDuration(duration);
				Video video = videoMapper.toModel(metadata);
				return videoRepository.save(video);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static long getVideoDuration(String videoUrl) throws Exception {
		FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoUrl);
		grabber.start();
		long duration = grabber.getLengthInTime();
		grabber.stop();
		return duration / 1000;
	}

	@Override
	public Video getVideoById(int videoId) {
		// TODO Auto-generated method stub
		return videoRepository.findById(videoId).orElse(null);
	}


	
	

}
