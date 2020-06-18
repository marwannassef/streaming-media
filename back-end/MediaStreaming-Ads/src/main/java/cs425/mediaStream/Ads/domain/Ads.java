package cs425.mediaStream.Ads.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
public class Ads {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "Ad must contain a title")
	private String title;
	@Length(max = 250 , message = "description must be at most 250 characters")
	private String description;
	
	private String imageUrl;
	
	public Ads() {

	}
	public Ads(@NotNull(message = "Ad must contain a title") String title,
			@Length(max = 250, message = "description must be less than 250 characters") String description,
			String imageUrl) {
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Ads [id=" + id + ", title=" + title + ", description=" + description + ", imageUrl=" + imageUrl + "]";
	}
	
	

}
