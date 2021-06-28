package com.coms309.backend.Data;

import java.sql.Timestamp;

public class Titles {
	
	private String threadId;
	private String title;
	public Titles() {
		
	}

	public Titles(String threadId, String title) {
		super();
		this.threadId = threadId;
		this.title = title;
	}
	public String getThreadId() {
		return threadId;
	}
	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	
}
