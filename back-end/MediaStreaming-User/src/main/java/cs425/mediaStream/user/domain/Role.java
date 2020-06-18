package cs425.mediaStream.user.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;



@Entity
public class Role {

	@Id
	private long id;
	@NotNull
	private String roleName;
	
	public Role() {
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public long getId() {
		return id;
	}
}
