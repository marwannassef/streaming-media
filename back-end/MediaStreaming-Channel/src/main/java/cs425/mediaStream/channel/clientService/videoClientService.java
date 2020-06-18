package cs425.mediaStream.channel.clientService;

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

import cs425.mediaStream.channel.DTO.VideoDto;
import cs425.mediaStream.channel.config.ForwardToken;

@Service
public class videoClientService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ForwardToken forwadToken;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Value("${video-Service-Url}")
	private String videoService;
	
	
	public List<VideoDto> getVideos(long channelId){
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Authorization", forwadToken.getToken());
//		HttpEntity request = new HttpEntity<>(headers);
		System.out.println(channelId);
		List<VideoDto> videos= restTemplate.getForObject(lookUpUrlFor(videoService)+"/video/channel/"+channelId, List.class);
		System.out.println(videos);
		return videos;
		
	}
	
	private String lookUpUrlFor(String appName) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
		return instanceInfo.getHomePageUrl();
	}
}
