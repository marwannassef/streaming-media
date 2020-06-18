package cs425.mediaStream.video.clientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import cs425.mediaStream.video.config.ForwardToken;
import cs425.mediaStream.video.service.dto.ChannelDTO;

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
	
	public ChannelDTO getChannel(long id) {
//		System.out.println(forwadToken.getToken());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", forwadToken.getToken());
		HttpEntity request = new HttpEntity<>(headers);
		return restTemplate.exchange(lookUpUrlFor(channelService) +"/channel/details/"+id, HttpMethod.GET, request, ChannelDTO.class).getBody();
	}
	
	public long getChannelIdByUser() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", forwadToken.getToken());
		HttpEntity request = new HttpEntity<>(headers);
		return restTemplate.exchange(lookUpUrlFor(channelService) +"/channel/user", HttpMethod.GET, request, Long.class).getBody();
	}
	private String lookUpUrlFor(String appName) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
		return instanceInfo.getHomePageUrl();
	}
	
}
