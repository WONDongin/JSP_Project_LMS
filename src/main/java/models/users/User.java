package models.users;

// 사용자 정보 테이블(Users)의 Bean Class

public class User {
	private String user_no;
	private String password;
	private String role;
	private String email;
	private String tel;
	private String user_name;
	private int gender;
	private int user_grade;
	private String major_no;
	
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getUser_grade() {
		return user_grade;
	}
	public void setUser_grade(int user_grade) {
		this.user_grade = user_grade;
	}
	public String getMajor_no() {
		return major_no;
	}
	public void setMajor_no(String major_no) {
		this.major_no = major_no;
	}
	@Override
	public String toString() {
		return "Users [user_no=" + user_no + ", password=" + password + ", role=" + role + ", email=" + email + ", tel="
				+ tel + ", name=" + user_name + ", gender=" + gender + ", user_grade=" + user_grade + ", major_no=" + major_no + "]";
	}
	
	


}
