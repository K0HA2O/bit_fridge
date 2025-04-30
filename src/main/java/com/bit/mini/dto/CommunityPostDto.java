package com.bit.mini.dto;

import java.sql.Timestamp;

public class CommunityPostDto {

	private int id;
	private String title;
	private String content;
	private String imagePath;
	private int userId; // 매핑: user_id
	private int hit; // 매핑: hit
	private Timestamp created_at; // 매핑: created_at
	private Timestamp updatedAt; // 매핑: updated_at
	private String author; // (posts테이블에 없는거)
	private int likeCount; // (posts테이블에 없는거)

	public CommunityPostDto() {

	}

	public CommunityPostDto(int id, String title, String content, String imagePath, int userId, int hit,
			Timestamp created_at, Timestamp updatedAt, String author, int likeCount) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.imagePath = imagePath;
		this.userId = userId;
		this.hit = hit;
		this.created_at = created_at;
		this.updatedAt = updatedAt;
		this.author = author;
		this.likeCount = likeCount;
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public String getImagePath() {

		return imagePath;
	}

	public void setImagePath(String imagePath) {

		this.imagePath = imagePath;
	}

	public int getUserId() {

		return userId;
	}

	public void setUserId(int userId) {

		this.userId = userId;
	}

	public int getHit() {

		return hit;
	}

	public void setHit(int hit) {

		this.hit = hit;
	}

	public Timestamp getCreated_at() {

		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {

		this.created_at = created_at;
	}

	public Timestamp getUpdatedAt() {

		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {

		this.updatedAt = updatedAt;
	}

	public String getAuthor() {

		return author;
	}

	public void setAuthor(String author) {

		this.author = author;
	}

	public int getLikeCount() {

		return likeCount;
	}

	public void setLikeCount(int likeCount) {

		this.likeCount = likeCount;
	}

}
