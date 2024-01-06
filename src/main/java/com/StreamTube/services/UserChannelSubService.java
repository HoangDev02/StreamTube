package com.StreamTube.services;

import java.util.List;

import com.StreamTube.models.UserChannelSub;

public interface UserChannelSubService {

	UserChannelSub getUserById(Integer id);

	List<UserChannelSub> getAllUserChannelSubs();

	UserChannelSub createUserChannelSub(UserChannelSub userChannelSub);

	UserChannelSub updateUserChannelSub(Integer id, UserChannelSub userChannelSub);

	void deleteChannelSub(Integer id);

	boolean isSubscribed(Integer userId, Integer channelId);

	void subscribeChannel(Integer userId, Integer channelId);

	void unsubscribeChannel(Integer userId, Integer channelId);

	Integer getTotalSubscriber(Integer channelId);
}
