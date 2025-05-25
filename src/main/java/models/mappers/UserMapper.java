package models.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import models.users.User;

/* model/mappers/UsersMapper.java - 김기흔 */

public interface UserMapper {

	//사용자 추가
	@Insert("INSERT INTO users (user_no, password, role, email, tel, user_name, gender, user_grade, major_no) " +
	        "VALUES (#{user_no}, #{password}, #{role}, #{email}, #{tel}, #{user_name}, #{gender}, #{user_grade}, #{major_no})")
	int insert(User user);
	
	//로그인
	@Select("select * from users where user_no = #{value}")
	User selectOne(String user_no);
	
	//아이디찾기
	@Select("select user_no from users where user_name=#{user_name} and email=#{email}")
	String userSearch(@Param("user_name") String user_name, @Param("email") String email);
	
	//계정정보 존재확인
	@Select("select user_no from users where user_no=#{value}")
	String isUser(String user_no);
	
	//비밀번호 초기화
	@Update("update users set password=#{randpw} where user_no=#{user_no}")
	int pwReset(@Param("user_no") String user_no, @Param("randpw") String randpw);
	
	//내 정보 수정
	@Update("update users set user_name=#{user_name}, gender=#{gender}, tel=#{tel}, email=#{email},"
				+ " user_grade=#{user_grade}, major_no=#{major_no} where user_no=#{user_no}")
	int update(User user);
	
	//비밀번호 재설정
	@Update("update users set password=#{n_pass1} where user_no=#{user_no}")
	int updatePass(@Param("user_no") String user_no, @Param("n_pass1") String n_pass1);
	
	// 사용자 삭제
	@Delete("delete from users where user_no=#{value}")
	int deleteuser(String user_no);
	
	//모든 사용자 정보 출력
	@Select("select * from users")
	List<User> selectAll();

    //사용자 검색========================================================================
  @Select("select * from users where user_no like #{keyword} order by user_no")
  List<User> searchByUserNo(@Param("keyword") String keyword);

  @Select("select * from users where user_name like #{keyword} order by user_no")
  List<User> searchByUserName(@Param("keyword") String keyword);

  @Select("select user_no from users where user_no = #{user_no} and password = #{password}")
  int pwCheck(Map<String, Object> map);

  @Select("select * from users where role = #{keyword} order by user_no")
  List<User> searchByRole(@Param("keyword") String keyword);

  @Select("select user_name from users where user_no = #{user_no}")
  String selectName(String user_no);
  
  

  //원동인 학사일정알림 대상자조회(학생/교수)
  @Select("select user_no from users where role in (1, 2)")
  List<String> getUserNosByRole();
  
}
