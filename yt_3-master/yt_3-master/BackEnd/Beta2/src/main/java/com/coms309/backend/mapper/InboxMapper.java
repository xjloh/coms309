package com.coms309.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.coms309.backend.Data.Conversation;
import com.coms309.backend.Data.Inbox;

@Mapper
public interface InboxMapper {

	/**
	 * Calls getUserInbox procedure which returns information regarding user inbox
	 * @param userId
	 * 		user id to query
	 * @return List of InboxView objects
	 */
	@Select("CALL getUserInbox(#{username})")
	@Options(statementType = StatementType.CALLABLE)
	List<Inbox> getInboxView(String username);
	
	/**
	 * Function to insert a new message into the mysql databae
	 * @param fromId
	 * 		sender id
	 * @param toId
	 * 		receiver id
	 * @param message
	 * 		message content
	 */
	@Insert("INSERT INTO inbox(fromUser, toUser,message,created) VALUES (#{fromId},#{toId},#{message} ,NOW())")
	void InsertMessage(String fromId, String toId, String message);
	
	/**
	 * Function to get messages between to users
	 * @param sender
	 * 		sender id
	 * @param receiver
	 * 		receiver id
	 * @return List containing messages between two users
	 */
	@Select("SELECT i.mid, i.message, i.created, i.fromUser FROM inbox i WHERE ( i.fromUser =#{sender} and i.toUser = #{receiver}) or (i.fromUser = #{receiver} and i.toUser = #{sender}) limit 20")
	List<Conversation> getChatView(String sender, String receiver);
	
	@Select("SELECT i.mid, i.message, i.created, i.fromUser FROM inbox i where i.mid > #{mid} and ( i.fromUser =#{sender} and i.toUser = #{receiver}) or (i.fromUser = #{receiver} and i.toUser = #{sender}) ")
	List<Conversation> updateChatView(int mid, String sender,String receiver);
}
