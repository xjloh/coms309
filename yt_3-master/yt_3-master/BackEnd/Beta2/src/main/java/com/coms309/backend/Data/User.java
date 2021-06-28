package com.coms309.backend.Data;

public class User {
	
	private int uid;
	private String username;
	private String password;
	private String email;
	private float longitude;
	private float latitude;
	private String sessionKey;
	private String online;
	private String bio;
	private String imgPath;
	

	public User(int uid, String username, String password, String email) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User(int uid, String username, String password, String email, float longitude, float latitude,
			String sessionKey, String online, String bio, String imgPath) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sessionKey = sessionKey;
		this.online = online;
		this.bio = bio;
		this.imgPath = imgPath;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}


	public String getBio() {
		return bio;
	}


	public void setBio(String bio) {
		this.bio = bio;
	}


	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}	
}
