package cs425.mediaStream.video.service;

import java.util.List;
import java.util.Optional;

import cs425.mediaStream.video.domain.Video;
import cs425.mediaStream.video.service.dto.CommentDto;
import cs425.mediaStream.video.service.dto.StatisticsDto;
import cs425.mediaStream.video.service.dto.VideoDto;
import cs425.mediaStream.video.service.dto.VideoPage;
import cs425.mediaStream.video.service.dto.VideoStatisticsDto;

public interface VideoService {

	public Optional<VideoDto> getVideo(long id);
	
	public List<VideoDto> findByChannelId(long channelId);
	
	public VideoDto createVideo(VideoDto videoDto);
	
	public void deleteVideo(long VideoId);
	
	public VideoDto updateVideo(VideoDto videoDto);
	
	public List<VideoDto> getHomeVideos();
	
	public void likeVideo(long id);
	
	public void activateVideo(long videoId);
	
	public CommentDto commentVideo(long id,CommentDto commentDto);
	
	List<VideoDto> convertEntityListToResponseList(List<Video> entityList);
	
	public void dislikeVideo(long id); 
	
	public void saveExtension(String videoName);
	
	public VideoStatisticsDto getVideoStatistics(long videoId);
	
	public List<StatisticsDto> getVideoStat(long videoId);
	
	public List<VideoDto> searchVideos(String searchWord, int offest , int size);
	
	public List<StatisticsDto> getTopVideos(long limit);
	
	public VideoPage getListVideos(int pageNum,int size);
	
	public Optional<VideoDto> banUnbanVideo(long id, boolean ban);
	
	

}
