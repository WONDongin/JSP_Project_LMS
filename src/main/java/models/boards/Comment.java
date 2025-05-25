package models.boards;

import java.util.Date;
// 이동원
public class Comment {
    private int comm_no;
    private int arti_no;
    private String user_no;
    private String comm_content;
    private Date comm_date;
    private String user_name; // 추가-댓글 작성자명

    public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getComm_no() {
        return comm_no;
    }

    public void setComm_no(int comm_no) {
        this.comm_no = comm_no;
    }

    public int getArti_no() {
        return arti_no;
    }

    public void setArti_no(int arti_no) {
        this.arti_no = arti_no;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getComm_content() {
        return comm_content;
    }

    public void setComm_content(String comm_content) {
        this.comm_content = comm_content;
    }

    public Date getComm_date() {
        return comm_date;
    }

    public void setComm_date(Date comm_date) {
        this.comm_date = comm_date;
    }
}