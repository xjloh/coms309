package com.example.demo.WebSocket;
import javax.websocket.Session;

public class UserData 
{
	private String userId;
	private Session session;
	private float latitude;
	private float longitude;
	private String connectedUser;
	
	// Initialize new connected user
	UserData(String userId, Session session, float latitude, float longitude)
	{
		this.userId = userId;
		this.session = session;
		this.latitude = latitude;
		this.longitude = longitude;
		this.connectedUser = null;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public String getConnectedUser() {
		return connectedUser;
	}
	public void setConnectedUser(String connectedUser) {
		this.connectedUser = connectedUser;
	}
}
