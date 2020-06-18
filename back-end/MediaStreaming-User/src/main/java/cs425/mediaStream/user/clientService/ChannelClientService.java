package cs425.mediaStream.user.clientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import cs425.mediaStream.user.config.ForwardToken;
import cs425.mediaStream.user.service.dto.ChannelDTO;
import cs425.mediaStream.user.service.dto.UserRequest;
import cs425.mediaStream.user.service.dto.UserResponse;

@Service
public class ChannelClientService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ForwardToken forwadToken;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Value("${channel-Service-Url}")
	private String channelService;
	
	public void createChannel(UserRequest userRequest) {
		System.out.println(forwadToken.getToken());
		ChannelDTO channel = new ChannelDTO();
		channel.setChannelName(userRequest.getFullName());
		channel.setChannelOwner(userRequest.getId());
		channel.setChannelImg(userRequest.getChannelImg());
		System.err.println(userRequest.getId());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", forwadToken.getToken());
		HttpEntity request = new HttpEntity(channel, headers);
		restTemplate.exchange(lookUpUrlFor(channelService) +"/channel/save", HttpMethod.POST, request, Object.class);
	}
	
	public ChannelDTO getChannelIdByUser(String token) {
		HttpHeaders headers = new HttpHeaders();
		System.err.println(token);
		headers.set("Authorization", token);
		HttpEntity request = new HttpEntity<>(headers);
		return restTemplate.exchange(lookUpUrlFor(channelService) +"/channel", HttpMethod.GET, request, ChannelDTO.class).getBody();
	}
	
	private String lookUpUrlFor(String appName) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
		return instanceInfo.getHomePageUrl();
	}
	
}
