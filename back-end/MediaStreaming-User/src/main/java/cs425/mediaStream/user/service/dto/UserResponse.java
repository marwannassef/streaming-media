package cs425.mediaStream.user.service.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import cs425.mediaStream.user.domain.Role;

public class UserResponse {
	
	private long id;
	private String fullName;
	@Email
	@NotNull(message = "Email is required")
	private String email;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	private boolean isBanned=false;
	
	private boolean isApproved=false;
	
	private Set<Integer> subscribedChannelsId=new HashSet<Integer>();
	
	private Set<RoleDto> roles = new HashSet<RoleDto>();

	public UserResponse() {
	}

	public UserResponse(long id, String fullName, @Email @NotNull(message = "Email is required") String email,
			LocalDate birthDate, boolean isBanned, boolean isApproved, Set<Integer> subscribedChannelsId,
			Set<RoleDto> roles) {
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.birthDate = birthDate;
		this.isBanned = isBanned;
		this.isApproved = isApproved;
		this.subscribedChannelsId = subscribedChannelsId;
		this.roles = roles;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Set<Integer> getSubscribedChannelsId() {
		return subscribedChannelsId;
	}

	public void setSubscribedChannelsId(Set<Integer> subscribedChannelsId) {
		this.subscribedChannelsId = subscribedChannelsId;
	}

	public Set<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleDto> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", fullName=" + fullName + ", email=" + email + ", birthDate=" + birthDate
				+ ", isBanned=" + isBanned + ", isApproved=" + isApproved + ", subscribedChannelsId="
				+ subscribedChannelsId + ", roles=" + roles + "]";
	}

}
