package cs425.mediaStream.video.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import cs425.mediaStream.video.domain.Comment;

@JsonInclude(value = Include.NON_EMPTY)
public class VideoDto {

	private long id;

	@NotNull
	@Length(max = 100, message = "Video title should be at most 100 characters")
	private String videoTitle;
	
	private boolean isbanned = false;

	
	private String videoUrl;

	private String description;

	private LocalDateTime publishedDate;

	private long NumOfLikes;
	
	private long NumOfViews;
	
	@JsonInclude(Include.NON_DEFAULT)
	@NotNull
	private long channelNumber;

	private ChannelDTO channel;
	
	private boolean isLiked;

	private List<VideoDto> recommendedVideos = new ArrayList<>();

	private Set<CommentDto> comments = new HashSet<CommentDto>();

	public VideoDto() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public boolean isIsbanned() {
		return isbanned;
	}

	public void setIsbanned(boolean isbanned) {
		this.isbanned = isbanned;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public long getNumOfLikes() {
		return NumOfLikes;
	}

	public void setNumOfLikes(long numOfLikes) {
		NumOfLikes = numOfLikes;
	}

	public long getNumOfViews() {
		return NumOfViews;
	}

	public void setNumOfViews(long numOfViews) {
		NumOfViews = numOfViews;
	}

	public long getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(long channelNumber) {
		this.channelNumber = channelNumber;
	}

	public ChannelDTO getChannel() {
		return channel;
	}

	public void setChannel(ChannelDTO channel) {
		this.channel = channel;
	}


	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComment(Set<CommentDto> comment) {
		this.comments = comment;
	}
	
	public List<VideoDto> getRecommendedVideos() {
		return recommendedVideos;
	}
	public void setRecommendedVideos(List<VideoDto> recommendedVideos) {
		this.recommendedVideos = recommendedVideos;
	}

	@Override
	public String toString() {
		return "VideoDto [id=" + id + ", videoTitle=" + videoTitle + ", isbanned=" + isbanned + ", videoUrl=" + videoUrl
				+ ", description=" + description + ", publishedDate=" + publishedDate + ", NumOfLikes=" + NumOfLikes
				+ ", NumOfViews=" + NumOfViews + ", channelId=" + channelNumber + ", channel=" + channel
				+ ", recommendedVideos=" + recommendedVideos + ", comments=" + comments + "]";
	}
	
	



}
