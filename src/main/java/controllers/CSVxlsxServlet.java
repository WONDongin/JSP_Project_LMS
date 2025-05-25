package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import models.classes.AsDao;
import models.classes.Assignment;
import models.classes.Class1;
import models.classes.Reg_classDao;
import models.classes.Student;
import models.classes.SubAsDao;
import models.classes.Sub_as;

//이동원
@MultipartConfig
@WebServlet(urlPatterns = {"/classLMS/upload_asCSV", "/classLMS/download_asXLSX", // 과제 점수부여
		"/classLMS/upload_stCSV", "/classLMS/download_stXLSX"}) // 점수 부여
public class CSVxlsxServlet extends HttpServlet {
	AsDao asDao = new AsDao();
	SubAsDao subAsDao = new SubAsDao();
	Reg_classDao rcDao = new Reg_classDao();
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { // 업로드
		String path = request.getServletPath();
		Class1 class1 = (Class1) request.getSession().getAttribute("class1");
		// multipart/form-data 형식인지 확인 (파일 업로드 요청인지 체크)
		int as_no = -1;
		String msg = null;
		String url = null;
		FileItem csvFileItem = null;
	    boolean isMultipart = ServletFileUpload.isMultipartContent(request);
	    if (!isMultipart) {
	    	request.setAttribute("msg", "파일 형식이 올바르지 않습니다.");
	        request.setAttribute("url", "classLMS/manageAs"); // 되돌아갈 페이지 경로
	    } else if(path.equals("/classLMS/upload_asCSV") && class1 != null) { // 과제점수 업로드
			Map<String, Integer> scores = new HashMap<>(); // 학번, 점수
		    // 파일 아이템을 생성할 때 사용할 팩토리 객체 생성 (임시 저장소 등 설정 가능)
		    DiskFileItemFactory factory = new DiskFileItemFactory();
		    // 업로드된 요청을 처리할 업로더 객체 생성
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    try {
		        // 요청에서 업로드된 파일/필드를 추출해서 리스트로 받음
		        List<FileItem> items = upload.parseRequest(request);
		        for (FileItem item : items) {
		            if (item.isFormField()) {
		            	if("as_no".equals(item.getFieldName())) { // as_no 파라미터값 가져오기
		            		as_no = Integer.parseInt(item.getString("utf-8"));
		            	} 
		            } else if("file".equals(item.getFieldName())) {
	            		csvFileItem = item;
	            	}
		        } // for
                
		        // 업로드된 파일을 한 줄씩 읽기 위해 BufferedReader 사용
	            try (BufferedReader reader = new BufferedReader(
	                    new InputStreamReader(csvFileItem.getInputStream(), StandardCharsets.UTF_8))) {
	            	boolean isFirstLine = true;
	            	String line;
	            	while ((line = reader.readLine()) != null) {
	            	    if (isFirstLine) { isFirstLine = false; continue; }
	            	    String[] data = line.split(",", -1);
	            	    try {
	                	    if(as_no == Integer.parseInt(data[0])) {
		                	    String user_no = data[1];
		                	    if(data[3] == null || data[3].trim().equals("") || data.length != 4) {
		                	    	continue;
		                	    }
		                	    int as_score = Integer.parseInt(data[3]);
		                	    scores.put(user_no, as_score);
	                	    }// if
	            	    } catch (NumberFormatException e) { 
	            	    	msg = "양식이 올바르지 않습니다.";
	        	        	url = "classLMS/manageAs?as_no=" + as_no;
	            	    } // try
	            	} // while
	            } // try
	            
		        int count = 0;
		        if( (count = subAsDao.updateScores(as_no, scores)) == scores.size()) { // submitted_assignment 테이블에 점수 넣기
		        	msg = count + "명의 성적 반영 성공\n꼭 반영여부를 확인하세요.";
		        	url = "classLMS/manageAs?as_no=" + as_no;
		        } else {
		        	msg = count + "명의 성적 반영 성공\n성적 반영에 오류가 생겼으니 확인하세요.";
		        	url = "classLMS/manageAs?as_no=" + as_no;
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        msg = "오류 발생. 업로드 실패.";
	        	url = "classLMS/manageAs?as_no=" + as_no;
		    }
	    } else if(path.equals("/classLMS/upload_stCSV") && class1 != null) { // 학생 점수 업로드
	    	Map<String, Integer[]> exMap = new HashMap<>(); // 학번, {중간, 기말}
	    	
	    	Map<String, Student> stMap = new HashMap<>();
	    	List<Student> studentList = rcDao.studentList(class1);
	    	for(Student st : studentList) { stMap.put(st.getUser_no(), st); }
			
			
		    // 파일 아이템을 생성할 때 사용할 팩토리 객체 생성 (임시 저장소 등 설정 가능)
		    DiskFileItemFactory factory = new DiskFileItemFactory();
		    // 업로드된 요청을 처리할 업로더 객체 생성
		    ServletFileUpload upload = new ServletFileUpload(factory);
		    try {
		        // 요청에서 업로드된 파일/필드를 추출해서 리스트로 받음
		        List<FileItem> items = upload.parseRequest(request);
		        for (FileItem item : items) {
		            // 일반 폼필드가 아닐 때
		            if (!item.isFormField()) {
		                // 업로드된 파일을 한 줄씩 읽기 위해 BufferedReader 사용
		                try (BufferedReader reader = new BufferedReader(
		                        new InputStreamReader(item.getInputStream(), StandardCharsets.UTF_8))) {
		                	boolean isFirstLine = true;
		                	String line;
		                	while ((line = reader.readLine()) != null) {
		                	    if (isFirstLine) { isFirstLine = false; continue; }
		                	    String[] data = line.split(",", -1);
		                	    Integer[] exams = new Integer[2];
		                	    try {
			                	    if(stMap.get(data[0]) != null) { // 입력된 학번의 학생이 있는 경우
				                	    String user_no = data[0];
				                	    if(data.length != 5) {
				                	    	continue;
				                	    }
				                	    if(data[3] != null && !data[3].trim().equals("")) {
				                	    	Integer exam1 = Integer.parseInt(data[3]);
				                	    	exams[0] = exam1; // 중간점수
				                	    }
				                	    if(data[4] != null && !data[4].trim().equals("")) {
				                	    	Integer exam2 = Integer.parseInt(data[4]);
				                	    	exams[1] = exam2; // 기말점수
				                	    }
				                	    exMap.put(user_no, exams);
			                	    } // if
		                	    } catch (NumberFormatException e) { 
		                	    	msg = "양식이 올바르지 않습니다.";
		            	        	url = "classLMS/manageAs?as_no=" + as_no;
		                	    } // try
		                	} // while
		                } // try
		            } // if
		        } // for
		        if(rcDao.updateExamScore(class1, studentList, exMap)) { // registered_classes에 점수 넣기
		        	msg = "성적 반영 성공!!\n꼭 반영여부를 확인하세요.";
		        } else {
		        	msg = "성적 반영 중 오류가 생겼으니 확인하세요.";
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        msg = "오류 발생. 업로드 실패.";
		    }
		    url = "classLMS/manageScore";
	    } // if
	    request.setAttribute("msg", msg);
	    request.setAttribute("url", url);
	    System.out.println(msg);
	    System.out.println(url);
//	    request.getRequestDispatcher("/views/alert.jsp").forward(request, response);
	    response.sendRedirect(request.getContextPath() + "/" + url);
        return;
        // alert에서 아무리 해도 EL로 request 객체로 못받아서 Redirect로 처리.
    } // doPost

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException { // xlsx다운로드
        String path = request.getServletPath();
        Class1 class1 = (Class1)request.getSession().getAttribute("class1");
        if(path.equals("/classLMS/download_stXLSX") && class1 != null) { // 학점
        	handleStDown(request, response);
        }else if(path.equals("/classLMS/download_asXLSX") && class1 != null){ // 과제
	        int as_no = Integer.parseInt(request.getParameter("as_no"));
	        handleAsDown(request, response);
        }
    }
    
    private void handleStDown(HttpServletRequest request, HttpServletResponse response)
    	  	throws IOException { // 학점 관리
    	try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("template");
			CellStyle yellowCell = workbook.createCellStyle();
			CellStyle redCell = workbook.createCellStyle();
			CellStyle nameCell = workbook.createCellStyle();
			Font font = workbook.createFont();
			Class1 class1 = (Class1)request.getSession().getAttribute("class1");
			List<Student> studentList = rcDao.studentList(class1);
			
			CellStyle[] CSs = new CellStyle[3];
			CSs[0] = yellowCell;
			CSs[1] = redCell;
			CSs[2] = nameCell;
			for(CellStyle cs : CSs) { // 사방 테두리 설정
				cs.setBorderTop(BorderStyle.THIN);
				cs.setBorderBottom(BorderStyle.THIN);
				cs.setBorderLeft(BorderStyle.THIN);
				cs.setBorderRight(BorderStyle.THIN);
			}
			font.setFontHeightInPoints((short) 20);
			font.setBold(true);
			yellowCell.setFont(font);
			yellowCell.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			yellowCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			yellowCell.setWrapText(false);
			redCell.setFillForegroundColor(IndexedColors.ROSE.getIndex());
			redCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			nameCell.setFont(font);
			nameCell.setFillForegroundColor(IndexedColors.ROSE.getIndex());
			nameCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			// 1행: 안내 메시지
			Row row0 = sheet.createRow(0);
			row0.createCell(0).setCellValue(
				"※ 노란칸의 값만 수정하세요.\n"
				+ "파일을 저장할 때 꼭 CSV UTF-8(쉼표로 분리)로 저장하세요.\n"
				+ "csv파일로 저장 후 파일을 열지 마세요.\n이 행을 삭제 후 업로드하세요."
			);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
			// 0번째 행의 0열 ~ 4열까지 병합 (총 5칸 병합)
			row0.getCell(0).setCellStyle(yellowCell);

			// 2행: 헤더
			Row row1 = sheet.createRow(1);
			row1.createCell(0).setCellValue("학번");
			row1.createCell(1).setCellValue("학년");
			row1.createCell(2).setCellValue("이름");
			row1.createCell(3).setCellValue("중간고사 (최대 30)");
			row1.createCell(4).setCellValue("기말고사 (최대 40)");
			int i = 0;
			for(Student st : studentList) {
				Row r = sheet.createRow(2 + i);
				r.createCell(0).setCellValue(st.getUser_no());
				r.createCell(1).setCellValue(st.getUser_grade());
				r.createCell(2).setCellValue(st.getUser_name());
				r.createCell(3).setCellValue("");
				r.createCell(4).setCellValue("");
				r.getCell(0).setCellStyle(redCell);
				r.getCell(1).setCellStyle(redCell);
				r.getCell(2).setCellStyle(nameCell);
				r.getCell(3).setCellStyle(yellowCell);
				r.getCell(4).setCellStyle(yellowCell);
				i++;
			} // for
			
			for(i = 0; i < 5; i++) {
				row1.getCell(i).setCellStyle(redCell);
				sheet.setColumnWidth(i, 120 * 36);
			}
			row0.setHeight((short)900);

			// 응답 설정
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"score_template.xlsx\"");

			try (OutputStream out = response.getOutputStream()) {
			    workbook.write(out);
			}
		}
	}

