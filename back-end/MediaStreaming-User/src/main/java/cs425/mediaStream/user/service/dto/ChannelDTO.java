package cs425.mediaStream.user.service.dto;

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
	private Boolean isActive;
	@NotBlank
	private long channelOwner;
	private String channelImg;
	private String coverImg;
	
//	List<VideoDto> videos = new ArrayList<>();
	
	public ChannelDTO() {
	}

	public ChannelDTO(@Length(max = 15, message = "Channel name should be at most 15 characters") String channelName,
			LocalDate creationDate,
			@Length(max = 250, message = "Description should be at most 250 characters") String description,
			Boolean isActive, long cowner) {
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
	
	public long getChannelOwner() {
		return channelOwner;
	}
	
	public void setChannelOwner(long channelOwner) {
		this.channelOwner = channelOwner;
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
	

}
