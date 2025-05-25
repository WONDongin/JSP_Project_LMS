package models.others;

import java.util.Date;

// 원동인
public class Event {
    private int event_no;  // 행사번호
    private String event_name; // 행사명
    private Date even_s_date; // 사작일
    private Date even_e_date; // 종료일

    
	public int getEvent_no() {
		return event_no;
	}
	public void setEvent_no(int event_no) {
		this.event_no = event_no;
	}
	public String getEvent_name() {
		return event_name;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public Date getEven_s_date() {
		return even_s_date;
	}
	public void setEven_s_date(Date even_s_date) {
		this.even_s_date = even_s_date;
	}
	public Date getEven_e_date() {
		return even_e_date;
	}
	public void setEven_e_date(Date even_e_date) {
		this.even_e_date = even_e_date;
	}

	
}
