package controllers;

import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.users.User;
import models.users.UserDao;


import models.others.Event;
import models.others.EventDao;
import models.others.Notification;
import models.classes.AsDao;
import models.classes.Assignment;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.others.NotificationDao;

@WebServlet(urlPatterns = { "/mainLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class MainLMSController extends MskimRequestMapping {
	private UserController uc = new UserController();
	private EventDao eventDao = new EventDao();
	private Class1Dao clsdao = new Class1Dao();
	private AsDao asDao = new AsDao();
  
	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		String loginCheck = uc.loginIdCheck(request, response); 
		if(loginCheck != null) { return loginCheck; } // 로그인 확인
		

		// 메인_강의목록(학생/교수)_원동인
		User login = (User) request.getSession().getAttribute("login");
	    Calendar cal = Calendar.getInstance();
	    int calYear = cal.get(Calendar.YEAR);
	    int calMonth = cal.get(Calendar.MONTH) + 1;
	    int term = (calMonth >= 3 && calMonth <= 8) ? 1 : 2;
	    Class1 cls = new Class1();
	    cls.setUser_no(login.getUser_no());
	    cls.setYear(calYear);
	    cls.setTerm(term);
	    List<Class1> classesList_main_s = clsdao.nowClassList(cls);
	    request.setAttribute("classesList_main_s", classesList_main_s);
	    String userNo = login.getUser_no();
	    List<Class1> classesList_main_p = clsdao.selectByProfessor(userNo);
	    request.setAttribute("classesList_main_p", classesList_main_p);    
	
	    // 메인_과제목록 (메인_내 수강강의 과제)
	    asDao = new AsDao();
	    Map<String, List<Assignment>> assignmentMap_main = new HashMap<>();
	    for (Class1 c : classesList_main_s) {
	        String classNo = c.getClass_no();
	        List<Assignment> assignments_main = asDao.list(c);
	        if (assignments_main != null) {
	            // null 요소 제거
	            assignments_main.removeIf(Objects::isNull);

	            // 비어있지 않은 경우만 Map에 추가
	            if (!assignments_main.isEmpty()) {
	                assignmentMap_main.put(classNo, assignments_main);
	            }
	        }
	    }
	    List<Assignment> assignmentMap_prof = asDao.selectAssignmentsByProf(userNo);
	    
	    request.setAttribute("assignmentMap_prof", assignmentMap_prof);
	    request.setAttribute("assignmentMap_main", assignmentMap_main);
	    
		// 캘린더
		String yearStr = request.getParameter("year");
		String monthStr = request.getParameter("month");
		int year, month;
		if (yearStr != null && monthStr != null) {
		    year = Integer.parseInt(yearStr);
		    month = Integer.parseInt(monthStr);
		} else {
		    LocalDate now = LocalDate.now();
		    year = now.getYear();
		    month = now.getMonthValue();
		}
		LocalDate firstDay = LocalDate.of(year, month, 1);
		int startDayOfWeek = firstDay.getDayOfWeek().getValue() % 7;
		int lastDate = firstDay.lengthOfMonth();

		List<Event> event_main = eventDao.eventList();

		// 달력 셀 생성 (6주:42칸)
		List<Map<String, Object>> calendarCells = new ArrayList<>();
		for (int i = 0; i < 42; i++) {
		    Map<String, Object> cell = new HashMap<>();
		    int dateNum = i - startDayOfWeek + 1;

		    if (dateNum > 0 && dateNum <= lastDate) {
		        LocalDate currentDate = LocalDate.of(year, month, dateNum);
		        cell.put("date", dateNum);
		        cell.put("fullDate", currentDate.toString());
		        // 해당 날짜에 포함되는 이벤트 필터링
		        List<Event> dayEvents = new ArrayList<>();
		        for (Event event : event_main) {
		            LocalDate start = event.getEven_s_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		            LocalDate end = event.getEven_e_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		            if (!currentDate.isBefore(start) && !currentDate.isAfter(end)) {
		                dayEvents.add(event);
		            }
		        }
		        cell.put("events", dayEvents);
		    } else {
		        cell.put("date", null); // 빈칸
		    }
		    calendarCells.add(cell);
		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("calendarCells", calendarCells);
		
		return null; // 정상인 경우
  }

	// 사용자 관리 폼(관리자 외 접근권한 없음)=================================================
	@RequestMapping("adminForm")
	public String adminForm(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		UserDao dao = new UserDao();
		List<User> users = dao.selectAll();
		request.setAttribute("users", users);
		return null;
	}

	// 사용자 추가 폼 =================================================================
	@RequestMapping("addUserForm")
	public String addUserForm(HttpServletRequest request, HttpServletResponse response) {
		return "mainLMS/addUserForm";
	}

	//사용자 추가 ====================================================================
	@RequestMapping("adduser")
	public String addUser(HttpServletRequest request, HttpServletResponse response) {
		UserDao dao = new UserDao();
		User user = new User();
		user.setUser_no(request.getParameter("user_no"));
		user.setUser_name(request.getParameter("user_name"));
		user.setPassword(request.getParameter("password"));
		user.setRole(request.getParameter("role"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		user.setMajor_no(request.getParameter("major_no"));
		String grade = request.getParameter("user_grade");
		user.setUser_grade(grade != null && !grade.isEmpty() ? Integer.parseInt(grade) : 0);
		user.setEmail(request.getParameter("email"));
		user.setTel(request.getParameter("tel"));
		if (dao.insert(user)) {
			request.setAttribute("msg", user.getUser_name() + "님을 사용자로 추가하였습니다.");
			request.setAttribute("url", "adminForm");
		} else {
			request.setAttribute("msg", "오류 발생");
			request.setAttribute("url", "addUserForm");
		}
		return "alert";
	}

	// 사용자 수정 폼 =====================================================
	@RequestMapping("updateUserForm")
	public String updateUserForm(HttpServletRequest request, HttpServletResponse response) {
		UserDao dao = new UserDao();
		String user_no = request.getParameter("user_no");
		User user = dao.selectOne(user_no);
		request.setAttribute("user", user);
		return "mainLMS/updateUserForm";
	}

	// 사용자 정보 수정(관리자권한)============================================
	@RequestMapping("updateuser")
	public String updateuser(HttpServletRequest request, HttpServletResponse response) {
		UserDao dao = new UserDao();
		User user = new User();
		user.setUser_no(request.getParameter("user_no"));
		user.setUser_name(request.getParameter("user_name"));
		user.setGender(Integer.parseInt(request.getParameter("gender")));
		user.setTel(request.getParameter("tel"));
		user.setEmail(request.getParameter("email"));
		user.setUser_grade(Integer.parseInt(request.getParameter("user_grade")));
		user.setMajor_no(request.getParameter("major_no"));
		user.setPassword(request.getParameter("password"));
		// 비밀번호를 위한 db의 데이터 조회. : login 정보로 조회하기
		User login = (User) request.getSession().getAttribute("login");
		String msg = "비밀번호가 틀립니다.";
		String url = "users/updateForm";
		// 비밀번호 같을시
		if (user.getPassword().equals(login.getPassword())) {
			if (dao.update(user)) {
				msg = "회원정보 수정완료";
				url = "adminForm";
			} else {
				msg = "회원정보 수정실패";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}

	//사용자 삭제폼(관리자 권한) ========================================================
	@RequestMapping("deleteUserForm")
	public String deleteUserForm(HttpServletRequest request, HttpServletResponse response) {
		UserDao dao = new UserDao();
		String user_no = request.getParameter("user_no");
		User user = dao.selectOne(user_no);
		request.setAttribute("user", user);
		return "mainLMS/deleteUserForm";
	}
	//사용자 삭제(관리자 권한) ========================================================
	@RequestMapping("deleteuser")
	public String deleteuser(HttpServletRequest request, HttpServletResponse response) {
		UserDao dao = new UserDao();
		String user_no = request.getParameter("user_no");
		String password = request.getParameter("password");
		User login = (User) request.getSession().getAttribute("login");
		String msg = "비밀번호가 맞지 않습니다.";
		String url = "deleteUserForm";
		if (password != null && login.getPassword().equals(password)) {
			boolean result = dao.deleteuser(user_no);
			if (result) {
				msg = "사용자 삭제 완료";
				url = "adminForm";
			} else {
				msg = "사용자 삭제 실패";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}

	// 사용자 검색 ========================================
	@RequestMapping("searchusers")
	public String searchusers(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("type"); // user_name, user_no, email
		String keyword = request.getParameter("keyword"); // 검색어
		if (type.equals("role")) {
			if (keyword.equals("학생")) {
				keyword = "1";
			}
			if (keyword.equals("교수")) {
				keyword = "2";
			}
		}
		UserDao dao = new UserDao();
		if (type == null || type.isEmpty()) {
			request.setAttribute("msg", "검색 기준을 선택해주세요");
			request.setAttribute("url", "adminForm");
			return "alert";
		}
		if (keyword == null || keyword.isEmpty()) {
			request.setAttribute("msg", "검색어를 입력해주세요");
			request.setAttribute("url", "adminForm");
			return "alert";
		}

		List<User> users = dao.searchUsers(type, keyword);
		if (users == null || users.isEmpty()) {
			request.setAttribute("msg", "검색 결과가 없습니다.");
			request.setAttribute("url", "adminForm");
			return "alert";
		}
		request.setAttribute("users", users);
		return "mainLMS/adminForm";
	}

	// 원동인 학사일정(관리자 권한)
	@RequestMapping("event")
	public String event(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		// 학사일정 알림 대상자 조회(학생/교수)
		UserDao userDao = new UserDao();
		List<String> eventUserList = userDao.getUserNosByRole(); // role이 1 또는 2인 사용자 목록

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 삭제 처리
		String deleteNo = request.getParameter("delete");
		if (deleteNo != null) {
		    int no = Integer.parseInt(deleteNo);
		    if (eventDao.delete(no)) {
		        request.setAttribute("msg", "삭제 완료");
		        request.setAttribute("url", "event");
		    } else {
		        request.setAttribute("msg", "삭제 실패");
		        request.setAttribute("url", "event");
		    }
		    return "alert";
		}

		// 파라미터 수신
		String event_name = request.getParameter("event_name");
		String s_date = request.getParameter("even_s_date");
		String e_date = request.getParameter("even_e_date");

		if (event_name != null && s_date != null && e_date != null) {
		    Event event = new Event();
		    event.setEvent_name(event_name);
		    event.setEven_s_date(formatter.parse(s_date + " 00:00:00"));
		    event.setEven_e_date(formatter.parse(e_date + " 23:59:59"));

		    String noStr = request.getParameter("event_no");
		    boolean result;

		    NotificationDao notifDao = new NotificationDao();
		    // 수정 또는 등록
		    if (noStr != null && !noStr.equals("")) {
		        event.setEvent_no(Integer.parseInt(noStr));
		        result = eventDao.update(event);
		    } else {
		        result = eventDao.insert(event);		        
		    }

		    if (result) {
		        // 알림 테이블에 insert 처리
		        for (String user_no : eventUserList) {
		            Notification notif = new Notification();
		            notif.setUser_no(user_no);
		            notif.setNotif_content(( "["+ event_name + "] 학사일정이 등록 되었습니다."));
		            notif.setNotif_date(new Date());
		            notifDao.notificationInsert(notif); 
		        }

		        request.setAttribute("msg", "등록 성공");
		    } else {
		        request.setAttribute("msg", "등록 실패");
		    }
		    return "alert";
		}
		// 리스트 조회
		request.setAttribute("eventList", eventDao.eventList());
		return "mainLMS/event";
	}

	// 수강신청
	// 오예록
	@RequestMapping("signUpClass")
	public String signUpClass(HttpServletRequest request, HttpServletResponse response) {
		Class1 cls = new Class1();
		Calendar cal = Calendar.getInstance();
		User login = (User) request.getSession().getAttribute("login");
		String type = request.getParameter("type");
		String fine = request.getParameter("fine");
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int term = (month >= 3 && month <= 8) ? 1 : 2;
		cls.setYear(year);
		cls.setTerm(term);
		
		List<Class1> classesList = clsdao.selectClassesByYearTerm(cls, login.getUser_no(), type, fine);
		request.setAttribute("classesList", classesList);
		return "mainLMS/signUpClass";
	}
	@RequestMapping("signUpCls")
	public String signUpCls(HttpServletRequest request, HttpServletResponse response) {
		Class1 key = new Class1();
		key.setUser_no(request.getParameter("user_no"));
	    key.setClass_no(request.getParameter("cls_no"));
	    key.setMax_p(Integer.parseInt(request.getParameter("max_p")));
	    key.setBan(request.getParameter("ban"));
	    key.setYear(Integer.parseInt(request.getParameter("year")));
	    key.setTerm(Integer.parseInt(request.getParameter("term")));
	    if (clsdao.enrolledCount(key) >= key.getMax_p()) {
	    	request.setAttribute("msg", "정원 초과");
	    	request.setAttribute("url", "signUpClass");
		    return "alert";
	    }
	    if (clsdao.countRegistered(key) >= 6) {
	    	request.setAttribute("msg", "6개 이상 신청이 불가합니다.");
	    	request.setAttribute("url", "signUpClass");
		    return "alert";
	    }
		if (clsdao.insertRegisteredClass(key)) {
	        request.setAttribute("msg", "수강 신청을 성공 했습니다.");
	    } else {
	        request.setAttribute("msg", "수강 신청을 실패했습니다.");
	    }
	    request.setAttribute("url", "signUpClass");
	    return "alert";
	}
	
	// 학점 조회
	 @RequestMapping("markList")
	    public String markList(HttpServletRequest request, HttpServletResponse response) {
	        String loginCheck = uc.loginIdCheck(request, response);
	        if (loginCheck != null) return loginCheck;
	        User login = (User) request.getSession().getAttribute("login");

	        String type = request.getParameter("type");
	        String fine = request.getParameter("fine");

	        List<Class1> classesList = clsdao.selectGradesByUserFilter(login.getUser_no(), type, fine);
	        request.setAttribute("classesList", classesList);

	        return "mainLMS/markList";
	    }
}