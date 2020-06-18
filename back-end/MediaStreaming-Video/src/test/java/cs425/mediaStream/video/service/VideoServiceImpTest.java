package cs425.mediaStream.video.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import cs425.mediaStream.video.clientService.ChannelClientService;
import cs425.mediaStream.video.repository.VideoRepository;
import cs425.mediaStream.video.service.dto.ChannelDTO;
import cs425.mediaStream.video.service.dto.CommentDto;
import cs425.mediaStream.video.service.dto.VideoDto;


@RunWith(MockitoJUnitRunner.class)
public class VideoServiceImpTest {

	@InjectMocks 
	VideoServiceImpl videoServiceImpl;
	
	@Mock
	VideoRepository videoRepository;
	
	@Mock
	ChannelClientService channelClientService; 
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	/*
	 private VideoDto initVideo(VideoDto video) {
		video.setNumOfLikes(videoRepository.getNumOfLikes(video.getId()));
		video.setNumOfViews(videoRepository.getNumOfViews(video.getId()));
		return video;
	}
	 * */
	@Test
	public void initVideoTest()
	{
		Long ret = (long) 100; 
	    Mockito.when(videoRepository.getNumOfLikes( Mockito.anyLong() )).thenReturn(ret);
	    Mockito.when(videoRepository.getNumOfViews( Mockito.anyLong() )).thenReturn(ret);
	    
	    VideoDto video = new VideoDto();
	    video = videoServiceImpl.initVideo(video);

	    assertEquals(video.getNumOfLikes(), ret);	
	    assertEquals(video.getNumOfViews(), ret);
	}
	
	/*
	private Set<CommentDto> initComments(Set<CommentDto> comments){
		comments.forEach(c -> {
			ChannelDTO channel = channelClientService.getChannel(c.getChannelId());
			c.setChannelImg(channel.getChannelImg());
		});
		return comments;
	}
	  */
	@Test
	public void initCommentsTest()
	{
		Set<CommentDto> commentList = new HashSet<>();
		CommentDto c1 = new CommentDto();
		c1.setId(10);
		c1.setText("descripe");
		
		CommentDto c2 = new CommentDto();
		c2.setId(5);
		c2.setText("descripe");
		
		commentList.add(c1);
		commentList.add(c2);
		
		ChannelDTO channelDro = new ChannelDTO();
		channelDro.setChannelImg("my name");
	    Mockito.when(channelClientService.getChannel( Mockito.anyLong() )).thenReturn(channelDro);

	    Set<CommentDto> commentsDto = videoServiceImpl.initComments(commentList);
	    assertEquals(commentsDto.size() , 2);
	    for(CommentDto cDto : commentsDto)
	    {
		    assertEquals(cDto.getChannelImg() , "my name");	
	    }
	    
	}
	
	
	
	
}
