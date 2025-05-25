package models.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.Reg_classMapper;

public class Reg_classDao {
	private Class<Reg_classMapper> cls = Reg_classMapper.class;
	private Map<String, Object> map = new HashMap<String, Object>();
	
	public int studentCount(Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).studentCount(class1);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return -1;
	}

	public List<Student> studentList(Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 return session.getMapper(cls).studentList(class1);
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return null;
	}

	// 
	public boolean updateExamScore(Class1 class1, List<Student> studentList, Map<String, Integer[]> exMap) {
		SqlSession session = MyBatisConnection.getConnection();
		map.clear();
		map.put("class_no", class1.getClass_no());
		map.put("ban", class1.getBan());
		map.put("year", class1.getYear());
		map.put("term", class1.getTerm());
		 try {
			 for(Student st : studentList) {
				 map.put("user_no", st.getUser_no());
				 map.put("exam1", exMap.get(st.getUser_no())[0]);
				 map.put("exam2", exMap.get(st.getUser_no())[1]);
				 if(session.getMapper(cls).updateExamScore(map) <= 0) { continue; }
			 }
			 return true;
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		return false;
	}

	public boolean checkStudent(Class1 target, String user_no) {
		SqlSession session = MyBatisConnection.getConnection();
		 try {
			 map.clear();
			 map.put("user_no", user_no);
			 map.put("class_no", target.getClass_no());
			 map.put("ban", target.getBan());
			 map.put("year", target.getYear());
			 map.put("term", target.getTerm());
			 return user_no.equals(session.getMapper(cls).isStudent(map));
		 } catch (Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(session);
		 }
		 return false;
	}
	

}
