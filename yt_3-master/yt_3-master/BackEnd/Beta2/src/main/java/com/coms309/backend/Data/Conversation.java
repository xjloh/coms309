package com.coms309.backend.Data;

import java.sql.Timestamp;

public class Conversation {
	private String message;
	private Timestamp time;
	private String  name;
	private int mid;
	
	public Conversation(int mid,String message, Timestamp time, String name) {
		super();
		this.mid = mid;
		this.message = message;
		this.time = time;
		this.name = name;
		
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	
}
