package com.StreamTube.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StreamTube.models.Video;

public interface VideoRepository extends JpaRepository<Video, Integer>{

	List<Video> findAllByChannelId(Integer channelId);

}
