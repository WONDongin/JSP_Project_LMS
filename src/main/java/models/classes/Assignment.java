package models.classes;

import java.util.Date;
import java.util.Map;

public class Assignment {
	
	private int as_no;
	private String as_name;
	private String as_content;
	private Date as_s_date;
	private Date as_e_date;
	private int as_point;
	private String class_no;
	private String ban;
	private int year;
	private int term;
	private int submittedCount; // 제출한 학생 수
	
	@Override
	public String toString() {
		return "Assignment [as_no=" + as_no + ", as_name=" + as_name + ", as_content=" + as_content + ", as_s_date="
				+ as_s_date + ", as_e_date=" + as_e_date + ", as_point=" + as_point + ", class_no=" + class_no
				+ ", ban=" + ban + ", year=" + year + ", term=" + term + ", submittedCount="
				+ submittedCount + "]";
	}

	public int getAs_no() {
		return as_no;
	}

	public void setAs_no(int as_no) {
		this.as_no = as_no;
	}



	public String getAs_name() {
		return as_name;
	}



	public void setAs_name(String as_name) {
		this.as_name = as_name;
	}



	public String getAs_content() {
		return as_content;
	}



	public void setAs_content(String as_content) {
		this.as_content = as_content;
	}



	public Date getAs_s_date() {
		return as_s_date;
	}



	public void setAs_s_date(Date as_s_date) {
		this.as_s_date = as_s_date;
	}



	public Date getAs_e_date() {
		return as_e_date;
	}



	public void setAs_e_date(Date as_e_date) {
		this.as_e_date = as_e_date;
	}



	public int getAs_point() {
		return as_point;
	}



	public void setAs_point(int as_point) {
		this.as_point = as_point;
	}



	public String getClass_no() {
		return class_no;
	}



	public void setClass_no(String class_no) {
		this.class_no = class_no;
	}



	public String getBan() {
		return ban;
	}



	public void setBan(String ban) {
		this.ban = ban;
	}



	public int getYear() {
		return year;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public int getTerm() {
		return term;
	}



	public void setTerm(int term) {
		this.term = term;
	}

	public int getSubmittedCount() {
		return submittedCount;
	}

	public void setSubmittedCount(int submittedCount) {
		this.submittedCount = submittedCount;
	}
	
}
