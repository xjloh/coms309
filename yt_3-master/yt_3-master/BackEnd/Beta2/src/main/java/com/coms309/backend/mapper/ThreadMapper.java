package com.coms309.backend.mapper;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.javassist.compiler.ast.Pair;
import org.apache.ibatis.mapping.StatementType;

import com.coms309.backend.Data.Threads;
import com.coms309.backend.Data.Titles;
import com.coms309.backend.Data.User;


//NEED TO MODIFY IT WITH NEW DATABASE
@Mapper
public interface ThreadMapper  {

	/**
	 * Function to get threads based on content
	 * @param content
	 * 		content of thread
	 * @return a thread
	 */
	@Select("select * from threads where content = #{content}")
	public Threads findByC(@Param("content") String content);
	
	/**
	 * Function to get threads based on id
	 * @param tid
	 * 		thread id
	 * @return a thread
	 */
	@Select("select * from threads where tid = #{tid}")
	public Threads findById(@Param("tid") int tid);
	
	/**
	 * Function to get all threads
	 * @return list of threads
	 */
	@Select("Select * from threads")
	List<Threads> getAllThreads();
	
	/**
	 * Function to add a thread into the database
	 * @param t thread to add
	 */
	@Insert("INSERT INTO threads VALUE(#{tid},#{title},#{content},NOW(),#{latitude},#{longitude},#{author_id})")
	public void addThread(Threads t);
	
	/**
	 * Function to get threads within a certain radius of a point
	 * @param lng	longitude
	 * @param lat	latitude
	 * @param radius radius
	 * @return list of Threads within a radius of a point
	 */

	@Select("CALL getThreadsWithinRadius(#{lng},#{lat},#{radius})")
	@Options(statementType = StatementType.CALLABLE)
	List<Threads> getThreadsRadius(double lng, double lat , double radius);

	
}


