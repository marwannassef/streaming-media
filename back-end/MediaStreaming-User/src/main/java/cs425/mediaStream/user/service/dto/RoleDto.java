package cs425.mediaStream.user.service.dto;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class RoleDto {
	
	
	private String roleName;

	
	public RoleDto() {
	}

	public RoleDto(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

}
