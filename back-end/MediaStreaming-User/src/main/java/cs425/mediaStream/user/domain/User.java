package cs425.mediaStream.user.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	private String fullName;
	
	@Email
	@NotNull(message = "Email is required")
	@Column(nullable = false)
	private String email;
	@NotNull(message = "Password is required")
	@Column(nullable = false)
	private String password;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	private boolean isBanned=false;
	private boolean isApproved=false;
	
	@ElementCollection
	@CollectionTable(name = "Subscriptions")
	private Set<Integer> subscribedChannelsId=new HashSet<Integer>();
	
	@ManyToMany
	private Set<Role> roles = new HashSet<Role>();

	public User() {
	}

	public User(String fullName, @Email @NotNull(message = "Email is required") String email,
			@NotNull(message = "Password is required") String password, LocalDate birthDate, boolean isBanned,
			boolean isApproved, Set<Integer> subscribedChannelsId, Set<Role> roles) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.isBanned = isBanned;
		this.isApproved = isApproved;
		this.subscribedChannelsId = subscribedChannelsId;
		this.roles.addAll(roles);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public boolean getIsBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public boolean getIsApproved() {
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}
	
	
	
	

	
	
	

}
