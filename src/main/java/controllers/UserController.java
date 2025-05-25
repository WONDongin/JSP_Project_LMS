package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.classes.Reg_classDao;
import models.classes.Student;
import models.others.Major;
import models.others.MajorDao;
import models.others.Notification;
import models.others.NotificationDao;
import models.users.User;
import models.users.UserDao;
/*
	controllers/UserController.java - 김기흔
*/
@WebServlet(urlPatterns = {"/users/*"},
initParams = {@WebInitParam(name="view",value="/views/")})
public class UserController extends MskimRequestMapping{
	private UserDao dao = new UserDao();
	private MajorDao majorDao = new MajorDao();
	private Reg_classDao rcDao = new Reg_classDao();
	private NotificationDao NotificationDao = new NotificationDao();
	/*
	 	Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
	 
		C 상단에 꼭 선언해야 함.
		private UserController uc = new UserController();
		
		필요한 것에 따라 2줄 모두 메서드 내부 위에 작성.

		String loginCheck = uc.loginIdCheck(request, response); 
		if(loginCheck != null) { return loginCheck; } // 로그인 확인

		String adminCheck = uc.adminCheck(request, response);
		if(adminCheck != null) { return adminCheck; } // 관리자 확인

		String studentCheck = uc.studentCheck(request, response);
		if(studentCheck != null) { return studentCheck; } // 학생 확인
		
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인

		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인

		String user_noCheck = uc.user_noCheck(request, response);
		if(user_noCheck != null) { return user_noCheck; } // 유저번호 확인

		String majorCheck = uc.majorCheck(request, response);
		if(majorCheck != null) { return majorCheck; } // 소속학과 확인
	
	*/
	
