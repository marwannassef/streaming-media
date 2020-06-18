package cs425.mediaStream.channel.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.internal.NotNull;

@Entity
public class Subscriber {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(nullable = false)
	private long subscriberId;
	
	@CreationTimestamp
	private LocalDateTime creationDate;
	public Subscriber() {
	
	}
	public Subscriber(long subscriberId) {
		this.subscriberId = subscriberId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(long subscriberId) {
		this.subscriberId = subscriberId;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	
}	
