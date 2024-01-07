package com.StreamTube.services;

import java.util.List;

import com.StreamTube.models.Channel;

public interface ChannelService {
	Channel createChannel(Channel channel);
	List<Channel> getAllChannel();
	Channel getChannelById(Integer id);
	Channel updateChannel(Integer id, Channel channel); 
	List<Channel> findByCreatorId(Integer id);
	public boolean checkOwnership(Integer channelId, Integer userId);
}
