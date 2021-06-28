package com.coms309.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.coms309.backend.Data.Comments;

@Mapper
public interface CommentsMapper {
	
	@Insert("INSERT INTO comments(content,upvotes,pid, author,createdTime) values(#{content},0,#{pid},#{author},NOW())")
	public void insertComment(String content, int pid, String author);
	
	@Select("SELECT * from comments c where c.pid = #{pid}")
	public List<Comments> getCommentByPid(int pid);
	
	@Select("SELECT c.author from comments c where c.cid = #{cid}")
	public String getComment(int cid);
	
	
	@Select("SELECT c.upvotes from comments c where c.cid = #{cid}")
	public int getUpvotes(int cid);
	
	
	@Update("UPDATE comments c set upvotes = upvotes +1 where c.cid  = #{cid}")
	void updateUpvote(@Param("cid") int cid);
	
	@Update("UPDATE comments c set upvotes = (GREATEST(upvotes - 1, 0)) where c.cid  = #{cid}")
	void updateDownvote(@Param("cid") int cid);
	

}
