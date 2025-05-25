package models.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.SubAsMapper;


public class SubAsDao {
	private Class<SubAsMapper> cls = SubAsMapper.class;
	private Map<String, Object> map = new HashMap<>();
	private int i;
	
	public int updateScores(int as_no, Map<String, Integer> scores) {
		i = 0; 						// 학번, 점수
		SqlSession conn = MyBatisConnection.getConnection();
		map.clear();
		 try {
			 map.put("as_no", as_no);
			 scores.forEach((user_no, sc) -> {
				 map.put("user_no", user_no);
				 map.put("as_score", sc);
				 if(conn.getMapper(cls).updateScore(map) == 1) { i++; }
			 });
		 } catch(Exception e) {
			 e.printStackTrace();
		 } finally {
			 MyBatisConnection.close(conn);
		 }
		 return i;
	}
	public List<Sub_as> list(int as_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).list(as_no);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	public Sub_as selectOne(String user_no, int as_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("user_no", user_no);
			map.put("as_no", as_no);
			return session.getMapper(cls).selectOne(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	//수업별 과제 제출 수강생 목록  삭제==============================
	public void deleteStd_list(String a, int as_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			 session.getMapper(cls).deleteStd_list(a, as_no);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}			
	}
	public int count(Assignment tmp) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).count(tmp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return 0;
	}
	
	
	
}
