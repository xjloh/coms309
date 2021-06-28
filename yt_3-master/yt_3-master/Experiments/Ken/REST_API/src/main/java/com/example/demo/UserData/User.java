package com.example.demo.UserData;

import javax.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name="userId", nullable=false)
	private String userId;
	
	private String password;
	private String emailAddress;
	
	@Column(name="sessionKey", nullable=false)
	private String sessionKey;

	public Integer getId()
	{
		return id;
	}
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress()
	{
		return emailAddress;
	}
	
	public String getSessionKey()
	{
		return sessionKey;
	}
	
	public void setSessionKey(String sessionKey)
	{
		this.sessionKey = sessionKey;
	}
	
}
