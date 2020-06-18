package cs425.mediaStream.user.config;

import org.springframework.stereotype.Component;

@Component
public class ForwardToken {

	private String token="";
	
	
	public ForwardToken() {
	}
	public ForwardToken(String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
