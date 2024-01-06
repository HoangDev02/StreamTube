package com.StreamTube.servicesImps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StreamTube.models.Channel;
import com.StreamTube.repositories.ChannelRepository;
import com.StreamTube.services.ChannelService;

@Service
public class ChannelServiceImp implements ChannelService{
	
	@Autowired
	private ChannelRepository channelRepository;
	
	@Override
	public Channel createChannel(Channel channel) {
		return channelRepository.save(channel);
	}

	@Override
	public List<Channel> getAllChannel() {
		return channelRepository.findAll();
	}

	@Override
	public Channel getChannelById(Integer id) {
		// TODO Auto-generated method stub
		return channelRepository.findById(id).orElse(null);
	}

	@Override
	public Channel updateChannel(Integer id, Channel channel) {
		// TODO Auto-generated method stub
		return channelRepository.findById(id)
				.map(existingChannel -> {
					existingChannel.setName(channel.getName());
					existingChannel.setDescription(channel.getDescription());
					return channelRepository.save(existingChannel);
				})
				.orElse(null);
	}
	@Override
	public boolean checkOwnership(Integer channelId, Integer userId) {
	    Channel channel = getChannelById(channelId);
	    return channel != null && channel.getCreator().getId().equals(userId);
	}
}
