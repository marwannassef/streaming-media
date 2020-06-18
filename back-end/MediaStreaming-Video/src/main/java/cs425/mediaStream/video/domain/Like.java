package cs425.mediaStream.video.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

@Entity(name = "videoLike")
public class Like {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;

	private long userId;
	
	@CreationTimestamp
	private LocalDateTime likeDate;
	

	public Like() {
	}

	public Like(long userId) {
		this.userId = userId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public LocalDateTime getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(LocalDateTime likeDate) {
		this.likeDate = likeDate;
	}

	@Override
	public String toString() {
		return "Like [id=" + id + ", userId=" + userId + ", likeDate=" + likeDate + "]";
	}

	

}
