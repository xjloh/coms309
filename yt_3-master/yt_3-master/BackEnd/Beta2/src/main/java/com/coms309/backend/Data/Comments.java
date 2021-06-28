package com.coms309.backend.Data;

import java.sql.Timestamp;

public class Comments {
	
	private int cid;
	private String content;
	private int upvotes;
	private int pid;
	private String author;
	private Timestamp createdTime;
	
	
	public Comments(int cid, String content, int upvotes, int pid, String author, Timestamp createdTime) {
		this.cid = cid;
		this.content = content;
		this.upvotes = upvotes;
		this.pid = pid;
		this.author = author;
		this.createdTime = createdTime;
	}
	public int getUpvotes() {
		return upvotes;
	}
	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	

}
