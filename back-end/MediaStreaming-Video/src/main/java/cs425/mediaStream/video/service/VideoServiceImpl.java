package cs425.mediaStream.video.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.http.client.HttpResponseException;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.bouncycastle.pqc.jcajce.provider.qtesla.SignatureSpi.qTESLA;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import antlr.collections.impl.LList;
import cs425.mediaStream.video.clientService.ChannelClientService;
import cs425.mediaStream.video.domain.Comment;
import cs425.mediaStream.video.domain.Like;
import cs425.mediaStream.video.domain.Video;
import cs425.mediaStream.video.domain.View;
import cs425.mediaStream.video.filter.JwtUserDetails;
import cs425.mediaStream.video.repository.LikeRepository;
import cs425.mediaStream.video.repository.VideoRepository;
import cs425.mediaStream.video.service.dto.ChannelDTO;
import cs425.mediaStream.video.service.dto.CommentDto;
import cs425.mediaStream.video.service.dto.StatisticsDto;
import cs425.mediaStream.video.service.dto.VideoDto;
import cs425.mediaStream.video.service.dto.VideoPage;
import cs425.mediaStream.video.service.dto.VideoStatisticsDto;

@Service
@Transactional
public class VideoServiceImpl implements VideoService{

	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private LikeRepository LikeRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private ChannelClientService channelClientService;
	
	HashMap<Long, ChannelDTO> vistedChannel = new HashMap<>();
	
	@Override
	public Optional<VideoDto> getVideo(long id) {
		// TODO Auto-generated method stub
		
		Optional<Video> video = videoRepository.findById(id);
		video.get().addViews(new View());
		if(!video.isPresent())
			return Optional.ofNullable(null);
		VideoDto videoDto =mapper.map(video.get(), VideoDto.class);
		try {
		JwtUserDetails userDetails = getPrincipal();
		if(videoRepository.getLike(id, userDetails.getId())!=null)
			videoDto.setLiked(true);
		}catch(Exception e) {System.out.println("Like Error" + e.getMessage());}
		videoDto.setChannel(channelClientService.getChannel(videoDto.getChannelNumber()));
		videoDto.setRecommendedVideos(getRecommendedVideos(id));
		videoDto.setComment(initComments(videoDto.getComments()));
		return Optional.ofNullable(initVideo(videoDto));
	}
	
	public Set<CommentDto> initComments(Set<CommentDto> comments){
		comments.forEach(c -> {
			ChannelDTO channel = channelClientService.getChannel(c.getChannelId());
			c.setChannelImg(channel.getChannelImg());
		});
		return comments;
	}
	
	public VideoDto initVideo(VideoDto video) {
		video.setNumOfLikes(videoRepository.getNumOfLikes(video.getId()));
		video.setNumOfViews(videoRepository.getNumOfViews(video.getId()));
		return video;
	}

	@Override
	public List<VideoDto> findByChannelId(long channelId) {
		// TODO Auto-generated method stub
		List<VideoDto> videosDto = convertEntityListToResponseList(videoRepository.getVideosByChannelNumberAndIsActiveTrue(channelId));
		videosDto.forEach(v->{
			v.setComment(null);
			v=initVideo(v);
			});
		return videosDto;

	}

	public void likeVideo(long id) {
		JwtUserDetails userDetails =getPrincipal();
		Optional<Video> video = videoRepository.findById(id);
		Like l =videoRepository.getLike(id, userDetails.getId());
		if(videoRepository.getLike(id, userDetails.getId())!=null)
			throw new ResponseStatusException(HttpStatus.CONFLICT, "you already liked");
		video.get().addLikes(new Like(userDetails.getId()));
	}
	
	public void dislikeVideo(long id) {
		JwtUserDetails userDetails =getPrincipal();
		Optional<Video> video = videoRepository.findById(id);
		System.out.println("okay"+id+" "+userDetails.getId());
		Like like =videoRepository.getLike(id, userDetails.getId());
		LikeRepository.deleteById(like.getId());

	}
	
	
	@Override
	public CommentDto commentVideo(long id,CommentDto commentDto) {
		// TODO Auto-generated method stub
		JwtUserDetails userDetails =getPrincipal();
		Optional<Video> video = videoRepository.findById(id);
		long userChannel = channelClientService.getChannelIdByUser();
		ChannelDTO channel = channelClientService.getChannel(userChannel);		
		Comment newComment= new Comment(commentDto.getText(), channel.getId(), userDetails.getUsername());
		video.get().addComments(newComment);
		videoRepository.flush();
		return mapper.map(newComment, CommentDto.class);
	}
	
