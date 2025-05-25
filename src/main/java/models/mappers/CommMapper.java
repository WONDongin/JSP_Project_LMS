package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import models.boards.Comment;

public interface CommMapper {

	@Insert("insert into comments (arti_no, user_no, comm_content) "
			+ " values(#{arti_no}, #{user_no}, #{comm_content})")
	int insert(Comment comm);

	@Select("select c.*, u.user_name from comments c "
			+ " join users u on c.user_no = u.user_no "
			+ " where arti_no = #{value}")
	List<Comment> list(int arti_no);

	@Select("select c.*, u.user_name from comments c join users u on c.user_no = u.user_no where comm_no = #{value}")
	Comment selectOne(int comm_no);

	@Delete("delete from comments where comm_no = #{value}")
	int delete(int comm_no);
	
}
