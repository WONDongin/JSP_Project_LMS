package models.boards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import models.MyBatisConnection;
import models.mappers.CommMapper;


//이동원
public class CommentDao {
	private Class<CommMapper> cls = CommMapper.class;
	private Map<String, Object> map = new HashMap<>();

	public boolean insert(Comment comm) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).insert(comm) > 0;
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session);
		}
		return false;
	}

	public List<Comment> list(int arti_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).list(arti_no);
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session);
		}
		return null;
	}

	public Comment selectOne(int comm_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).selectOne(comm_no);
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session);
		}
		return null;
	}

	public boolean delete(int comm_no) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			return session.getMapper(cls).delete(comm_no) > 0;
		} catch(Exception e) { e.printStackTrace();
		} finally { MyBatisConnection.close(session);
		}
		return false;
	}

}
