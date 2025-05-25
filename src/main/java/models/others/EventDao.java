package models.others;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.EventMapper;

public class EventDao {
	
	private Class<EventMapper> cls = EventMapper.class;
	private Map<String, Object> map = new HashMap<>();
	
	// 학사일정 등록
	public boolean insert(Event event) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(event) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	// 학사일정 업데이트
	public boolean update(Event event) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(event) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	// 학사일정 삭제
	public boolean delete(int no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(no) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	
	// 학사일정 리스트
	public List<Event> eventList() {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).eventList();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
}
