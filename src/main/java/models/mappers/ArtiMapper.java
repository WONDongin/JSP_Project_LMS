package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import models.boards.Article;

public interface ArtiMapper {
	
	@Select("<script>select count(arti_no) from articles a"
			+ " join users u on a.user_no = u.user_no "
			+ " where board_id = #{board_id} "
			+ " <if test='column != null'> " 
			+ " and ${column} like concat('%',#{keyword},'%') "
			+ " </if></script>")
	int count(Map<String, Object> map);

	@Select("<script>"
			+ "select a.*, u.user_name, count(c.comm_no) commCount from articles a "
			+ " join users u on a.user_no = u.user_no "
			+ " left join comments c on a.arti_no = c.arti_no "
			+ " where board_id = #{board_id} "
			+ " <if test='column != null'> " 
			+ " and ${column} like concat('%',#{keyword},'%') "
			+ " </if>"
			+ " group by a.arti_no"
			+ " order by arti_no desc limit #{start}, #{limit}</script>")
	List<Article> list(Map<String, Object> map);

	@Select("select a.*, u.user_name from articles a "
			+ " join users u on a.user_no = u.user_no "
			+ " where arti_no = #{value}")
	Article selectOne(int arti_no);

	@Insert("insert into articles (board_id, arti_title, arti_content, user_no) "
			+ "values (#{board_id}, #{arti_title}, #{arti_content}, #{user_no}) ")
	int insert(Article arti);

	@Select("select board_id from articles where arti_no = #{arti_no}")
	String getBoard_id(int arti_no);

	@Delete("delete from articles where arti_no = #{arti_no}")
	int delete(int arti_no);

	@Update("update articles set arti_title = #{arti_title}, arti_content = #{arti_content}, file = #{file} "
			+ " where arti_no = #{arti_no}")
	boolean update(Article arti);

}
