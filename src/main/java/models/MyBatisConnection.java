package models;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConnection {
	private static SqlSessionFactory sqlMap;
	static {
		String resource = "models/mappers/mybatis-config.xml";
		InputStream input = null;
		try {
			input = Resources.getResourceAsStream(resource);
		} catch(IOException e) { e.printStackTrace(); }
		sqlMap = new SqlSessionFactoryBuilder().build(input);
	}
	public static SqlSession getConnection() {
		return sqlMap.openSession();
	}
	public static void close(SqlSession session) {
		session.commit();
		session.close();
	}
}
