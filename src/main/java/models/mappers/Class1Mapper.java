package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;

import models.classes.Class1;

public interface Class1Mapper {

	@Select("SELECT * FROM classes WHERE class_no = #{class_no} AND ban = #{ban} AND year = #{year} AND term = #{term}")
	Class1 selectOne(Class1 class1);
	
	@Select("SELECT c.* , rc.status , u.user_name AS prof " + "FROM registered_classes rc "
			+ "JOIN classes c ON rc.class_no = c.class_no AND rc.ban = c.ban AND rc.year = c.year AND rc.term = c.term "
			+ "JOIN users u ON u.user_no = c.user_no "
			+ "WHERE rc.user_no = #{user_no} AND rc.year = #{year} AND rc.term = #{term}")
	List<Class1> nowClassList(Class1 class1);
	
	@Select("SELECT days " + "FROM class_days " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} "
			+ "AND term = #{term}")
	List<Integer> selectDaysByClass(Class1 class1);

	@Select("SELECT * " + "FROM classes " + "WHERE user_no = #{user_no}")
	List<Class1> selectByProfessor(@Param("user_no") String userNo);

	@Insert("INSERT INTO classes " + "(class_no, ban, year, term, major_no, user_no, class_name, class_grade, credit, "
			+ "classroom, s_time, e_time, max_p, c_plan, file , s_date, e_date) " + "VALUES " + "(#{class_no}, #{ban}, #{year}, #{term}, #{major_no}, #{user_no}, "
			+ "#{class_name}, #{class_grade}, #{credit}, #{classroom}, " + "#{s_time}, #{e_time}, #{max_p}, #{c_plan}, #{file}, #{s_date}, #{e_date})")
	int insertClass(Class1 class1);

	@Insert("INSERT INTO class_days " + "(class_no, ban, year, term, days) " + "VALUES "
			+ "(#{class1.class_no}, #{class1.ban}, #{class1.year}, #{class1.term}, #{day})")
	int insertDay(@Param("class1") Class1 class1, @Param("day") Integer day);

	@Update("UPDATE classes SET " + "class_name = #{class_name}, credit = #{credit}, classroom = #{classroom}, "
			+ "s_time = #{s_time}, e_time = #{e_time}, c_plan = #{c_plan}, file = #{file}, max_p = #{max_p}, s_date = #{s_date}, e_date =#{e_date}" + "WHERE class_no = #{class_no} "
			+ "AND ban = #{ban} AND year = #{year} AND term = #{term}")
	int updateClass(Class1 class1);

	@Delete("DELETE FROM class_days " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} " + "AND term = #{term}")
	int deleteDays(Class1 class1);

	@Delete("DELETE FROM classes " + "WHERE class_no = #{class_no} " + "  AND ban = #{ban} " + "  AND year = #{year} " + "  AND term = #{term}")
	int deleteClass(Class1 class1);
	
	@Delete("DELETE FROM registered_classes WHERE user_no = #{user_no} AND class_no = #{class_no} AND ban = #{ban} AND year = #{year} AND term = #{term}")
	int cancelClass(Class1 class1);
	
	/*	@Select("SELECT * " + "FROM classes WHERE year = #{class1.year} " 
				+ "AND term = #{class1.term} " 
				+ "AND class_no "
				+ "NOT IN (SELECT class_no "
				+ "FROM registered_classes " 
				+ "WHERE user_no = #{user_no}) ")*/
	@Select(
		  "<script>"
		  + "SELECT c.*, u.user_name AS prof_name "
		  + "FROM classes c "
		  + "JOIN users u ON c.user_no = u.user_no "
		  + "WHERE c.year = #{class1.year} "
		  + "AND c.term = #{class1.term} "
		  + "<if test='user_no != null and user_no != \"\"'>"
		  + "AND c.class_no NOT IN ( "
		  + "SELECT class_no "
		  + "FROM registered_classes "
		  + "WHERE user_no = #{user_no}) "
		  + "</if>"
		  + "<if test='type == \"class_name\"'> "
		  + "AND c.class_name LIKE CONCAT('%', #{fine}, '%') "
		  + "</if> "
		  + "<if test='type == \"user_name\"'> "
		  + "AND u.user_name LIKE CONCAT('%', #{fine}, '%') "
		  + "</if>"
		  + "</script>"
		)
	List<Class1> selectClassesByYearTerm(@Param("class1") Class1 class1 , @Param("user_no") String user_no, @Param("type") String type, @Param("fine") String fine);

	@Select("SELECT COUNT(*) " + "FROM registered_classes " + "WHERE class_no = #{class_no} " + "AND ban = #{ban} " + "AND year = #{year} "
			+ "AND term = #{term}")
	int enrolledCount(Class1 class1);

	@Insert("INSERT INTO registered_classes(user_no, class_no, ban, year, term, status) "
			+ "VALUES (#{user_no}, #{class_no}, #{ban}, #{year}, #{term}, 0)")
	boolean insertRegisteredClass(Class1 class1);
	
	@Select("SELECT COUNT(*) FROM registered_classes " +
	        "WHERE user_no = #{user_no} AND year = #{year} AND term = #{term}")
	int countRegistered(Class1 class1);

	@Update("UPDATE registered_classes SET status = 1 WHERE user_no = #{user_no} AND class_no = #{class_no} AND ban = #{ban} AND year = #{year} AND term = #{term}")
	int confirmClass(Class1 cls);
	
	@Select(
		"<script>"
	    + "SELECT c.*, u.user_name AS prof_name "
	    + "FROM classes c "
	    + "JOIN users u ON c.user_no = u.user_no"
	    + "<where>"
		+ "<if test='type == \"class_name\"'> "
		+ "c.class_name LIKE CONCAT('%', #{fine}, '%') "
		+ "</if> "
		+ "<if test='type == \"user_name\"'> "
		+ "u.user_name LIKE CONCAT('%', #{fine}, '%') "
		+ "</if>"
		+ "</where>"
	    + "</script>"
	)
	List<Class1> selectAllClass(@Param("type") String type, @Param("fine") String fine);
	
	
	@Select("SELECT * FROM classes " + "WHERE year = #{year} AND term = #{term} ")
	List<Class1> selectTimeClash(Class1 class1);
  
	// 강의계획서(원동인)
	@Select("SELECT * FROM classes WHERE class_no = #{class_no} AND ban = #{ban} AND year = #{year} AND term = #{term}")
	List<Class1> classinfoList(Class1 class1);
	
	// 세션에 교수이름(원동인)
	@Select("select user_name from users where user_no=#{user_no}")
	String selectProf(Class1 class1);

	@Select(
	        "<script>"
	      + "SELECT c.*, rc.exam1_score, rc.exam2_score, rc.as_tot_score, rc.att_score, u.user_name "
	      + "AS prof, rc.year,rc.term "
	      + "FROM registered_classes rc "
	      + "JOIN classes c "
	      + "ON rc.class_no=c.class_no "
	      + "AND rc.ban=c.ban "
	      + "AND rc.year=c.year "
	      + "AND rc.term=c.term "
	      + "JOIN users u "
	      + "ON c.user_no=u.user_no "
	      + "WHERE rc.user_no=#{user_no} "
	      + "AND rc.status=2 "
	      + "<if test=\"type=='class_name'\">AND c.class_name LIKE CONCAT('%',#{fine},'%') </if>"
	      + "<if test=\"type=='user_name'\">AND u.user_name LIKE CONCAT('%',#{fine},'%') </if>"
	      + "<if test=\"type=='year_term'\">AND CONCAT(rc.year,'-',rc.term) LIKE CONCAT('%',#{fine},'%') </if>"
	      + "</script>"
	    )
	    List<Class1> selectGradesByUserFilter(@Param("user_no") String userNo, @Param("type") String type, @Param("fine") String fine );
  
}