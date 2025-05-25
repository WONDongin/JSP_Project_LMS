package models.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.UserMapper;

/* model/users/UsersDao.java - 김기흔 */

public class UserDao {
	private Class<UserMapper> cls = UserMapper.class;
	private Map<String, Object> map = new HashMap<>();

	//로그인정보 부르기
	public User selectOne(String user_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOne(user_no);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	//아이디 잧기
	public String userSearch(String user_name, String email) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).userSearch(user_name, email);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;

	}

	//있는 계정인지 확인
	public String isUser(String user_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).isUser(user_no);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	// 비밀번호 초기화
	public int pwReset(String user_no, String randpw) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).pwReset(user_no, randpw);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}

	//내 정보 수정
	public boolean update(User user) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(user) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// 사용자 추가
	public boolean insert(User user) {
		SqlSession conn = MyBatisConnection.getConnection();
		try {
			if (conn.getMapper(cls).insert(user) > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(conn);
		}
		return false;
	}

	//사용자 삭제
	public boolean deleteuser(String user_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).deleteuser(user_no) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	// 비밀번호 재설정
	public boolean updatePass(String user_no, String n_pass1) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).updatePass(user_no, n_pass1) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;

	}

	// 모든 리스트 호출
	public List<User> selectAll() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	//사용자 검색
	public List<User> searchUsers(String type, String keyword) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			switch (type) {
			case "user_no":
				return session.getMapper(cls).searchByUserNo(keyword + "%");
			case "user_name":
				return session.getMapper(cls).searchByUserName(keyword + "%");
			case "role":
				return session.getMapper(cls).searchByRole(keyword);
			default:
				return session.getMapper(cls).selectAll(); // 예외처리용
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public boolean pwCheck(String user_no, String inputPass) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("user_no", user_no);
			map.put("password", inputPass);
			return session.getMapper(cls).pwCheck(map) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	public String selectName(String user_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectName(user_no);
    } catch (Exception e) {
          e.printStackTrace();
    } finally {
      MyBatisConnection.close(session);
    }
    return null;
  }
	
	// 원동인 학사일정알림 대상자조회(학생/교수)
	public List<String> getUserNosByRole() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).getUserNosByRole();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
  
}