	// 로그인 =================================================================
	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse response) {			
		// 파라미터 조회
		String user_no = request.getParameter("user_no");
		String password = request.getParameter("password");
		User user = dao.selectOne(user_no);
		String msg = null;
		String url = null;
		
		if(user == null){ // 아이디가 없는 경우
			msg = "아이디를 확인하세요";
			url = "loginForm";
		} else if (!password.equals(user.getPassword())) {
			msg = "비밀번호를 확인하세요";
			url = "loginForm";
		} else { 
			request.getSession().setAttribute("login", user);
			request.getSession().setAttribute("class1", null);
			msg = user.getUser_name() + "님 반갑습니다.";
			url = "../mainLMS/main";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}		
	// 아이디 찾기=========================================================================
	@RequestMapping("id")
	public String id(HttpServletRequest request, HttpServletResponse response) {
		String user_name = request.getParameter("user_name");
		String email = request.getParameter("email");
			
		String user_no = dao.userSearch(user_name, email);

		  if (user_no == null) {
		        request.setAttribute("msg", "아이디가 없습니다.");
		        request.setAttribute("url", "idForm");
		        System.out.println("null");
		        return "alert";
		    } else if ("999".equals(user_no)) {
		        request.setAttribute("msg", "조회할 수 없는 계정입니다.");
		        request.setAttribute("url", "idForm");
		        System.out.println("admin");
		        return "alert";
		    } else {
		        request.setAttribute("user_no", user_no);
		        System.out.println("not null");
		        return "users/id";
		    }
	}
	// 비밀번호 초기화 =================================================================
	@RequestMapping("resetpw")
	public String resetpw(HttpServletRequest request, HttpServletResponse response) {
		String user_no = request.getParameter("user_no");
		String email = request.getParameter("email");
		String randpw = null;
			
		//존재하는 계정인지 확인
		String isUser_no = dao.isUser(user_no);	
			
		if(isUser_no != null && isUser_no.equals(user_no)) {
			Random rand = new Random();
			int x = 100000 + rand.nextInt(900000);
			randpw = String.valueOf(x);
			int pass = dao.pwReset(user_no, randpw);
			if(pass == 1) {
				request.setAttribute("msg", "임시 비밀번호는"+ randpw+"입니다. 비밀번호를 복사해주세요.");
				request.setAttribute("close", true);
				return "alert";
			} else {
				request.setAttribute("msg", "오류발생");
				return "users/resetPwForm";
			}		
		}
		request.setAttribute("msg", "비밀번호를 찾을 수 없습니다.");
		request.setAttribute("url", "resetPwForm");
		return "alert";
	}
	
	
	// 로그아웃 =================================================================
	@RequestMapping("logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		request.setAttribute("msg", "로그아웃되었습니다");
		request.setAttribute("url", "loginForm");
		return "alert";
	}
	
	// 로그인 체크 =================================================================
	public String loginIdCheck(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		if(login == null) {
			request.getSession().setAttribute("login", login);
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("url", "../users/loginForm");
			return "alert";
		}
		return null; // 정상인 경우
	}
	// 로그인 아이디 체크(팝업창) =================================================================
	public String loginIdCheckPopup(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		if(login == null) {
			request.setAttribute("msg", "로그인 하세요");
			request.setAttribute("close", true);
			return "alert";
		}
		return null; // 정상인 경우
	}
	// 이동원
	// 관리자 체크 ===================================
	public String adminCheck(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		String loginCheck = loginIdCheck(request, response); 
		if(loginCheck != null) { return loginCheck; }
		if(login.getRole().equals("3")) {
			return null;
		}
		request.setAttribute("msg", "관리자가 아닙니다.");
		request.setAttribute("url", "../mainLMS/main");
		return "alert";
	}
	// 오예록
	// 학생 체크 ===================================
	public String studentCheck(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		String loginCheck = loginIdCheck(request, response); 
		if(loginCheck != null) { return loginCheck; }
		if(login.getRole().equals("1")) {
			return null;
		}
		request.setAttribute("msg", "학생이 아닙니다.");
		request.setAttribute("url", "../mainLMS/main");
		return "alert";
	}
	// 이동원
	// 교수 체크 ===================================
	public String profCheck(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		String loginCheck = loginIdCheck(request, response); 
		if(loginCheck != null) { return loginCheck; }
		if(login.getRole().equals("2")) {
			return null;
		}
		request.setAttribute("msg", "교수가 아닙니다.");
		request.setAttribute("url", "../mainLMS/main");
		return "alert";
	}
	// 이동원 
	// 유저번호 체크 ===================================
	public String user_noCheck(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		String target = request.getParameter("user_no");
		if(target == null || login.getRole().equals("3")
				|| login.getUser_no().equals(target)) {
			// 관리자 or 내 정보일 경우(정상접근)
			return null;
		}
		request.setAttribute("msg", "자신의 정보만 볼 수 있습니다.");
		request.setAttribute("url", "../mainLMS/main");
		return "alert";
	}
	// 이동원
	// 소속학과 체크 ===================================
	public String majorCheck(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		String target = request.getParameter("major_no");
		if(login.getRole().equals("3")
				|| login.getMajor_no().equals(target)) {
			// 관리자 or 내가 속한 학과일 경우(정상접근)
			System.out.println(login.getMajor_no());
			System.out.println(target);
			return null;
		}
		request.setAttribute("msg", "자신이 속한 학과가 아닙니다.");
		request.setAttribute("url", "../board/board?board_id"+login.getMajor_no());
		return "alert";
	}
	// 이동원
	// 강의 체크 ===================================
	public String classCheck(HttpServletRequest request, HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		Class1 target = (Class1)request.getSession().getAttribute("class1");
		if(login.getRole().equals("3")) { // login이 관리자 일 때
			return null;
		} else if(login.getRole().equals("2")) { // login이 교수일 경우
			if(login.getUser_no().equals(target.getUser_no())) { // 자신의 강의일 경우
				return null; }
			request.setAttribute("msg", "자신이 진행하는 강의가 아닙니다.");
		} else if(login.getRole().equals("1")) { // login이 학생일 경우
			if(rcDao.checkStudent(target, login.getUser_no())) { // 소속학생일 경우
				return null; }
			request.setAttribute("msg", "수강 중인 강의가 아닙니다.");	
		}
		request.setAttribute("url", "../board/board?board_id"+login.getMajor_no());
		return "alert";
	}
	/* 회원정보(DB 에서 가져오기) =================================================================*/
	@RequestMapping("info")
	@MSLogin("loginIdCheckPopup")
	public String info(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		User user = dao.selectOne(login.getUser_no());
		request.setAttribute("user", user);
		return "users/info";
	}
	// 회원정보 수정 페이지 ====================================================================
		@RequestMapping("updateForm")
		@MSLogin("loginIdCheckPopup") 
		public String updateForm(HttpServletRequest request, HttpServletResponse response) {
			return "users/updateForm";
		}		
	
		/* 회원정보 수정 =================================================================   */
		@RequestMapping("update")
		@MSLogin("loginIdCheckPopup")
		public String update(HttpServletRequest request, HttpServletResponse response) {		
			User user = new User();
			user.setUser_no(request.getParameter("user_no"));
			user.setUser_name(request.getParameter("user_name"));
			user.setGender(Integer.parseInt(request.getParameter("gender")));
			user.setTel(request.getParameter("tel"));
			user.setEmail(request.getParameter("email"));
			user.setUser_grade(Integer.parseInt(request.getParameter("grade")));
			user.setPassword(request.getParameter("password"));
			// 비밀번호를 위한 db의 데이터 조회. : login 정보로 조회하기
			User login = (User)request.getSession().getAttribute("login");	
			String msg = "비밀번호가 틀립니다.";
			String url = "users/updateForm";
			// 비밀번호 같을시
			if(user.getPassword().equals(login.getPassword())) {
				if(dao.update(user)) {
					msg = "회원정보 수정완료";
					url = "info";
				} else {
					msg = "회원정보 수정실패";
				}
			}
			
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);
			return "alert";
		}
		
		// 비밀번호 재설정 창 =================================================================
		@RequestMapping("pwForm")
		@MSLogin("loginIdCheckPopup")
		public String pwForm(HttpServletRequest request, HttpServletResponse response) {
			User login = (User) request.getSession().getAttribute("login");
			return "users/pwForm";
		}
	    //비밀번호 재설정 ====================================================================	
		@RequestMapping("updatepw")
		public String updatepw(HttpServletRequest request, HttpServletResponse response) {
			String password = request.getParameter("password");
			String n_pass1 = request.getParameter("new_password1");
			String n_pass2 = request.getParameter("new_password2");
			User login = (User) request.getSession().getAttribute("login");		
			
			if(password.equals(login.getPassword())){
		    	 if(!n_pass1.equals("") && !n_pass2.equals("") && n_pass1.equals(n_pass2)) {
		    		if(dao.updatePass(login.getUser_no(), n_pass1)) { // 비밀번호 수정성공
		    			request.setAttribute("msg", "비밀번호 수정성공.");
		    			request.setAttribute("url", "info");
		    			return "alert"; 
		    		} else { // 비밀번호 수정실패
		    			StringBuilder sb = new StringBuilder();
		    			sb.append("alert('비밀번호 수정시 오류가 발생했습니다.');\n");
		    			sb.append("self.close();");
		    			request.setAttribute("script", sb.toString());
		    			return "dummy"; // dummy.jsp 생성
		    		}
		    	} else if (n_pass1.equals("") || n_pass1.equals("")) {
		    		request.setAttribute("msg", "새로운 비밀번호를 입력하세요");
		    		request.setAttribute("url", "pwForm");
		    		return "alert";
		    	}
		    	 else { // 비밀번호 오류
		    		request.setAttribute("msg", "새로운 비밀번호들이 일치하지 않습니다.");
		    		request.setAttribute("url", "pwForm");
		    		return "alert";
		    	} 
		    } else {
				request.setAttribute("msg", "비밀번호가 틀립니다.");
				request.setAttribute("url", "pwForm");
				return "alert";
		    }
		}
		
		// 원동인 (알림)
		@RequestMapping("notificationForm")
		public String notification(HttpServletRequest request, HttpServletResponse response) throws ParseException {
			User login = (User) request.getSession().getAttribute("login");
			System.out.println(login.getUser_no());
		    List<Notification> notificationsList = NotificationDao.getNotificationsByUser(login.getUser_no());
		    request.setAttribute("notificationsList", notificationsList);
		    String deleteNo = request.getParameter("delete");
			if (deleteNo != null) {
			    int no = Integer.parseInt(deleteNo);
			    if (NotificationDao.delete(no)) {
			        request.setAttribute("msg", "삭제 완료");
			    } else {
			        request.setAttribute("msg", "삭제 실패");
			    }
			    return "alert";
			}
			return "users/notificationForm";
		}
}
