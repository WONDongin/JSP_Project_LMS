package models.classes;

// 김기흔
// 이동원 수정 - 학년, 이름 추가
//submitted_assignments
public class Sub_as {
	
	private String user_no; // 학번
	private String user_grade; // 학년 - 추가
	private String user_name; // 이름 - 추가
	private int as_no; // 과제 번호
	private String file; // 파일 경로
	private int as_score; // 점수
	
	@Override
	public String toString() {
		return "Sub_as [user_no=" + user_no + ", user_grade=" + user_grade + ", user_name=" + user_name + ", as_no="
				+ as_no + ", file=" + file + ", as_score=" + as_score + "]";
	}
	
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public int getAs_no() {
		return as_no;
	}
	public void setAs_no(int as_no) {
		this.as_no = as_no;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public int getAs_score() {
		return as_score;
	}
	public void setAs_score(int as_score) {
		this.as_score = as_score;
	}
	public String getUser_grade() {
		return user_grade;
	}
	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
