package models.boards;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.BoardMapper;

//이동원
public class BoardDao {
	private Class<BoardMapper> cls = BoardMapper.class;
	private Map<String, Object> map = new HashMap<>();

	public String selectName(String board_id) {
		SqlSession session = MyBatisConnection.getConnection();
		try { return session.getMapper(cls).selectName(board_id);
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session); }
		return null;
	}
	
}
