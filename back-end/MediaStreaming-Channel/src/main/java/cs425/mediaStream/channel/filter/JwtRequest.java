package cs425.mediaStream.channel.filter;


import java.util.List;



public class JwtRequest {
	
	private  Long id;
	private String username;
	private String password;
	private List<authority> authorities;
	
	
	
	public JwtRequest() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public List<authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "JwtRequest [id=" + id + ", username=" + username + ", password=" + password + ", authorities="
				+ authorities + "]";
	}
	
	
	
	
}
