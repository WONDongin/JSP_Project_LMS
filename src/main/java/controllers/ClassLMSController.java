package controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.AsDao;
import models.classes.Assignment;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.classes.Reg_classDao;
import models.classes.Student;
import models.classes.SubAsDao;
import models.classes.Sub_as;
import models.users.User;
import models.users.UserDao;

@WebServlet(urlPatterns = { "/classLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class ClassLMSController extends MskimRequestMapping {
	private UserController uc = new UserController();
	private Class1Dao class1Dao = new Class1Dao();
	private AsDao asDao = new AsDao();
	private Reg_classDao rcDao = new Reg_classDao();
	private SubAsDao subAsDao = new SubAsDao();	
	
	// 세션의 class1 값 체크. 없다면 강의선택페이지로 이동 ===
	public String chkClass1(HttpServletRequest request) {
		if(request.getSession().getAttribute("class1") == null) {
			request.setAttribute("msg", "강의를 선택하세요.");
			request.setAttribute("url", "../deptLMS/classList");
			return "alert"; // 없음
		}
		return null; // 있음.
	}
	
	/*
	 	세션 체크
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
	
	 */
	
	
	// (dwChecked)세션에 클래스 넣기
	private String putClass1Session(HttpServletRequest request, Class1 class1) {
		if(request.getParameter("class_no") != null && 
				!request.getParameter("class_no").trim().equals("")) {
	        class1 = new Class1(request.getParameter("class_no"),
	              request.getParameter("ban"),
	              Integer.parseInt(request.getParameter("year")),
	              Integer.parseInt(request.getParameter("term")));
		} else { 
			class1 = (Class1)request.getSession().getAttribute("class1"); 
		}
		class1 = class1Dao.selectOne(class1); // class1 객체 db에서 지정
        if(class1 == null) {
           request.setAttribute("msg", "class1 세션에 저장 실패");
           request.setAttribute("url", "mainLMS/main");
           return "alert";
        }
        // 세션에 교수정보(원동인)
		String prof = class1Dao.selectProf(class1);
	    class1.setProf(prof);
        request.getSession().setAttribute("class1", class1); // session 속성으로 class1 지정
        return null;
     }

	
	// (dwChecked)강의 계획서===================== 
	@RequestMapping("classInfo")
	public String classInfo(HttpServletRequest request, HttpServletResponse response) {
		String loginCheck = uc.loginIdCheck(request, response);
		if(loginCheck != null) { return loginCheck; } // 로그인 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
    	class1 = (Class1) request.getSession().getAttribute("class1");
    	if(class1.getFile() != null) {
    	// 확장자 추출
	    	String ext = class1.getFile().substring(class1.getFile().lastIndexOf("."));
	    	request.setAttribute("ext", ext);
    	}
		return "classLMS/classInfo";
	}
	
	// 과제관리 접근권한 설정================================================
	@RequestMapping("manageAs")
	public String manageAs(HttpServletRequest request, HttpServletResponse response) {
		String loginCheck = uc.loginIdCheck(request, response);
		if(loginCheck != null) { return loginCheck; } // 로그인 확인
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		String hasClass = chkClass1(request);
		if(hasClass != null) { return hasClass; } // class1 확인
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
	
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");
		class1.setNow_p(class1Dao.enrolledCount(class1));

		List<Assignment> asList = asDao.list(class1);
		for(Assignment tmp : asList) {
			tmp.setSubmittedCount(subAsDao.count(tmp));
		}
		List<Student> stList = rcDao.studentList(class1);
		
		if(request.getParameter("as_no") != null) {
			int as_no = Integer.parseInt(request.getParameter("as_no"));
			String asName = asDao.selectOne(as_no).getAs_name();
			List<Sub_as> subAsList = new ArrayList<Sub_as>();
			
			for(Student st : stList) { // a : string 형태의 수강생 user_no
				if(subAsDao.selectOne(st.getUser_no(), as_no) == null) {
					Sub_as tmpAs = new Sub_as();
					tmpAs.setUser_no(st.getUser_no());
					tmpAs.setUser_grade(st.getUser_grade()+"");
					tmpAs.setUser_name(st.getUser_name());
					tmpAs.setAs_score(0);
					subAsList.add(tmpAs);
				} else { subAsList.add(subAsDao.selectOne(st.getUser_no(), as_no)); }
				
				request.setAttribute("as_no", as_no);
				request.setAttribute("subAsList", subAsList);
			}
			request.setAttribute("asName", asName);
		}
		request.setAttribute("asList", asList);

		return "classLMS/manageAs"; // 정상인 경우
	}
	// (dwChecked)과제 추가 접근권한 설정============================================================
	@RequestMapping("addAssignmentForm")
	public String addAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
		return null; // 정상인 경우
	}
	
	// (dwChecked)과제 추가============================================================
	@RequestMapping("addAssignment")
	public String addAssignment(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		Class1 class1 = (Class1)request.getSession().getAttribute("class1");
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
		
		Assignment as = new Assignment();
	    as.setAs_name(request.getParameter("as_name"));
	    as.setAs_content(request.getParameter("as_content"));
	    
	    String as_s_date_str = request.getParameter("as_s_date");
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    LocalDateTime localDateTime = LocalDateTime.parse(as_s_date_str, formatter);
	    ZoneId zoneId = ZoneId.systemDefault(); // 서버 시스템 타임존
	    Date as_s_date = Date.from(localDateTime.atZone(zoneId).toInstant());
	    as.setAs_s_date(as_s_date); // 과제 시작일
	    
	    String as_e_date_str = request.getParameter("as_e_date");
	    localDateTime = LocalDateTime.parse(as_e_date_str, formatter);
	    Date as_e_date = Date.from(localDateTime.atZone(zoneId).toInstant());
	    as.setAs_e_date(as_e_date); //과제 마감일
	    
	    as.setAs_point(Integer.parseInt(request.getParameter("as_point")));
	    // 과제 배점
	    as.setClass_no(class1.getClass_no());
	    as.setBan(class1.getBan());
	    as.setYear(class1.getYear());
	    as.setTerm(class1.getTerm());
		if(asDao.insert(as)) {
			request.setAttribute("msg","과제를 추가하였습니다.");
			request.setAttribute("url", "manageAs");
		} else {
			request.setAttribute("msg", "오류 발생");
			request.setAttribute("url", "addAssignmentForm");
		}
		return "alert";				
	}
	
	// (DwCheck)과제 수정 권한 업데이트 및 요청 객체 전달============================================================
	@RequestMapping("updateAssignmentForm")
	public String udpateAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
		int as_no = Integer.parseInt(request.getParameter("as_no"));
		
		Assignment as = asDao.selectOne(as_no);
		//시작날짜, 끝나는 날짜는 따로 저장
		LocalDateTime as_s_date = as.getAs_s_date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
		LocalDateTime as_e_date = as.getAs_e_date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
		request.setAttribute("as_s_date", as_s_date);
		request.setAttribute("as_e_date", as_e_date);
		request.setAttribute("as", as);
		return null; // 정상인 경우
	}
	
	// (dwCheck)과제 수정 =========================================================
	@RequestMapping("updateAssignment")
	public String updateuser(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
		
		Assignment as = new Assignment();
		as.setAs_no(Integer.parseInt(request.getParameter("as_no")));
	    as.setAs_name(request.getParameter("as_name"));
	    as.setAs_content(request.getParameter("as_content"));
	    
	    String as_s_date_str = request.getParameter("as_s_date");
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    LocalDateTime localDateTime = LocalDateTime.parse(as_s_date_str, formatter);
	    ZoneId zoneId = ZoneId.systemDefault(); // 서버 시스템 타임존
	    Date as_s_date = Date.from(localDateTime.atZone(zoneId).toInstant());
	    as.setAs_s_date(as_s_date);
	    
	    String as_e_date_str = request.getParameter("as_e_date");
	    localDateTime = LocalDateTime.parse(as_e_date_str, formatter);
	    Date as_e_date = Date.from(localDateTime.atZone(zoneId).toInstant());
	    as.setAs_e_date(as_e_date);
	    
	    as.setAs_point(Integer.parseInt(request.getParameter("as_point")));
	    as.setClass_no(class1.getClass_no());
	    as.setBan(class1.getBan());
	    as.setYear(class1.getYear());
	    as.setTerm(class1.getTerm());
		if(asDao.update(as)) {
			request.setAttribute("msg","과제를 수정하였습니다.");
			request.setAttribute("url", "manageAs");
		} else {
			request.setAttribute("msg", "오류 발생");
			request.setAttribute("url", "updateAssignmentForm");
		}
		request.setAttribute("as_no", as.getAs_no());
		return "alert";		
	}
	// (dwCheck)과제 삭제폼 =====================================================
	@RequestMapping("deleteAssignmentForm")
	public String deleteAssignmentForm(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
		
		int as_no = Integer.parseInt(request.getParameter("as_no"));
		Assignment as = asDao.selectOne(as_no);
		request.setAttribute("as", as);
		return null; // 정상인 경우
	}
	
	// (dwCheck)과제 삭제===========================================================
	@RequestMapping("deleteAssignment")
	public String deleteAssignment(HttpServletRequest request , HttpServletResponse response) {
		User login = (User)request.getSession().getAttribute("login");
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		String classCheck = uc.classCheck(request, response);
		if(classCheck != null) { return classCheck; } // 강의 확인
		
	    String password = request.getParameter("password");
	    int as_no = Integer.parseInt(request.getParameter("as_no"));
	    String msg = "비밀번호가 맞지 않습니다.";
	    String url = "deleteAssignmentForm?as_no=" + as_no;
	    if (password != null && login.getPassword().equals(password)) {
	        List<String> r_stuList = asDao.selectReg_Std(class1.getClass_no());
	        for(String a : r_stuList) {
	           subAsDao.deleteStd_list(a, as_no);
	        }
	        boolean result = asDao.deleteAssignment(as_no);
	        if (result) {
	            msg = "과제를 삭제하였습니다.";
	            url = "manageAs";
	        } else {
	            msg = "과제 삭제 실패";
	        }
	    }
	    request.setAttribute("msg", msg);
	    request.setAttribute("url", url);
	    return "alert";
	}
	
	//과제제출(학생) 메인폼 =============================================
	@RequestMapping("submitAs") //기흔체크
	public String submitAs(HttpServletRequest request , HttpServletResponse response) {				
		String studentCheck = uc.studentCheck(request, response);
		if(studentCheck != null) { return studentCheck; } // 학생 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		
		User login = (User)request.getSession().getAttribute("login");
		class1 = (Class1)request.getSession().getAttribute("class1");
		
		List<Assignment> asList = asDao.list(class1);
		List<Integer> as_noList = asDao.selectAs_no(class1);
		List<String> filelist = new ArrayList<>();
		for(int as_no : as_noList) {
			String file = asDao.selectFile(login.getUser_no(), as_no);
			filelist.add(file);
		}

		request.setAttribute("filelist", filelist);
		request.setAttribute("asList", asList);
		return "classLMS/submitAs";
	}
	
	//과제제출(학생) 제출폼 =============================================
	@RequestMapping("submitassignment")
	public String submitassignment(HttpServletRequest request , HttpServletResponse response) {
		String studentCheck = uc.studentCheck(request, response);
		if(studentCheck != null) { return studentCheck; } // 학생 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		
		int as_no = Integer.parseInt(request.getParameter("as_no"));
		Assignment as1 = asDao.selectOne(as_no);
		request.setAttribute("as", as1);
		return "classLMS/submitassignment";
	}
	
	@RequestMapping("upload") //commons-fileupload-1.4 jar + commons-io-2.6.jar
	public String upload(HttpServletRequest request , HttpServletResponse response) {
	    User login = (User)request.getSession().getAttribute("login");
	    String path = request.getServletContext().getRealPath("/") + "files/";
	    File uploadDir = new File(path);
	    if (!uploadDir.exists()) uploadDir.mkdirs();
	    int maxSize = 10 * 1024 * 1024; // 10MB
	    String asNo = null;
	    String fileName = null;
	    if (ServletFileUpload.isMultipartContent(request)) {
	        try {
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            factory.setSizeThreshold(1024 * 1024); // 1MB 메모리 임계값
	            factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

	            ServletFileUpload upload = new ServletFileUpload(factory);
	            upload.setSizeMax(maxSize);

	            List<FileItem> formItems = upload.parseRequest(request);

	            if (formItems != null && formItems.size() > 0) {
	                for (FileItem item : formItems) {
	                    if (item.isFormField()) {
	                        if (item.getFieldName().equals("as_no")) {
	                            asNo = item.getString("utf-8");
	                        }
	                    } else {
	                    	if(item.getName() == null || item.getName().trim().equals("")) { throw new Exception();}
	                        fileName = new File(item.getName()).getName();
	                        String filePath = path + fileName;
	                        File storeFile = new File(filePath);
	                        item.write(storeFile);
	                    }
	                }
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            request.setAttribute("msg", "파일 업로드 중 오류가 발생했습니다.");
	            request.setAttribute("url", "submitassignment");
	            return "alert";
	        }
	    }
	    Sub_as as = new Sub_as();
	    as.setAs_no(Integer.parseInt(asNo));
	    as.setFile(fileName);
	    as.setUser_no(login.getUser_no());

	    Sub_as existing = asDao.selectSub_as(as.getUser_no(), as.getAs_no());
	    if (existing.getFile() == null) {
	        if (asDao.updateAs(as)) {
	            request.setAttribute("msg", "과제가 제출되었습니다.");
	            request.setAttribute("url", "submitAs");
	        } else {
	            request.setAttribute("msg", "과제제출에 실패하였습니다.");
	            request.setAttribute("url", "submitassignment");
	        }
	    } else {
	        if (asDao.updateAs(as)) {
	            request.setAttribute("msg", "제출된 파일을 수정했습니다.");
	            request.setAttribute("url", "submitAs");
	        } else {
	            request.setAttribute("msg", "수정에 실패하였습니다.");
	            request.setAttribute("url", "submitassignment");
	        }
	    }

	    return "alert";
	}

	// 이동원 학점 관리========================
	@RequestMapping("manageScore")
	public String manageScore(HttpServletRequest request , HttpServletResponse response) {

		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; } // 교수 확인
		Class1 class1 = new Class1();
		String cs = putClass1Session(request, class1); 
		if(cs != null) { return cs; } // 세션, class1 변수에 클래스 초기화
		
		class1 = (Class1)request.getSession().getAttribute("class1");		
		List<Student> studentList = rcDao.studentList(class1);
		studentList.sort((s1, s2) -> s1.getUser_no().compareTo(s2.getUser_no()));
		// 학번을 오름차순으로 정렬
		request.setAttribute("reg_users", studentList);
		return "classLMS/manageScore";
	}

}