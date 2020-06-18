package cs425.mediaStream.channel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import cs425.mediaStream.channel.DTO.ChannelDTO;
import cs425.mediaStream.channel.DTO.StatisticsDto;
import cs425.mediaStream.channel.DTO.VideoDto;
import cs425.mediaStream.channel.model.Subscriber;
import cs425.mediaStream.channel.service.ChannelServiceImpl;
import io.swagger.annotations.Api;

@Api( description  = "Channel contoller handles all end points related to channel service")
@RestController
@CrossOrigin
@RequestMapping(value = "/channel", produces = "application/json")
public class ChannelController {
	@Autowired
	private ChannelServiceImpl channelService;
	
	
	@GetMapping("/")
	public List<ChannelDTO> findAll() {
		return channelService.findAll();
	}
	
	@GetMapping("/{id}")
	public ChannelDTO findById(@PathVariable long id) {
		ChannelDTO channelDTO = channelService.findById(id);
		return channelDTO;
	}
	
	@GetMapping
	public ChannelDTO findByUserId() {
		ChannelDTO channelDTO = channelService.findByUserId();
		return channelDTO;
	}
	
	@GetMapping("user")
	public long getChannelByUserId() {
//		Long id= new Long( channelService.findChannelByUserId());
		return channelService.findChannelByUserId();
	}
	
	
	@GetMapping("subscribe/{channelId}")
	public void subscribeToChannel(@PathVariable long channelId) {
		channelService.subscribeToChannel(channelId);
	}
	
	@GetMapping("unsubscribe/{channelId}")
	public void unSubscribeToChannel(@PathVariable long channelId) {
		channelService.unSubscribe(channelId);
	}
	
	@GetMapping("/details/{id}")
	public ChannelDTO findChannelDetailsById(@PathVariable long id) {
		return channelService.findById(id);
	}
	@DeleteMapping("delete/{id}")
	public ChannelDTO deleteById(@PathVariable long id) {
		return channelService.deleteById(id);	
	}
	
	@PostMapping("/save")
	public ChannelDTO save(@RequestBody ChannelDTO channelDTO) {
		System.out.println(channelDTO);
		return channelService.save(channelDTO);
	}
	
	@GetMapping("/statistics")
	public List<StatisticsDto> getVideoStatistics() {
		return channelService.getChannelStat();
	}

}
