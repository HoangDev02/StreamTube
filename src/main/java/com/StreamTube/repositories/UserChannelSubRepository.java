package com.StreamTube.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.StreamTube.models.UserChannelSub;

public interface UserChannelSubRepository extends JpaRepository<UserChannelSub, Integer>{

	boolean existsByUserIdAndChannelId(Integer userId, Integer channelId);

	void deleteByUserIdAndChannelId(Integer userId, Integer channelId);

	Integer countByAndChannelId(Integer channelId);
	
}
