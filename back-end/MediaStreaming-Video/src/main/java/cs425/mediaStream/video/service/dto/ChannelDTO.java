package cs425.mediaStream.video.service.dto;


import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChannelDTO {

	private long id;
	@Length(max = 15, message = "Channel name should be at most 15 characters")
	private String channelName;
	
	private String channelImg;
	
	private String coverImg;
	private boolean isSubscribed;
	//private UserDto channelOwner;	
	public ChannelDTO() {
	}

	public ChannelDTO(long id,
			@Length(max = 15, message = "Channel name should be at most 15 characters") String channelName,
			UserDto channelOwner) {
		this.id = id;
		this.channelName = channelName;
		//this.channelOwner = channelOwner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelImg() {
		return channelImg;
	}

	public void setChannelImg(String channelImg) {
		this.channelImg = channelImg;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public boolean getIsSubscribed() {
		return isSubscribed;
	}

	public void setIsSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	@Override
	public String toString() {
		return "ChannelDTO [id=" + id + ", channelName=" + channelName + ", channelImg=" + channelImg + ", coverImg="
				+ coverImg + ", isSubscribed=" + isSubscribed + "]";
	}
	
	
//	public UserDto getChannelOwner() {
//		return channelOwner;
//	}
//
//
//	public void setChannelOwner(UserDto channelOwner) {
//		this.channelOwner = channelOwner;
//	}

}
