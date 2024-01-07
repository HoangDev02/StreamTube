package com.StreamTube.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StreamTube.models.Channel;

public interface ChannelRepository extends JpaRepository<Channel, Integer>{
	List<Channel> findByCreatorId(Integer creatorId);
}
