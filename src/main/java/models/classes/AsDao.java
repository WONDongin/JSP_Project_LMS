package models.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.AsMapper;



public class AsDao {
	 private Class<AsMapper> cls = AsMapper.class;
	 private Map<String, Object> map = new HashMap<>();
	
	 // 과제 추가==============================================
	 public boolean insert(Assignment as) {
		 SqlSession conn = MyBatisConnection.getConnection();
		 try {
			 return conn.getMapper(cls).insert(as) > 0;
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(conn);
		 }
		 return false;
	}
	// 과제 목록 조회==========================================
	public List<Assignment> list(Class1 class1) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).list(class1);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}
	//과제 선택=============================================
	public Assignment selectOne(int as_no) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectOne(as_no);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}
	// 과제 수정=============================================
	public boolean update(Assignment as) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(as) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	// 과제 삭제 ============================================
	public boolean deleteAssignment(int as_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).deleteuser(as_no) > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	//제출한 과제==========================================================
	public Sub_as selectSub_as(String user_no, int as_no) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectSub_as(user_no, as_no);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;		
	}
	//제출된 과제 수정
	public boolean updateAs(Sub_as as) {
		 SqlSession conn = MyBatisConnection.getConnection();
		 try {
			 return conn.getMapper(cls).updateAs(as) > 0;
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(conn);
		 }
		 return false;	
	}
	//해당 과목을 수강하는 학생 user_no 리스트================================
	public List<String> selectReg_Std(String class_no) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectReg_Std(class_no);
			  } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}
	// 메인_과제목록 (학생용)
	public List<Assignment> selectAssignmentsByStudent(String classNo) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectAssignmentsByStudent(classNo);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}
	
	//해당 수업의 과제 번호들 select=================================
	public List<Integer> selectAs_no(Class1 class1) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectAs_no(class1);
			 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}

	// 메인_과제목록 (교수용)
	public List<Assignment> selectAssignmentsByProf(String classNo) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectAssignmentsByProf(classNo);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}

	public String selectFile(String user_no, int as_no) {
		 SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).selectFile(user_no, as_no);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}
}
