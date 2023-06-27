package com.newlecture.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//매핑되는 주소들에 대해 해당 필터를 적용시킴, xml 작성 필요없음, jsp 파일에만 필터 적용시켜야 css파일에 적용이 안되어 이미지 안 짤림
@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

	//기존보다 더 추가된 추상 메소드
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request
			, ServletResponse response
			, FilterChain chain)
			throws IOException, ServletException {
		
		//다음 필터나 서블릿 실행 전 인코딩 설정
		request.setCharacterEncoding("UTF-8");
		
		//다음 필터나 서블릿이 실행되기 전에 before filter 출력
		//System.out.println("before filter");
		//조건 검사를 통해 다음 필터나 서블릿을 실행시키는 함수
		chain.doFilter(request, response);
		//다음 필터나 서블릿이 실행된 후 after filter 출력
		//System.out.println("after filter");
	}

	//기존보다 더 추가된 추상 메소드
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
