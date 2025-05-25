package models.classes;

public class Student {
	private String user_no;
	private String user_name;
	private int user_grade;
	private int exam1_score; // 중간 점수
	private int exam2_score; // 기말 점수
	private int as_tot_score; // 과제 점수
	private int att_score; // 출석 점수
	
	@Override
	public String toString() {
		return "Student [user_no=" + user_no + ", user_name=" + user_name + ", user_grade=" + user_grade
				+ ", exam1_score=" + exam1_score + ", exam2_score=" + exam2_score + ", as_tot_score=" + as_tot_score
				+ ", att_score=" + att_score + "]";
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public int getExam1_score() {
		return exam1_score;
	}

	public void setExam1_score(int exam1_score) {
		this.exam1_score = exam1_score;
	}

	public int getExam2_score() {
		return exam2_score;
	}

	public void setExam2_score(int exam2_score) {
		this.exam2_score = exam2_score;
	}

	public int getAs_tot_score() {
		return as_tot_score;
	}

	public void setAs_tot_score(int as_tot_score) {
		this.as_tot_score = as_tot_score;
	}

	public int getAtt_score() {
		return att_score;
	}

	public void setAtt_score(int att_score) {
		this.att_score = att_score;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Student)) return false;
	    Student other = (Student) o;
	    return user_no == other.getUser_no();
	}

	@Override
	public int hashCode() {
	    return user_no.hashCode();
	}

	public int getUser_grade() {
		return user_grade;
	}

	public void setUser_grade(int user_grade) {
		this.user_grade = user_grade;
	}
  
}
