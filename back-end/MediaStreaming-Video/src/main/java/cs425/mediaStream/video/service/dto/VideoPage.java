package cs425.mediaStream.video.service.dto;

import java.util.List;



public class VideoPage {
	
	private List<VideoDto> videos;
	private int size;
	public VideoPage() {
	}
	public VideoPage(List<VideoDto> videos, int size) {
		this.videos = videos;
		this.size = size;
	}
	public List<VideoDto> getVideos() {
		return videos;
	}
	public void setVideos(List<VideoDto> videos) {
		this.videos = videos;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	

}
