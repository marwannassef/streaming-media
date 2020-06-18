package cs425.mediaStream.video.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

@Entity
public class Video {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Length(max = 100, message = "Video title should be at most 100 characters")
	@Column(nullable = false)
	private String videoTitle;
	private boolean isbanned=false;
	private boolean isActive=false;
	

	private String videoUrl;
	private String description;
	@NotNull
	private long channelNumber;
	@CreationTimestamp
	private LocalDateTime publishedDate;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "video_Like")
	private Set<Like> likes = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "video_view")
	private Set<View> views = new HashSet<>();

	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "Video_Comment")
	private Set<Comment> comment = new HashSet<Comment>();

	
	public Video() {
	}
	

	public Video(
			@NotNull @Length(max = 100, message = "Video title should be at most 100 characters") String videoTitle,
			boolean isbanned, @NotNull String videoUrl, String description, @NotNull long channelNumber) {
		this.videoTitle = videoTitle;
		this.isbanned = isbanned;
		this.videoUrl = videoUrl;
		this.description = description;
		this.channelNumber = channelNumber;
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public boolean getIsbanned() {
		return isbanned;
	}

	public void setIsbanned(boolean isbanned) {
		this.isbanned = isbanned;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(LocalDateTime publishedDate) {
		this.publishedDate = publishedDate;
	}
	public Set<Comment> getComment() {
		return comment;
	}

	public void addComments(Comment comment) {
		this.comment.add(comment);
	}

	public Set<Like> getLikes() {
		return likes;
	}
	public void addLikes(Like like) {
		likes.add(like);
	}
	public Set<View> getViews() {
		return views;
	}
	public void addViews(View view) {
		views.add(view);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	public long getChannelNumber() {
		return channelNumber;
	}


	public void setChannelNumber(long channelNumber) {
		this.channelNumber = channelNumber;
	}


	public boolean getIsActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
