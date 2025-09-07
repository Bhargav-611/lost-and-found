package com.project.lostfound.entity;

import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.*;

//import lombok.*;

@Entity
@Table(name="lost_items")
public class LostItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	private String itemName;

	@Temporal(TemporalType.DATE)
    private Date dateLost;
	
	private String location;
    private String description;
    
    
    private String imageName;
    private String imageType;
    
    @Lob
    private byte[] imageData;
    
    
	public LostItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LostItem(int id, User user, String itemName, Date dateLost, String location, String description) {
		super();
		this.id = id;
		this.user = user;
		this.itemName = itemName;
		this.dateLost = dateLost;
		this.location = location;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public void setItemName(String item_Name) {
		this.itemName = item_Name;
	}
	public Date getDateLost() {
		return dateLost;
	}
	public void setDateLost(Date dateLost) {
		this.dateLost = dateLost;
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
		return "LostItem [id=" + id + ", user=" + user + ", itemName=" + itemName + ", dateLost=" + dateLost
				+ ", location=" + location + ", description=" + description + ", imageName=" + imageName
				+ ", imageType=" + imageType + ", imageData=" + Arrays.toString(imageData) + "]";
	}
	
    
	
}
