package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import models.classes.Assignment;
import models.classes.Sub_as;

public interface SubAsMapper {

	@Update("UPDATE submitted_assignments SET as_score = #{as_score} WHERE user_no = #{user_no} AND as_no = #{as_no}")
	public int updateScore(Map<String, Object> map);

	@Select("select u.user_no, u.user_grade, u.user_name, sa.as_no as_no, sa.file , sa.as_score as_score from users u "
		+ " join submitted_assignments sa on u.user_no = sa.user_no where as_no = #{as_no}")
	public List<Sub_as> list(int as_no);

	@Select("select u.user_no, u.user_grade, u.user_name, sa.as_no as_no, sa.file file, sa.as_score as_score from users u "
			+ " join submitted_assignments sa on u.user_no = sa.user_no where as_no = #{as_no} and u.user_no = #{user_no}")
	public Sub_as selectOne(Map<String, Object> map);
	
	@Delete("Delete from submitted_assignments where user_no=#{user_no} and as_no=#{as_no}")
	public void deleteStd_list(@Param("user_no") String a,@Param("as_no") int as_no);

	@Select("select count(user_no) submittedCount from submitted_assignments where as_no = #{as_no}")
	public int count(Assignment tmp);
	
}
