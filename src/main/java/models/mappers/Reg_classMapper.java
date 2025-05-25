package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import models.classes.Class1;
import models.classes.Student;

public interface Reg_classMapper {

	@Select("select count(user_no) from registered_classes where "
		+ " class_no = #{class_no} and ban = #{ban} and year = #{year} and term = #{term}")
	int studentCount(Class1 class1);

	@Select("select u.user_no, u.user_name, u.user_grade, rc.exam1_score, rc.exam2_score, rc.as_tot_score, rc.att_score "
			+ " from users u join registered_classes rc "
			+ " on u.user_no = rc.user_no where (class_no = #{class_no} and ban = #{ban} and year = #{year} and term = #{term})"
			+ " ORDER BY u.user_no")
	List<Student> studentList(Class1 class1);

	@Update("update registered_classes set exam1_score = #{exam1}, exam2_score = #{exam2} where user_no = #{user_no} "
			+ " and class_no = #{class_no} and ban = #{ban} and year = #{year} and term = #{term}")
	int updateExamScore(Map<String, Object> map);

	@Select("select user_no from registered_classes where user_no = #{user_no} and class_no = #{clasS_no} and ban = #{ban} and year = #{year} and term = #{term} and status = '1'")
	String isStudent(Map<String, Object> map);
	
}