	@Override
	public VideoDto createVideo(VideoDto videoDto) {
		// TODO Auto-generated method stub
		Video newVideo = mapper.map(videoDto, Video.class);
		newVideo.setChannelNumber(channelClientService.getChannelIdByUser());
		return mapper.map(videoRepository.save(newVideo), VideoDto.class);
	}
	@Override
	public void saveExtension(String videoName) {
		// TODO Auto-generated method stub
		String []videoId = videoName.split("\\.");
		Long myId = new Long(videoId[0]);
		Optional<Video> video = videoRepository.findById(myId);
		video.get().setVideoUrl(videoName);
	}
	@Override
	public void activateVideo(long videoId) {
		// TODO Auto-generated method stub
		Optional<Video> video = videoRepository.findById(videoId);
		if(!video.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Video does not exist");
		video.get().setActive(true);
	}
	

	@Override
	public void deleteVideo(long VideoId) {
		// TODO Auto-generated method stub
		videoRepository.deleteById(VideoId);
	}

	@Override
	public VideoDto updateVideo(VideoDto videoDto) {
		// TODO Auto-generated method stub
		Video updateVideo = mapper.map(videoDto, Video.class);
		return mapper.map(videoRepository.save(updateVideo), VideoDto.class);
	}

	@Override
	public List<VideoDto> convertEntityListToResponseList(List<Video> entityList) {
		// TODO Auto-generated method stub
		if(entityList == null)
			return null;
		return entityList.stream()
				.map(entity -> mapper.map(entity, VideoDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<VideoDto> getHomeVideos() {
		// TODO Auto-generated method stub
		List<VideoDto> videosDto = convertEntityListToResponseList(videoRepository.getVideosByIsActiveTrueAndIsbannedFalse());
		return attachChannels(videosDto,true);
	}
	
	private List<VideoDto> attachChannels(List<VideoDto> videosDto,boolean commentOn){
		for(VideoDto video : videosDto) {
			long channelId = video.getChannelNumber();
			System.out.println(channelId);
			if(commentOn)
				video.setComment(null);
			video.getChannelNumber();
			if(!vistedChannel.containsKey(channelId)) {
				video.setChannel(channelClientService.getChannel(channelId));
				System.out.println(video.getChannel())	;
				vistedChannel.put(channelId, video.getChannel());
				continue;
			}
			video.setChannel(vistedChannel.get(channelId));
			video = initVideo(video);
		}
		return videosDto;
	}
	
	private List<VideoDto> getRecommendedVideos(long videoId){
		List<Video> videos = videoRepository.getRecommendedVideos(videoId);
		List<VideoDto> videoDtos= convertEntityListToResponseList(videos);
		videoDtos.forEach(v->
		{
			v.setComment(null);
			v = initVideo(v);
		});
		return attachChannels(videoDtos, false);
	}

	private JwtUserDetails getPrincipal() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (JwtUserDetails) principal;
		
	}

	@Override
	public VideoStatisticsDto getVideoStatistics(long videoId) {
		// TODO Auto-generated method stub
		JwtUserDetails userDetails =getPrincipal();
		long channelId = channelClientService.getChannelIdByUser();
		Video video = videoRepository.findById(videoId).get();
		if(channelId != video.getChannelNumber())
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED ,"You cannot access this video");
		VideoStatisticsDto videoStatisitcsDto = mapper.map(video, VideoStatisticsDto.class);
		videoStatisitcsDto.setNumOfLikes(videoRepository.getNumOfLikes(videoId));
		videoStatisitcsDto.setNumOfViews(videoRepository.getNumOfViews(videoId));
		return videoStatisitcsDto;
	}
	
	@Override
	public List<StatisticsDto> getVideoStat(long videoId) {
		// TODO Auto-generated method stub
		JwtUserDetails userDetails =getPrincipal();
		long channelId = channelClientService.getChannelIdByUser();
		Video video = videoRepository.findById(videoId).get();
		if(channelId != video.getChannelNumber())
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED ,"You cannot access this video");
		List<StatisticsDto> views= new ArrayList<StatisticsDto>();
		views.addAll(videoRepository.getViewStatistics(videoId)); 
		return views;
//		.stream().limit(limit).collect(Collectors.toList());
	}

	@Override
	public List<VideoDto> searchVideos(String searchWord, int pageNum, int size) {
		// TODO Auto-generated method stub
			Pageable page = PageRequest.of(pageNum, size);
		return convertEntityListToResponseList(videoRepository.searchVideo(searchWord, page));
	}

	@Override
	public List<StatisticsDto> getTopVideos(long limit) {
		// TODO Auto-generated method stub
		JwtUserDetails userDetails =getPrincipal();
		long channelId = channelClientService.getChannelIdByUser();
		List<StatisticsDto> topvideos = videoRepository.getTopVideos(channelId);
		return topvideos.stream().limit(limit).collect(Collectors.toList());
	}

	@Override
	public VideoPage getListVideos(int pageNum,int size){
		Pageable page = PageRequest.of(pageNum, size);
		List<VideoDto> videoDtos = convertEntityListToResponseList(videoRepository.findAll(page).stream().collect(Collectors.toList()));
		return new VideoPage(videoDtos, videoRepository.getNumberOfVideos());
	}
	
	@Override
	public Optional<VideoDto> banUnbanVideo(long id, boolean ban){
		Optional<Video> video = videoRepository.findById(id);
		if(!video.isPresent())
			return Optional.ofNullable(null);
		video.get().setIsbanned(ban);
		return Optional.ofNullable(mapper.map(video.get(), VideoDto.class));
			
	}
	
	


}
