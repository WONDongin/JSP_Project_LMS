package models.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import models.others.Notification;

public interface NotificationMapper {

	// 학사일정 내정보관련 리스트
	@Select("select * from notifications "
			+ " where user_no = #{user_no} "
			+ " order by is_pinned DESC, notif_date DESC")
	List<Notification> getNotificationsByUser(String user_no);
	// 학사일정 전체발송
	@Insert("insert into notifications (user_no, notif_content, notif_date) "
			+ " values (#{user_no}, #{notif_content},  NOW()) ")
	Object notificationInsert(Notification notif);
	// 학사일정 삭제
	@Delete("delete from notifications where notif_no = #{notif_no}")
	int delete(int no);
}
