package cs425.mediaStream.Ads.service.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AdDto {
	
	private long id;
	@NotNull(message = "Ad must contain a title")
	private String title;
	@Length(max = 250 , message = "description must be at most 250 characters")
	private String description;
	@NotNull(message = "you should provide a Image")
	private String imageUrl;
	
	
	public AdDto() {
	}
	public AdDto(long id, @NotNull(message = "Ad must contain a title") String title,
			@Length(max = 250, message = "description must be at most 250 characters") String description,
			@NotNull(message = "you should provide a Image") String imageUrl) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "AdDto [id=" + id + ", title=" + title + ", description=" + description + ", imageUrl=" + imageUrl + "]";
	}
	
	
}
