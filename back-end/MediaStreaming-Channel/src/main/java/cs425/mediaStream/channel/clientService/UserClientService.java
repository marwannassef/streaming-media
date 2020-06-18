package cs425.mediaStream.channel.clientService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import cs425.mediaStream.channel.DTO.JwtTokenResponse;
import cs425.mediaStream.channel.filter.JwtRequest;
import cs425.mediaStream.channel.filter.JwtUserDetails;
import cs425.mediaStream.channel.filter.authority;


@Service
public class UserClientService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Value("${user-Service-Url}")
	private String userService;
	
	public Optional<UserDetails> validate(String token) {
		JwtTokenResponse tokenBody = new JwtTokenResponse(token);
		try {
			Optional<JwtRequest> user= Optional.ofNullable(restTemplate.postForObject(lookUpUrlFor(userService) +"/user/validate", tokenBody,JwtRequest.class));
			return getUserDetails(user.get());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error"+e);
			return Optional.ofNullable(null);
		}
	}
		
	public Optional<UserDetails> getUserDetails(JwtRequest jwtRequest){
		List<SimpleGrantedAuthority> authorities = new ArrayList();
		for ( authority a : jwtRequest.getAuthorities()) {
			 authorities.add(new SimpleGrantedAuthority(a.getAuthority()));
		}
		return Optional.ofNullable(new JwtUserDetails(jwtRequest.getId(), jwtRequest.getUsername(),authorities));
	}
	
	
	private String lookUpUrlFor(String appName) {
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka(appName, false);
		return instanceInfo.getHomePageUrl();
	}

}
