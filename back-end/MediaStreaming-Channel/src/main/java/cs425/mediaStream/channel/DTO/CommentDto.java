package cs425.mediaStream.channel.DTO;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;



public class CommentDto {

	private long id;
	
	@NotNull
	@Column(nullable = false)
	private String text;
	
	@NotNull
	private long userId;
	
	@NotNull
	private String username;
	
	public CommentDto() {
	}

	public CommentDto(@NotNull String text, @NotNull long userId, @NotNull String username) {
		this.text = text;
		this.userId = userId;
		this.username = username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
