package cs425.mediaStream.user.service.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 8317676219297719109L;

	private String token;
	private long expirationDate;
	private String channelImg;
	private long channelId;
	private String Name;
	private Collection<? extends GrantedAuthority>  Roles;

	public JwtTokenResponse() {
		super();
	}






	public JwtTokenResponse(String token, long expirationDate, String channelImg, long channelId, String name,
			Collection<? extends GrantedAuthority> roles) {
		this.token = token;
		this.expirationDate = expirationDate;
		this.channelImg = channelImg;
		this.channelId = channelId;
		Name = name;
		Roles = roles;
	}






	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public long getExpirationDate() {
		return expirationDate;
	}



	public void setExpirationDate(long expirationDate) {
		this.expirationDate = expirationDate;
	}


	public Collection<? extends GrantedAuthority> getRoles() {
		return Roles;
	}


	public String getChannelImg() {
		return channelImg;
	}


	public void setChannelImg(String channelImg) {
		this.channelImg = channelImg;
	}


	public long getChannelId() {
		return channelId;
	}


	public void setChannelId(long channelId) {
		this.channelId = channelId;
	}


	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		Roles = roles;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}