package cs425.mediaStream.video.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import cs425.mediaStream.video.clientService.ChannelClientService;
import cs425.mediaStream.video.clientService.UploadVideoService;
import cs425.mediaStream.video.domain.Like;
import cs425.mediaStream.video.service.VideoService;
import cs425.mediaStream.video.service.dto.CommentDto;
import cs425.mediaStream.video.service.dto.ForwardVideoNameDTO;
import cs425.mediaStream.video.service.dto.StatisticsDto;
import cs425.mediaStream.video.service.dto.VideoDto;
import cs425.mediaStream.video.service.dto.VideoPage;
import cs425.mediaStream.video.service.dto.VideoStatisticsDto;
import io.swagger.annotations.Api;
@Api(description  =  "video contoller handles all end points related to video service")
@RestController
@CrossOrigin
@RequestMapping(value = "/video", headers = "Accept=application/json")
public class VideoController {

	@Autowired
	private VideoService videoService;
	@Autowired 
	private UploadVideoService upload;
	@Autowired
	private ChannelClientService channelClientService;
	
	@GetMapping("/get/{id}")
	public VideoDto getVideo(@PathVariable long id) {
		return videoService.getVideo(id).
				orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found"));
	}
	
	@GetMapping("/statistics/{videoId}")
	public List<StatisticsDto> getVideoStatistics(@PathVariable long videoId) {
		return videoService.getVideoStat(videoId);
	}
	@GetMapping("statistics/topvideos")
	public List<StatisticsDto> getTopVideo(@RequestParam int limit){
		return videoService.getTopVideos(limit);
	}
	@GetMapping("channel/{channelId}")
	public List<VideoDto> getVideoByChannelId(@PathVariable long channelId) {
		return videoService.findByChannelId(channelId);
	}
	
	@PostMapping("upload")
	public VideoDto createVideo(@Valid @RequestBody VideoDto videoDto) {
		//System.out.println(channelClientService.getChannelIdByUser());
		return videoService.createVideo(videoDto);
	}
	
	@PutMapping("activate/{videoId}")
	public void activateVideo(@PathVariable long videoId) {
		videoService.activateVideo(videoId);
		VideoDto video = videoService.getVideo(videoId).get();
		upload.detectVideoObjects(video.getVideoUrl());
	}
	
	@PostMapping("convert/{videoName}")
	public void sendToUploadService(@PathVariable String videoName) {
		videoService.saveExtension(videoName);
		System.out.println(videoName);
		upload.uploadVideo(videoName);
	}
	
	
	@DeleteMapping("{id}")
	public void deleteVideo(@PathVariable long id) {
		videoService.deleteVideo(id);
	}
	
	@PutMapping
	public VideoDto updateVideo(@Valid @RequestBody VideoDto videoDto) {
		return videoService.updateVideo(videoDto);
	}
	
	@GetMapping("/home")
	public List<VideoDto> getHomeVideos(){
		return videoService.getHomeVideos();
	}
	
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/like/{videoId}")
	public void likeVideo(@PathVariable long videoId) {
		videoService.likeVideo(videoId);
	}
	
	@DeleteMapping("/dislike/{id}")
	public void dislikeVideo(@PathVariable long id) {
		videoService.dislikeVideo(id);
	}
	
	@PostMapping("comment/{videoid}")
	public CommentDto commentVideo(@PathVariable long videoid,@Valid @RequestBody CommentDto comment) {
		return videoService.commentVideo(videoid, comment);
	}
	
	@GetMapping("search")
	public List<VideoDto> searchVideo(@RequestParam String searchWord,@RequestParam int page , @RequestParam int size){
		return videoService.searchVideos("", page, size);
	}
	
	@GetMapping("list")
	public VideoPage listVideos(@RequestParam int pageNum,@RequestParam int size){
		return videoService.getListVideos(pageNum, size);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("ban/{id}")
	public VideoDto banVideo(@PathVariable long id,@RequestParam boolean ban) {
		return videoService.banUnbanVideo(id, ban).get();
	}
	
	
}