	private void handleAsDown(HttpServletRequest request, HttpServletResponse response)
            throws IOException { // 과제 관리
    	 try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("template");
			CellStyle yellowCell = workbook.createCellStyle();
			CellStyle redCell = workbook.createCellStyle();
			CellStyle nameCell = workbook.createCellStyle();
			Font font = workbook.createFont();
			
			CellStyle[] CSs = new CellStyle[3];
			CSs[0] = yellowCell;
			CSs[1] = redCell;
			CSs[2] = nameCell;
			for(CellStyle cs : CSs) { // 사방 테두리 설정
				cs.setBorderTop(BorderStyle.THIN);
				cs.setBorderBottom(BorderStyle.THIN);
				cs.setBorderLeft(BorderStyle.THIN);
				cs.setBorderRight(BorderStyle.THIN);
			}
			font.setFontHeightInPoints((short) 20);
			font.setBold(true);
			yellowCell.setFont(font);
			yellowCell.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			yellowCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			redCell.setFillForegroundColor(IndexedColors.ROSE.getIndex());
			redCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			nameCell.setFont(font);
			nameCell.setFillForegroundColor(IndexedColors.ROSE.getIndex());
			nameCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			// 1행: 안내 메시지
			Row row0 = sheet.createRow(0);
			row0.createCell(0).setCellValue(
				"※ 노란칸의 값만 수정하세요.\n"
				+ "파일을 저장할 때 꼭 CSV UTF-8(쉼표로 분리)로 저장하세요.\n"
				+ "csv파일로 저장 후 파일을 열지 마세요.\n이 행을 삭제 후 업로드하세요."
			);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
			// 0번째 행의 0열 ~ 3열까지 병합 (총 4칸 병합)
			row0.getCell(0).setCellStyle(yellowCell);

			// 2행: 헤더
			Row row1 = sheet.createRow(1);
			row1.createCell(0).setCellValue("과제번호");
			row1.createCell(1).setCellValue("학번");
			row1.createCell(2).setCellValue("이름");
			row1.createCell(3).setCellValue("과제점수");
			int i = 0;
			
			int as_no = Integer.parseInt(request.getParameter("as_no"));
			List<Sub_as> sub_asList = subAsDao.list(as_no);
			
			for(Sub_as sub_as : sub_asList) { if(sub_as == null) continue;
				Row r = sheet.createRow(2 + i);
				r.createCell(0).setCellValue(as_no);
				r.createCell(1).setCellValue(sub_as.getUser_no());
				r.createCell(2).setCellValue(sub_as.getUser_name());
				r.createCell(3).setCellValue("");
				r.getCell(0).setCellStyle(redCell);
				r.getCell(1).setCellStyle(redCell);
				r.getCell(2).setCellStyle(nameCell);
				r.getCell(3).setCellStyle(yellowCell);
				i++;
			} // for
			
			for(i = 0; i < 4; i++) {
				row1.getCell(i).setCellStyle(redCell);
				sheet.setColumnWidth(i, 120 * 36);
			}
			row0.setHeight((short)900);

			// 응답 설정
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=\"assignment_template.xlsx\"");

			try (OutputStream out = response.getOutputStream()) {
			    workbook.write(out);
			}
		}
    } // 메서드 끝
}
