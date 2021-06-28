package com.coms309.backend.Data;

public class MessagePoints {
	private double latitude;
	private double longitude;
	private int tid;
	private String title;
	
	public MessagePoints(int tid, String title, double latitude, double longitude ) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.tid = tid;
		this.title = title;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
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

}
