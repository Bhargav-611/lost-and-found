package com.project.lostfound.entity;

import jakarta.persistence.*;

import java.util.Arrays;
//import lombok.*;
import java.util.Date;

@Entity
@Table(name = "found_items")
public class FoundItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String itemName;
    
    @Temporal(TemporalType.DATE)
    private Date dateFound;
    
    private String location;
    private String description;
    
    private String imageName;
    private String imageType;
    
    @Lob
    private byte[] imageData;
    
    
	public FoundItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FoundItem(Long id, User user, String itemName, Date dateFound, String location, String description) {
		super();
		this.id = id;
		this.user = user;
		this.itemName = itemName;
		this.dateFound = dateFound;
		this.location = location;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Date getDateFound() {
		return dateFound;
	}
	public void setDateFound(Date dateFound) {
		this.dateFound = dateFound;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	@Override
	public String toString() {
		return "FoundItem [id=" + id + ", user=" + user + ", itemName=" + itemName + ", dateFound=" + dateFound
				+ ", location=" + location + ", description=" + description + ", imageName=" + imageName
				+ ", imageType=" + imageType + ", imageData=" + Arrays.toString(imageData) + "]";
	}
    
    
}