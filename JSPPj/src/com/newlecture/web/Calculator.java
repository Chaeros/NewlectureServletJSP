package com.newlecture.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class Calculator extends HttpServlet{
	/*
	 * @Override protected void service(HttpServletRequest req, HttpServletResponse
	 * resp) throws ServletException, IOException {
	 * 
	 * //html에서 소문자로 method 형식을 입력했어도 여기서는 반드시 대문자로 GET/POST 비교해줘야한다.
	 * if(req.getMethod().equals("GET")) { System.out.println("GET 요청옴"); } else
	 * if(req.getMethod().equals("POST")) { System.out.println("POST 요청옴"); }
	 * 
	 * //부모의 service 메소드는 GET메소드면 doGet메소드 실행, POST면 doPOST메소드를 실행한다. //단, doGet과
	 * doPOST 메소드가 정의되어 있어야 오류가 발생하지 않는다. //따라서 위 메소드 정의하지 않을거면 주석처리해야 문제없이 코드가
	 * 동작한다. //위 service 메소드를 밑의 super.service가 동작하기 이전 동작들을 수행시키는 용도로 사용할 수 있다.
	 * //service 메소드를 Override하지않으면 알아서 super.service가 동작한다.(service가 라우팅하는 역할)
	 * //GET방식과 POST방식 둘다 동일한 처리를 한다면 service작성하고 super.service는 없어도 됨
	 * super.service(req, resp); }
	 */
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGET 메소드가 호출되었습니다.");
		Cookie[] cookies = request.getCookies();
		
		String exp="0";
		
		if(cookies!=null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("exp")) {
					exp=c.getValue();
					break;
				}
			}
		}
		
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.write("<!DOCTYPE html>");
		out.write("<html>");
		out.write("<head>");
		out.write("<meta charset=\"UTF-8\">");
		out.write("<title>Insert title here</title>");
		out.write("<style>");
		out.write("input{");
		out.write("width:50px;");
		out.write("height:50px;");
		out.write("}");
		out.write(".output{");
		out.write("height:50px;");
		out.write("background:#e9e9e9;");
		out.write("font-size:24px;");
		out.write("font-weight:bold;");
		out.write("text-align:right;");
		out.write("padding: 0px 5px;");
		out.write("}");
		out.write("</style>");
		out.write("</head>");
		out.write("<body>");
		out.write("<form method=\"post\">"); //자기 페이지를 호출할 때는 action에 url을 안 넣어도 된다.
		out.write("<table>");
		out.write("<tr>");
		out.printf("<td class=\"output\" colspan=\"4\">%s</td>",exp);
		out.write("</tr>");
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"CE\"/></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"C\"/></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"BS\"/></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"/\"/></td>");
		out.write("</tr>");
					
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"7\"/></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"8\"/></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"9\"/></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"*\"/></td>");
		out.write("</tr>");
					
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"4\"/></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"5\"/></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"6\"/></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"-\"/></td>");
		out.write("</tr>");
					
		out.write("<tr>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"1\"/></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"2\"/></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"3\"/></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"+\"/></td>");
		out.write("</tr>");
					
		out.write("<tr>");
		out.write("<td></td>");
		out.write("<td><input type=\"submit\" name=\"value\" value=\"0\"/></td>");
		out.write("<td><input type=\"submit\" name=\"dot\" value=\".\"/></td>");
		out.write("<td><input type=\"submit\" name=\"operator\" value=\"=\"/></td>");
		out.write("</tr>");
					
		out.write("</table>");
		out.write("</form>");

		out.write("</body>");
		out.write("</html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPOST 메소드가 호출되었습니다.");
		//쿠키들을 배열로 읽어옴
		Cookie[] cookies = request.getCookies();
		
		//UTF-8 형식으로 전송하는 것
		response.setCharacterEncoding("UTF-8");
		//웹 브라우저에 UTF-8 형식으로 읽으라고 하는 것
		response.setContentType("text/html; charset=UTF-8");
		
		String value = request.getParameter("value");
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");
		
		String exp="";
		if(cookies!=null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("exp")) {
					exp=c.getValue();
					break;
				}
			}
		}
		
		if(operator!=null && operator.equals("=")){
			//javascript로 계산
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
			try {
				exp=String.valueOf(engine.eval(exp));
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(operator!=null && operator.equals("C")){
			//exp 내용 비우기
			exp="";
		}
		else {
			exp+=(value==null)?"":value;
			exp+=(operator==null)?"":operator;
			exp+=(dot==null)?"":dot;
		}
		
		
	
		
		Cookie expCookie = new Cookie("exp",exp);
		
		// 이걸 입력해야 쿠키가 바로 소멸된다.
		if(operator!=null && operator.equals("C"))
			expCookie.setMaxAge(0); 
		
		expCookie.setPath("/calculator");
		//이번 서블릿 실행 후 표기해줄 페이지를 지정
		response.addCookie(expCookie);
		response.sendRedirect("calculator");
	}
}
