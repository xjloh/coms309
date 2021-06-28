package com.coms309.backend.Data;

import java.sql.Timestamp;

public class Threads {
	private int tid;
	private String title;
	private String content;
	private Timestamp createdTime;
	private double longitude;
	private double latitude;
	private String author_id;
	
	
	public Threads(int tid_t, String title_t, String content_t,Timestamp time_t, double longitude_t, double latitude_t, String a_id) {
		// TODO Auto-generated constructor stub
		this.tid = tid_t;
		this.title = title_t;
		this.content= content_t;
		this.longitude=longitude_t;
		this.latitude =latitude_t;
		this.createdTime = time_t;
		this.author_id= a_id;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getTime_stamp() {
		return createdTime;
	}
	public void setTime_stamp(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
