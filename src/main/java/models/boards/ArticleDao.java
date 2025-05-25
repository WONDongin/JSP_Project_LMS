package models.boards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.ArtiMapper;
//이동원
public class ArticleDao {
	private Class<ArtiMapper> cls = ArtiMapper.class;
	private Map<String, Object> map = new HashMap<>();

	public int count(String board_id, String column, String keyword) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("board_id", board_id);
			map.put("column", column);
			map.put("keyword", keyword);
			return session.getMapper(cls).count(map);
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session);
		}
		return 0;
	}

	public List<Article> list(String board_id, int pageNum, int limit, String column, String keyword) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			map.clear();
			map.put("board_id", board_id);
			map.put("start", (pageNum-1) * limit);
			map.put("limit", limit);
			map.put("column", column);
			map.put("keyword", keyword);
			return session.getMapper(cls).list(map);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public Article selectOne(int arti_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOne(arti_no);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public boolean insert(Article arti) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(arti) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	public String getBoard_id(int arti_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).getBoard_id(arti_no);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public boolean delete(int arti_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(arti_no) > 0;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	public boolean update(Article arti) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).update(arti);
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
}
