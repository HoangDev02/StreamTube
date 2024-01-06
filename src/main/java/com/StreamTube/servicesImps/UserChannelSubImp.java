package com.StreamTube.servicesImps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.StreamTube.models.UserChannelSub;
import com.StreamTube.repositories.UserChannelSubRepository;
import com.StreamTube.services.ChannelService;
import com.StreamTube.services.UserChannelSubService;
import com.StreamTube.services.UserService;

@Service
public class UserChannelSubImp implements UserChannelSubService{
	
	@Autowired
	private UserChannelSubRepository userChannelSubRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChannelService channelService;

	@Override
	public UserChannelSub getUserById(Integer id) {
		return userChannelSubRepository.findById(id).orElse(null);
	}

	@Override
	public List<UserChannelSub> getAllUserChannelSubs() {
		// TODO Auto-generated method stub
		return userChannelSubRepository.findAll();
	}

	@Override
	public UserChannelSub createUserChannelSub(UserChannelSub userChannelSub) {
		// TODO Auto-generated method stub
		return userChannelSubRepository.save(userChannelSub);
	}

	@Override
	public UserChannelSub updateUserChannelSub(Integer id, UserChannelSub userChannelSub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteChannelSub(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSubscribed(Integer userId, Integer channelId) {
		return userChannelSubRepository.existsByUserIdAndChannelId(userId, channelId);
	}

	@Override
	public void subscribeChannel(Integer userId, Integer channelId) {
		UserChannelSub userChannelSub = new UserChannelSub();
		userChannelSub.setUser(userService.getUserById(userId));
		userChannelSub.setChannel(channelService.getChannelById(channelId));
		userChannelSubRepository.save(userChannelSub);
		
	}

	@Override
	public void unsubscribeChannel(Integer userId, Integer channelId) {
		userChannelSubRepository.deleteByUserIdAndChannelId(userId, channelId);
		
	}
	@Override
	public Integer getTotalSubscriber(Integer channelId) {
		return userChannelSubRepository.countByAndChannelId(channelId);
	}
	
	
}
