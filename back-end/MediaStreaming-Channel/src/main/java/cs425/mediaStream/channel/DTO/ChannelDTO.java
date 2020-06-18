package cs425.mediaStream.channel.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
public class ChannelDTO {

	private long id;
	@Length(max = 15, message = "Channel name should be at most 15 characters")
	private String channelName;
	private LocalDate creationDate;
	@Length(max = 250, message = "Description should be at most 250 characters")
	private String description;
	private Boolean isActive=true;
	@NotBlank
	private int channelOwner;
	
	private long NumOfSubscrbiers;
	
	private String channelImg;
	private String coverImg;
	
	private boolean isSubscribed;
	
	List<ChannelDTO> subscribedChannels= new ArrayList<ChannelDTO>();
	
	List<VideoDto> videos = new ArrayList<>();
	
	public ChannelDTO() {
	}

	public ChannelDTO(@Length(max = 15, message = "Channel name should be at most 15 characters") String channelName,
			LocalDate creationDate,
			@Length(max = 250, message = "Description should be at most 250 characters") String description,
			Boolean isActive, int cowner) {
		this.channelName = channelName;
		this.creationDate = creationDate;
		this.description = description;
		this.isActive = isActive;
		this.channelOwner = cowner;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getChannelOwner() {
		return channelOwner;
	}
	
	public void setChannelOwner(int channelOwner) {
		this.channelOwner = channelOwner;
	}
	
	public void setVideos(List<VideoDto> videos) {
		this.videos = videos;
	}
	public List<VideoDto> getVideos() {
		return videos;
	}

	public long getNumOfSubscrbiers() {
		return NumOfSubscrbiers;
	}

	public void setNumOfSubscrbiers(long numOfSubscrbiers) {
		NumOfSubscrbiers = numOfSubscrbiers;
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

	public List<ChannelDTO> getSubscribedChannels() {
		return subscribedChannels;
	}

	public void setSubscribedChannels(List<ChannelDTO> subscribedChannels) {
		this.subscribedChannels.addAll(subscribedChannels);
	}

	public boolean getIsSubscribed() {
		return isSubscribed;
	}

	public void setIsSubscribed(boolean isSubscribed) {
		this.isSubscribed = isSubscribed;
	}

	@Override
	public String toString() {
		return "ChannelDTO [id=" + id + ", channelName=" + channelName + ", creationDate=" + creationDate
				+ ", description=" + description + ", isActive=" + isActive + ", channelOwner=" + channelOwner
				+ ", NumOfSubscrbiers=" + NumOfSubscrbiers + ", channelImg=" + channelImg + ", coverImg=" + coverImg
				+ ", isSubscribed=" + isSubscribed + ", subscribedChannels=" + subscribedChannels + ", videos=" + videos
				+ "]";
	}
	

}
