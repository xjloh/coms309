package com.coms309.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.coms309.backend.Data.User;


@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM coms_309.User;")
	List<User> findAll();
	/**
	 * Function to insert a new user into the database
	 * @param user a new user with its information
	 */
	@Insert("INSERT INTO User (uid, username, password, email, longitude, latitude, sessionKey) VALUES(#{uid},#{username},#{password},#{email},#{longitude},#{latitude},#{sessionKey});")
	void insertUser(User user);
	
	/*
	 * @ConstructorArgs(value = {
	 * 
	 * @Arg(column = "password", javaType = String.class) })
	 */

	/**
	 * Function to select a user based on username
	 * @param username
	 * @return a user
	 */
	@Select("select * from User where username = #{username}")
	User getUserByUsername(@Param("username") String username);
	
	/**
	 * Function to select a user based on id
	 * @param username
	 * @return a user
	 */
	@Select("SELECT * FROM User u where u.uid = #{uid}")
	User findById(String uid);
	
	/**
	 * Function to get a user id based on session key
	 * @param sessionKey 
	 * @return user id
	 */
	@Select("SELECT u.uid FROM User u where u.sessionKey= #{sessionKey}")
	String findUserIdBySessionKey(String sessionKey);	
	
	@Select("Select * FROM User u  where u.username = #{username}")
	User getSessionKey(@Param("username") String username);
	
	
	
	/**
	 * Function to update a users bio.
	 * @param username
	 * @param bio
	 */
	@Update("UPDATE User set bio = #{bio} where username = #{username}")
	void updateUserBio(@Param("username") String username, @Param("bio") String bio);
	
	@Update("UPDATE User set isOnline = #{online} where username = #{username}")
	void updateUserOnline(@Param("username") String username, @Param("online") int online);
	
		
	@Update("UPDATE User set username = #{username}, email = #{email}, bio = #{bio} where uid = #{user_id}")
	public void updateUserProfile(@Param("username") String username, @Param("email") String email, @Param("bio")String bio, @Param("user_id") int user_id);
	/**
	 * function to update user session key
	 * @param username
	 * @param sessionKey
	 */
	@Update("UPDATE User set sessionKey = #{sessionKey} where username = #{username}")
	void addSessionKey(@Param("username") String username, @Param("sessionKey") String sessionKey);
	
	/**
	 * function to update user location
	 * @param longitude
	 * @param latitude
	 * @param username
	 */
	@Update("UPDATE User set longitude = #{longitude} ,latitude = #{latitude} where username = #{username}")
	void updateUserLocation(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("username") String username);
}
