package cs425.mediaStream.video.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cs425.mediaStream.video.clientService.ChannelClientService;
import cs425.mediaStream.video.clientService.UploadVideoService;
import cs425.mediaStream.video.clientService.UserClientService;
import cs425.mediaStream.video.config.ForwardToken;
import cs425.mediaStream.video.service.VideoService;
import cs425.mediaStream.video.service.dto.VideoDto;


@WebMvcTest(value = VideoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VideoControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
    VideoController controller;
		
	@MockBean
	VideoService videoService;
	
	@MockBean
	UserClientService clinet;
	
	@MockBean
	UploadVideoService uploadservice;
	
	@MockBean
	private ChannelClientService channelClientService;
	
	@MockBean
	private ForwardToken token;
	
	
	
	/*
	 @GetMapping("/get/{id}")
	public VideoDto getVideo(@PathVariable long id) {
		return videoService.getVideo(id).
				orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found"));
	}
	 */
	
	@Test
	@WithMockUser(username = "john", roles={"ADMIN"})
	void getVideoTest() throws Exception
	{
		Optional<VideoDto> videoDto = Optional.of(new VideoDto());
		videoDto.get().setDescription("Controller Test");
		
		Mockito.when(videoService.getVideo( Mockito.anyLong() )).thenReturn(videoDto);
		mockMvc.perform(get("/video/get/{id}",1L))
			    .andExpect(status().isOk());
	}
	
	
/*
 		@GetMapping("channel/{channelId}")
	public List<VideoDto> getVideoByChannelId(@PathVariable long channelId) {
		return videoService.findByChannelId(channelId);
	}
 * */	
	@Test
	@WithMockUser(username = "kalu", roles={"ADMIN"})
	void getVideoByChannelIdTest() throws Exception
	{
		List<VideoDto> videoDtos = new ArrayList<>();
		VideoDto videoDto1 = new VideoDto();
		videoDto1.setVideoTitle("video 1");
		
		VideoDto videoDto2 = new VideoDto();
		videoDto2.setVideoTitle("video 2");

		videoDtos.add(videoDto1);
		videoDtos.add(videoDto2);
		
		String inputInJson = this.mapToJson(videoDtos);
		
		Mockito.when(videoService.findByChannelId( Mockito.anyLong() )).thenReturn(videoDtos);
		MvcResult result =  mockMvc.perform(get("/video/channel/{channelId}",1L)).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	
}

