package com.newlecture.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.newlecture.web.entity.Notice;
import com.newlecture.web.service.NoticeService;

//파일 업로드를 위해 멀티파트로 클라이언트로 전송한 경우에는, 서버 쪽에서도 멀티파트 방식으로 데이터를 받아야 하기때문에 이를 설정해줘야만 한다.
@MultipartConfig(
	fileSizeThreshold=1024*1024,  //전송하는 파일이 1MB(1024byte*1024)가 넘으면 메모리말고 디스크를 쓰도록 함
	maxFileSize=1024*1024*50,	//파일 하나의 사이즈 최대값, 여기서는 50MB로 설정함
	maxRequestSize=1024*1024*50*5	//한 번에 전송할 수 있는 모든 전체 요청에 대한 사이즈 최대값
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//WEB-INF라는 디렉토리는 웹에서 절대로 사용자가 요청할 수 없는 폴더이다. 서버에서만 호출 가능
		request
		.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp")
		.forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title=request.getParameter("title");
		
		System.out.print("title:");
		System.out.println(title);
		
		String content=request.getParameter("content");
		String isOpen=request.getParameter("open");
		
		//Part는 바이너리 컨텐트를 넘겨주는 역할 수행한다.
		//아래 반복문은 다중 파일 업로드에 사용되고 있는 코드이다.
		Collection<Part> parts = request.getParts();
		StringBuilder builder = new StringBuilder();
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue;
			
			Part filePart = p;
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			
			InputStream fis = filePart.getInputStream();
			
			//현재 웹루트를 통한 상대경로를 넘겨주면, 아래 함수를 통해 실제 절대 경로를 반환해준다.
			String realPath = request.getServletContext().getRealPath("/upload");
			System.out.println(realPath);
			 
			//현재 시스템(os)이 갖고 있는 경로 구분법을 자동으로 찾아서 대입시켜준다. File.separator
			String filePath = realPath+ File.separator+fileName;
			FileOutputStream fos = new FileOutputStream(filePath);
			
			//바이트를 반환해줌, 단 int 형식으로 반환해줌, 끝이면 -1 반환함
			//buf를 통해 1바이트씩 받아오고 옮기지않고, 1024바이트씩 옮김.
			//단 314바이트가 있었다고 하면, size에는 314가 반환됨
			int size=0;
			byte[] buf = new byte[1024];
			while((size=fis.read(buf))!=-1)
			{
				//buf의 0번째 인덱스부터 size 크기만큼 저장
				fos.write(buf,0,size);
			}
			fos.close();
			fis.close();
			
		}
		//builder내의 start 인덱스에서 end-1까지 값을 지움
		builder.delete(builder.length()-1, builder.length());
		
		
		
		boolean pub = false;
		if(isOpen!=null)
			pub=true;
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("newlec");
		notice.setFiles(builder.toString());
		
		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);
		
		response.sendRedirect("list");
		
//		//UTF-8 형식으로 전송하는 것
//		response.setCharacterEncoding("UTF-8");
//		//웹 브라우저에 UTF-8 형식으로 읽으라고 하는 것
//		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		out.printf("title : %s<br>", title);
//		out.printf("content : %s<br>", content);
//		out.printf("open : %s<br>", isOpen);
	}
}
