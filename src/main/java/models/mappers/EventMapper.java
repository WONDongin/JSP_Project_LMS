package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import models.others.Event;

public interface EventMapper {

	@Insert("insert into events (event_name, even_s_date, even_e_date) "
			+ "values (#{event_name}, #{even_s_date}, #{even_e_date})")
	int insert(Event event);

	@Update("update events set event_name = #{event_name}, "
			+ " even_s_date = #{even_s_date}, even_e_date = #{even_e_date} "
			+ " where event_no = #{event_no}")
	int update(Event event);

	@Delete("delete from events where event_no = #{event_no}")
	int delete(int event_no);

	@Select("select * from events order by event_no desc")
	List<Event> eventList();
}
