package models.others;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.MajorMapper;

public class MajorDao {
	private Class<MajorMapper> cls = MajorMapper.class;
	private Map<String, Object> map = new HashMap<>();
	 
	public Major selectOne(String major_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOne(major_no);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

}
