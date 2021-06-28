package com.coms309.backend.Data;

public class Friend {
	private String name;
	private boolean online;
	public Friend(String name, boolean online) {
		super();
		this.name = name;
		this.online = online;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	
	
}
