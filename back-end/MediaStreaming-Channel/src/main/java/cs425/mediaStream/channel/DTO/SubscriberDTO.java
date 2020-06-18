package cs425.mediaStream.channel.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;



public class SubscriberDTO {


	private long id;
	
	@NotNull
	private long subscriberId;
	
	private LocalDateTime creationDate;

	public SubscriberDTO() {
		super();
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
