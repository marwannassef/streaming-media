package cs425.mediaStream.channel.DTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class VideoDto {

	private long id;
	
	@NotNull
	@Length(max = 100, message = "Video title should be at most 100 characters")
	private String videoTitle;
	
	private boolean isbanned=false;
	
	@NotNull
	@Column(nullable = false)
	private String videoUrl;
	
	private String description;
	
	private LocalDate publishedDate;
	
	private long likeNum;
	
	@NotNull
	private long channelId;
	
	private Set<CommentDto> comments = new HashSet<CommentDto>();

	public VideoDto() {
	}

	public VideoDto(
			@NotNull @Length(max = 100, message = "Video title should be at most 100 characters") String videoTitle,
			boolean isbanned, @NotNull String videoUrl, String description, LocalDate publishedDate, long likeNum,
			long channel, Set<CommentDto> comments) {
		this.videoTitle = videoTitle;
		this.isbanned = isbanned;
		this.videoUrl = videoUrl;
		this.description = description;
		this.publishedDate = publishedDate;
		this.likeNum = likeNum;
		this.channelId = channel;
		this.comments = comments;
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

	public LocalDate getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDate publishedDate) {
		this.publishedDate = publishedDate;
	}

	public long getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(long likeNum) {
		this.likeNum = likeNum;
	}

	public Set<CommentDto> getComments() {
		return comments;
	}

	public void setComments(Set<CommentDto> comments) {
		this.comments = comments;
	}

	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}
	
	
	
}
