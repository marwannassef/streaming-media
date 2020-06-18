package cs425.mediaStream.video.service.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import cs425.mediaStream.video.domain.Like;
import cs425.mediaStream.video.domain.View;

public class VideoStatisticsDto {

	private long id;
	
	private String videoTitle;
	
	private long NumOfLikes;
	private long NumOfViews;
	
	private LocalDateTime publishedDate;
	private Set<Like> likes = new HashSet<>();
	private Set<View> views = new HashSet<>();
	public VideoStatisticsDto() {
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
	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}
	public Set<Like> getLikes() {
		return likes;
	}
	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}
	public Set<View> getViews() {
		return views;
	}
	public void setViews(Set<View> views) {
		this.views = views;
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
	
}
