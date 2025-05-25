package controllers;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.util.*;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import models.classes.Class1;
import models.classes.Class1Dao;
import models.classes.Reg_classDao;
import models.classes.Student;
import models.users.User;

@WebServlet(urlPatterns = { "/deptLMS/*" }, initParams = { @WebInitParam(name = "view", value = "/views/") })
public class DeptLMSController extends MskimRequestMapping {
	private Class1Dao class1Dao = new Class1Dao();
	private Reg_classDao rcDao = new Reg_classDao();
	private UserController uc = new UserController();
	
	@RequestMapping("addClass")
	public String addClass(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		User login = (User) request.getSession().getAttribute("login");
		String today = LocalDate.now().toString();
		request.setAttribute("today", today);
		if(profCheck != null) { return profCheck; }
		return "deptLMS/addClass";
	}

	@RequestMapping("addClassList")
	public String addClassList(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		String type = request.getParameter("type");
		String find = request.getParameter("fine");
		List<Class1> classesList = class1Dao.selectAllClass(type, find);
		request.setAttribute("classesList", classesList);
		return "deptLMS/addClassList";
	}
	
	@RequestMapping("addClass1")
	public String addClass1(HttpServletRequest request, HttpServletResponse response) {
	    String profCheck = uc.profCheck(request, response);
	    if (profCheck != null) return profCheck;
	    
	    User login = (User) request.getSession().getAttribute("login");
	    String uploadPath = request.getServletContext().getRealPath("/") + "/files/";
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) uploadDir.mkdirs();
	    System.out.println(uploadPath);
	    Class1 cls = new Class1();
	    cls.setMajor_no(login.getMajor_no());
	    cls.setUser_no(login.getUser_no());

