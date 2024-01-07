package com.StreamTube.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDTO {
	private Integer id;
	private String name;
	private String description;
	private Date createDate;
	private int creatorId;
	private int subcribers;
	private boolean isOwner;
}
