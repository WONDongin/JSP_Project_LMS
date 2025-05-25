package models.classes;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import models.MyBatisConnection;
import models.mappers.Class1Mapper;
import models.mappers.UserMapper;
import models.users.User;

public class Class1Dao {

	public boolean insertClass(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			if (clsMapper.insertClass(cls) == 0) {
				session.rollback();
				return false;
			}
			for (Integer d : cls.getDays()) {
				clsMapper.insertDay(cls, d);
			}
			session.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	public List<Class1> selectByProfessor(String userNo) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			
			List<Class1> list = clsMapper.selectByProfessor(userNo);
			
			for (Class1 cls : list) {
				List<Integer> days = clsMapper.selectDaysByClass(cls);
				int cont_p = clsMapper.enrolledCount(cls);
				cls.setNow_p(cont_p);
				cls.setDays(days);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public Class1 selectOne(Class1 key) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			Class1 cls = clsMapper.selectOne(key);
			if (cls != null) {
				cls.setDays(clsMapper.selectDaysByClass(key));
			}
			return cls;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public boolean update(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			if (clsMapper.updateClass(cls) == 0) {
				session.rollback();
				return false;
			}
			clsMapper.deleteDays(cls);
			for (Integer d : cls.getDays()) {
				clsMapper.insertDay(cls, d);
			}
			session.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	public boolean delete(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			clsMapper.deleteDays(cls);
			if (clsMapper.deleteClass(cls) == 0) {
				session.rollback();
				return false;
			}
			session.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	public boolean cancelClass(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			if (clsMapper.cancelClass(cls) == 0) {
				session.rollback();
				return false;
			}
			session.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	
	public boolean confirmClass(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			if (clsMapper.confirmClass(cls) == 0) {
				session.rollback();
				return false;
			}
			session.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}

	public List<Class1> selectClassesByYearTerm(Class1 cls, String user_no, String type, String fine) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			List<Class1> list = clsMapper.selectClassesByYearTerm(cls, user_no, type, fine);
			for (Class1 clsDF : list) {
				User profName = userMapper.selectOne(clsDF.getUser_no());
				List<Integer> days = clsMapper.selectDaysByClass(clsDF);
				int cont_p = clsMapper.enrolledCount(clsDF);
				clsDF.setDays(days);
				clsDF.setProf(profName.getUser_name());
				clsDF.setNow_p(cont_p);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public boolean insertRegisteredClass(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			if (!clsMapper.insertRegisteredClass(cls)) {
				session.rollback();
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return false;
	}
	public List<Class1> nowClassList(Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			List<Class1> list = clsMapper.nowClassList(class1);
			for (Class1 cls : list) {
				List<Integer> days = clsMapper.selectDaysByClass(cls);
				int cont_p = clsMapper.enrolledCount(cls);
				cls.setNow_p(cont_p);
				cls.setDays(days);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	public int enrolledCount(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
	        Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
	        return clsMapper.enrolledCount(cls);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        MyBatisConnection.close(session);
	    }
	    return 0;
	}
	public int countRegistered(Class1 cls) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
	        Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
	        return clsMapper.countRegistered(cls);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        MyBatisConnection.close(session);
	    }
	    return 0;
	}

	public List<Class1> selectAllClass(String type, String fine) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			UserMapper userMapper = session.getMapper(UserMapper.class);
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			List<Class1> list = clsMapper.selectAllClass(type, fine);
			for (Class1 clsDF : list) {
				User profName = userMapper.selectOne(clsDF.getUser_no());
				List<Integer> days = clsMapper.selectDaysByClass(clsDF);
				int cont_p = clsMapper.enrolledCount(clsDF);
				clsDF.setDays(days);
				clsDF.setProf(profName.getUser_name());
				clsDF.setNow_p(cont_p);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}

	public List<Class1> selectTimeClash(Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			
			List<Class1> list = clsMapper.selectTimeClash(class1);
			
			for (Class1 cls : list) {
				List<Integer> days = clsMapper.selectDaysByClass(cls);
				int cont_p = clsMapper.enrolledCount(cls);
				cls.setNow_p(cont_p);
				cls.setDays(days);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}
	
	// 강의 계획서(원동인)
	public List<Class1> classinfoList(Class1 class1) {
		SqlSession session = MyBatisConnection.getConnection();
		try {
			Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
			return clsMapper.classinfoList(class1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MyBatisConnection.close(session);
		}
		return null;
	}


	// 세션에 교수이름(원동인)
	 public String selectProf(Class1 class1) {
	      SqlSession session = MyBatisConnection.getConnection();
	      try {
	         Class1Mapper clsMapper = session.getMapper(Class1Mapper.class);
	         return clsMapper.selectProf(class1);
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         MyBatisConnection.close(session);
	      }
	      return null;   
	   }
  
	 public List<Class1> selectGradesByUserFilter(String userNo, String type, String fine) {
        SqlSession session = MyBatisConnection.getConnection();
        try {
            Class1Mapper mapper = session.getMapper(Class1Mapper.class);
            List<Class1> list = mapper.selectGradesByUserFilter(userNo, type, fine);
            for (Class1 cls : list) {
                List<Integer> days = mapper.selectDaysByClass(cls);
                cls.setDays(days);
                cls.setNow_p(mapper.enrolledCount(cls));
            }
            return list;
        } finally {
            MyBatisConnection.close(session);
        }
    }

}