	    // 현재 연·학기 설정
	    Calendar cal = Calendar.getInstance();
	    int year  = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH) + 1;
	    int term  = (month >= 3 && month <= 8) ? 1 : 2;
	    cls.setYear(year);
	    cls.setTerm(term);

	    // commons-fileupload 세팅
	    DiskFileItemFactory factory = new DiskFileItemFactory();
	    factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	    ServletFileUpload upload = new ServletFileUpload(factory);
	    upload.setHeaderEncoding("UTF-8");
	    upload.setFileSizeMax(20 * 1024 * 1024); // 10MB per file
	    upload.setSizeMax(50 * 1024 * 1024);     // 30MB total

	    List<Integer> days = new ArrayList<>();
	    try {
	        List<FileItem> items = upload.parseRequest(request);
	        for (FileItem item : items) {
	            if (item.isFormField()) {
	                String name  = item.getFieldName();
	                String value = item.getString("UTF-8");
	                switch (name) {
	                    case "className":      cls.setClass_name(value);         break;
	                    case "classNo":        cls.setClass_no(value);           break;
	                    case "classBan":       cls.setBan(value);                break;
	                    case "classGrade":     cls.setClass_grade(Integer.parseInt(value)); break;
	                    case "credit":         cls.setCredit(Integer.parseInt(value));     break;
	                    case "maxP":           cls.setMax_p(Integer.parseInt(value));      break;
	                    case "classRoom":      cls.setClassroom(value);          break;
	                    case "sTime":          cls.setS_time(Integer.parseInt(value));     break;
	                    case "eTime":          cls.setE_time(Integer.parseInt(value));     break;
	                    case "courseSyllabus": cls.setC_plan(value);             break;
	                    case "sDate":          cls.setS_date(Date.valueOf(value));       break;
	                    case "eDate":          cls.setE_date(Date.valueOf(value));       break;
	                    case "days":
	                        days.add(Integer.parseInt(value));
	                        break;
	                }
	            } else if ("file1".equals(item.getFieldName()) && item.getSize() > 0) {
	                // 첨부파일 처리
	                String origName = new File(item.getName()).getName();
	                String ext = origName.contains(".")
	                    ? origName.substring(origName.lastIndexOf('.'))
	                    : "";
	                String savedName = UUID.randomUUID().toString() + ext;
	                item.write(new File(uploadPath, savedName));
	                cls.setFile(savedName);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("msg", "파일 업로드 중 오류가 발생했습니다.");
	        request.setAttribute("cls", cls);
	        return "deptLMS/addClass";
	    }

	    if (!days.isEmpty()) {
	        cls.setDays(days);
	    }

	    // 강의 시간/강의실 중복 체크
	    if (timeCheck(cls, request, response)) return "alert";

	    // DB 저장
	    if (class1Dao.insertClass(cls)) {
	        request.setAttribute("msg", "강의가 정상적으로 추가되었습니다.");
	        request.setAttribute("url", "myClass");
	        return "alert";
	        
	    } else {
	        request.setAttribute("msg", "강의 추가 중 오류가 발생했습니다.");
	        request.setAttribute("cls", cls);
	        return "deptLMS/addClass";
	    }
	}


	// 예전 업로드 방식
	/*// 강의 추가 기능 - 교수
	@RequestMapping("addClass1")
	public String addClass1(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		Class1 cls = new Class1();
		User login = (User) request.getSession().getAttribute("login");
		String path = request.getServletContext().getRealPath("/") + "/files/";
		File f = new File(path);
		if (!f.exists())
			f.mkdirs();
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		cls.setMajor_no(login.getMajor_no());
		cls.setUser_no(login.getUser_no());
	
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int term = (month >= 3 && month <= 8) ? 1 : 2;
	
		cls.setYear(year);
		cls.setTerm(term);
	
		cls.setClass_name(multi.getParameter("className"));
		cls.setClass_no(multi.getParameter("classNo"));
		cls.setBan(multi.getParameter("classBan"));
		cls.setClass_grade(Integer.parseInt(multi.getParameter("classGrade")));
		cls.setCredit(Integer.parseInt(multi.getParameter("credit")));
		cls.setMax_p(Integer.parseInt(multi.getParameter("maxP")));
		cls.setClassroom(multi.getParameter("classRoom"));
		cls.setS_time(Integer.parseInt(multi.getParameter("sTime")));
		cls.setE_time(Integer.parseInt(multi.getParameter("eTime")));
		cls.setC_plan(multi.getParameter("courseSyllabus"));
		cls.setFile(multi.getFilesystemName("file1"));
		cls.setS_date(Date.valueOf(multi.getParameter("sDate")));
		cls.setE_date(Date.valueOf(multi.getParameter("eDate")));
		String[] daysArr = multi.getParameterValues("days");
		
		// 요일 넣기
		if (daysArr != null) {
			List<Integer> days = Arrays.stream(daysArr).map(Integer::valueOf).collect(Collectors.toList());
			cls.setDays(days);
			System.out.println("days" + days);
		}
		System.out.println(timeCheck(cls, request, response));
		if(timeCheck(cls, request, response)) return "alert";
		
		if (class1Dao.insertClass(cls)) {
			request.setAttribute("msg", "강의가 정상적으로 추가되었습니다.");
			request.setAttribute("url", "myClass");
		} else {
			request.setAttribute("msg", "강의 추가 중 오류가 발생했습니다.");
			request.setAttribute("url", "addClass");
		}
		return "alert";
	}
	*/
	// 자신의 강의 목록 - 교수
	@RequestMapping("myClass")
	public String myClasses(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		User login = (User) request.getSession().getAttribute("login");
		String userNo = login.getUser_no();
		List<Class1> classesList = class1Dao.selectByProfessor(userNo);
		
		String today = LocalDate.now().toString();
		request.setAttribute("today", today);
		request.setAttribute("classesList", classesList);
		return "deptLMS/myClass";
	}
	
	// 강의 수정 - 교수
	@RequestMapping("updateClass")
	public String showUpdateForm(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		Class1 key = new Class1();
		
		String today = LocalDate.now().toString();
		request.setAttribute("today", today);
		
		key.setClass_no(request.getParameter("class_no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));

		Class1 cls = class1Dao.selectOne(key);
		request.setAttribute("cls", cls);
		return "deptLMS/updateClass";
	}
	
	@RequestMapping("updateClass1")
    public String updateClass1(HttpServletRequest request, HttpServletResponse response) {
        String profCheck = uc.profCheck(request, response);
        if (profCheck != null) return profCheck;
        
        User login = (User) request.getSession().getAttribute("login");
        String path = request.getServletContext().getRealPath("/") + "/files/";
        File uploadDir = new File(path);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        
        String today = LocalDate.now().toString();
		request.setAttribute("today", today);
        
        Class1 cls = new Class1();
        cls.setMajor_no(login.getMajor_no());
        cls.setUser_no(login.getUser_no());

        // commons-fileupload 세팅
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        upload.setFileSizeMax(20 * 1024 * 1024); // 개별 파일 최대 10MB
        upload.setSizeMax(50 * 1024 * 1024);     // 요청 전체 최대 30MB
        List<Integer> days = new ArrayList<>();
        try {
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // form field 처리
                    String name  = item.getFieldName();
                    String value = item.getString("UTF-8");
                    switch (name) {
                        case "className":      cls.setClass_name(value);       break;
                        case "class_no":       cls.setClass_no(value);         break;
                        case "classBan":       cls.setBan(value);              break;
                        case "year":           cls.setYear(Integer.parseInt(value));  break;
                        case "term":           cls.setTerm(Integer.parseInt(value));  break;
                        case "classGrade":     cls.setClass_grade(Integer.parseInt(value)); break;
                        case "credit":         cls.setCredit(Integer.parseInt(value));     break;
                        case "maxP":           cls.setMax_p(Integer.parseInt(value));      break;
                        case "classRoom":      cls.setClassroom(value);        break;
                        case "sTime":          cls.setS_time(Integer.parseInt(value));     break;
                        case "eTime":          cls.setE_time(Integer.parseInt(value));     break;
                        case "courseSyllabus": cls.setC_plan(value);           break;
                        case "sDate":          cls.setS_date(Date.valueOf(value));         break;
                        case "eDate":          cls.setE_date(Date.valueOf(value));         break;
                        case "days":
                            days.add(Integer.parseInt(value));
                            break;
                    }
                } else {
                    // file field 처리
                    if ("file1".equals(item.getFieldName()) && item.getSize() > 0) {
                        String orig = new File(item.getName()).getName();
                        String ext  = orig.substring(orig.lastIndexOf('.'));
                        String savedName = UUID.randomUUID().toString() + ext;
                        item.write(new File(path, savedName));
                        cls.setFile(savedName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "파일 업로드 중 오류 발생");
            return "deptLMS/updateClass";
        }

        // 요일 설정
        if (!days.isEmpty()) {
            cls.setDays(days);
        }
        request.setAttribute("cls", cls);
        request.setAttribute("class_no", cls.getClass_no());
    	request.setAttribute("ban", cls.getBan());
    	request.setAttribute("year", cls.getYear());
    	request.setAttribute("term", cls.getTerm());
        // 시간/강의실 중복 체크
        if (timeCheck(cls, request, response)) {
        	return "deptLMS/updateClass";
        }
        
        // DB 업데이트
        if (class1Dao.update(cls)) {
            request.setAttribute("msg", "강의가 정상적으로 수정되었습니다.");
	        request.setAttribute("url", "myClass");
	        return "alert";
	    } else {
            request.setAttribute("msg", "강의 수정에 실패하였습니다.");
        }
        return "deptLMS/updateClass";
    }

	/*	기존 업로드 방식
		// 강의 수정 기능 - 교수
		@RequestMapping("updateClass1")
		public String updateClass1(HttpServletRequest request, HttpServletResponse response) {
			String profCheck = uc.profCheck(request, response);
			if(profCheck != null) { return profCheck; }
			Class1 cls = new Class1();
			User login = (User) request.getSession().getAttribute("login");
			String path = request.getServletContext().getRealPath("/") + "/files/";
			File f = new File(path);
			if (!f.exists())
				f.mkdirs();
			MultipartRequest multi = null;
			try {
				multi = new MultipartRequest(request, path, 10 * 1024 * 1024, "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
			cls.setMajor_no(login.getMajor_no());
			cls.setUser_no(login.getUser_no());
	
			cls.setClass_name(multi.getParameter("className"));
			cls.setClass_no(multi.getParameter("classNo"));
			cls.setBan(multi.getParameter("classBan"));
			cls.setYear(Integer.parseInt(multi.getParameter("year")));
			cls.setTerm(Integer.parseInt(multi.getParameter("term")));
			cls.setCredit(Integer.parseInt(multi.getParameter("credit")));
			cls.setMax_p(Integer.parseInt(multi.getParameter("maxP")));
			cls.setClassroom(multi.getParameter("classRoom"));
			cls.setS_time(Integer.parseInt(multi.getParameter("sTime")));
			cls.setE_time(Integer.parseInt(multi.getParameter("eTime")));
			cls.setC_plan(multi.getParameter("courseSyllabus"));
			cls.setFile(multi.getFilesystemName("file1"));
			System.out.println("업로드된 파일명: " + multi.getFilesystemName("file1"));
			System.out.println("실제 저장 경로: " + path);
			cls.setS_date(Date.valueOf(multi.getParameter("sDate")));
			cls.setE_date(Date.valueOf(multi.getParameter("eDate")));
	
			String[] daysArr = multi.getParameterValues("days");
			if (daysArr != null) {
				List<Integer> days = Arrays.stream(daysArr).map(Integer::valueOf).collect(Collectors.toList());
				cls.setDays(days);
			}
			
			System.out.println(timeCheck(cls, request, response));
			if(timeCheck(cls, request, response)) return "alert";
			
			if (class1Dao.update(cls)) {
				request.setAttribute("msg", "강의가 정상적으로 수정 성공하였습니다.");
				request.setAttribute("url", "myClass");
			} else {
				request.setAttribute("msg", "강의를 수정 실패하였습니다.");
				request.setAttribute("url", "updateClass");
			}
			return "alert";
		}
	*/
	// 강의 삭제 - 교수
	@RequestMapping("deleteClass")
	public String deleteClass(HttpServletRequest request, HttpServletResponse response) {
		String profCheck = uc.profCheck(request, response);
		if(profCheck != null) { return profCheck; }
		Class1 key = new Class1();
		key.setClass_no(request.getParameter("class_no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));

		if (class1Dao.delete(key)) {
			request.setAttribute("msg", "강의가 정상적으로 삭제되었습니다.");
		} else {
			request.setAttribute("msg", "강의 삭제에 실패했습니다.");
		}
		request.setAttribute("url", "myClass");
		return "alert";
	}

	// 수강 목록 - 학생
	@RequestMapping("classList")
	public String classList(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int term = (month >= 3 && month <= 8) ? 1 : 2;
		
		
		Class1 cls = new Class1();
		cls.setUser_no(login.getUser_no());
		cls.setYear(year);
		cls.setTerm(term);
		
		String today = LocalDate.now().toString();
		request.setAttribute("today", today);
		
		List<Class1> classesList = class1Dao.nowClassList(cls);
		request.setAttribute("classesList", classesList);
		return "deptLMS/classList";
	}
	// 수강 확정 - 학생
	@RequestMapping("confirmClass")
	public String confirmClass(HttpServletRequest request, HttpServletResponse response) {
		Class1 key = new Class1();
		User login = (User) request.getSession().getAttribute("login");
		key.setUser_no(login.getUser_no());
		key.setClass_no(request.getParameter("class_no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));
		if (class1Dao.confirmClass(key)) {
			request.setAttribute("msg", "수강 확정 되었습니다.");
		} else {
			request.setAttribute("msg", "수강 확정을 실패했습니다.");
		}
		request.setAttribute("url", "classList");
		return "alert";
	}

	// 수강 신청 취소
	@RequestMapping("dropClass")
	public String dropClass(HttpServletRequest request, HttpServletResponse response) {
		User login = (User) request.getSession().getAttribute("login");
		Class1 key = new Class1();
		key.setUser_no(login.getUser_no());
		key.setClass_no(request.getParameter("class_no"));
		key.setBan(request.getParameter("ban"));
		key.setYear(Integer.parseInt(request.getParameter("year")));
		key.setTerm(Integer.parseInt(request.getParameter("term")));
		if (class1Dao.cancelClass(key)) {
			request.setAttribute("msg", "강의가 정상적으로 취소되었습니다.");
		} else {
			request.setAttribute("msg", "수강 신청 취소를 실패했습니다.");
		}
		request.setAttribute("url", "classList");
		return "alert";
	}
	
	private boolean timeCheck(Class1 cls, HttpServletRequest request, HttpServletResponse response) {
		// 강의 시간 비교를 위한 데이터값
		List<Class1> classesList = class1Dao.selectTimeClash(cls);
		for(Class1 class1I : classesList) {
			if(cls.getClass_name().equals(class1I.getClass_name())) return false;
			for(Integer daysI : class1I.getDays()) {
				for(Integer daysJ : cls.getDays()) {
					if(daysI.equals(daysJ)) {
						if(!(cls.getE_time() <= class1I.getS_time() || cls.getS_time() >= class1I.getE_time())) {
							if(cls.getUser_no().equals(class1I.getUser_no())) {
								request.setAttribute("msg", "강의 [" + class1I.getClass_name() + "] 와 시간 혹은 요일이 겹칩니다.");
								return true;
							} else if(cls.getClassroom().equals(class1I.getClassroom())) {
								System.out.println(cls.getBan()+" 같나욘?" + class1I.getBan());
								request.setAttribute("msg", "강의 [" + class1I.getClass_name() + "] 와 강의실이 겹칩니다.");
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	// 관리자가 보는 모든 클래스
	@RequestMapping("allClasses")
	public String allClasses(HttpServletRequest request, HttpServletResponse response) {
		String adminCheck = uc.adminCheck(request, response);
		if(adminCheck != null) { return adminCheck; } // 관리자 확인
		
		List<Class1> classList = class1Dao.selectAllClass("","");
		for(Class1 c : classList) {
			System.out.println(c);
		}
		request.setAttribute("classList", classList);
		return "deptLMS/allClasses";
	}
	
}
