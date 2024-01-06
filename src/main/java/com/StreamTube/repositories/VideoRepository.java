package com.StreamTube.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StreamTube.models.Video;

public interface VideoRepository extends JpaRepository<Video, Integer>{
	
}
