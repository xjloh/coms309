package com.coms309.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.coms309.backend.Data.Friend;

@Mapper
public interface FriendMapper {
	
	//ADD FRIEND
	@Insert("INSERT INTO friendList VALUES(#{uid},#{fid})")
	public void addFriend(String uid, String fid);
	
	//FIND FRIEND
	@Select("SELECT u.username,u.isOnline from User u where u.username in (SELECT f.friend_id from friendList f where f.user_id = #{uid} )")
	public List<Friend> findAllFriends(String uid);
	
	//DELETE FRIEND
	@Delete("DELETE FROM friendList where friend_id = #{fid} and user_id = #{uid}")
	public void deleteFriend(String uid, String fid);
	
}
