package cs425.mediaStream.channel.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@Entity
public class Channel {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	@Length(max = 15, message = "Channel name should be at most 15 characters")
	private String channelName;
	@CreationTimestamp
	private LocalDate creationDate;
	@Length(max = 250, message = "Description should be at most 250 characters")
	private String description;
	private Boolean isActive=true;
	
	private String channelImg;
	private String coverImg;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "channelId")
	private Set<Subscriber> subscribedUsers=new HashSet<Subscriber>();
	
	private int channelOwner;
	
	public Channel() {
	}

	public Channel(@Length(max = 15, message = "Channel name should be at most 15 characters") String channelName,
			LocalDate creationDate,
			@Length(max = 250, message = "Description should be at most 250 characters") String description,
			Boolean isActive, int cowner) {
		this.channelName = channelName;
		this.creationDate = creationDate;
		this.description = description;
		this.isActive = isActive;
		this.channelOwner = cowner;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public int getChannelOwner() {
		return channelOwner;
	}
	
	public void setChannelOwner(int channelOwner) {
		this.channelOwner = channelOwner;
	}

	public Set<Subscriber> getSubscribedUsers() {
		return subscribedUsers;
	}

	public void addSubscribers(Subscriber subscriber) {
		subscribedUsers.add(subscriber);
	}

	public String getChannelImg() {
		return channelImg;
	}

	public void setChannelImg(String channelImg) {
		this.channelImg = channelImg;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}
		

}
