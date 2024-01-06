package com.StreamTube.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class UserChannelSubDTO {
	private Integer id;
	private int userId;
	private int channelId;
	private Date subscribeDate;
}