package cs425.mediaStream.video.service.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



public class CommentDto {

	@JsonInclude(Include.NON_DEFAULT)
	private long id;
	
	@NotNull
	@Column(nullable = false)
	private String text;
	@CreationTimestamp
	private LocalDateTime publishedDate;
//	@NotNull
	private long channelId;
	
	private String channelImg;
	
//	@NotNull
	private String username;
	
	public CommentDto() {
	}

	public CommentDto(@NotNull String text, @NotNull long channelId, @NotNull String username) {
		this.text = text;
		this.channelId = channelId;
		this.username = username;
	}

	
	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public long getChannelId() {
		return channelId;
	}

	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getChannelImg() {
		return channelImg;
	}

	public void setChannelImg(String channelImg) {
		this.channelImg = channelImg;
	}
	
	
}
