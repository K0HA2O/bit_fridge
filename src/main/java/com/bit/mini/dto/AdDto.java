package com.bit.mini.dto;

public class AdDto {

	private int id;
	private String imageUrl;
	private String linkUrl;
	private boolean isActive;

	public AdDto() {
	}

	public AdDto(int id, String imageUrl, String linkUrl, boolean isActive) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
		this.linkUrl = linkUrl;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
