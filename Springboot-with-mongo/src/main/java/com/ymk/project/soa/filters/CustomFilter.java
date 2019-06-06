package com.ymk.project.soa.filters;

import java.io.IOException;

import javax.servlet.*;

import org.springframework.stereotype.Component;

@Component
public class CustomFilter implements Filter {

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		arg2.doFilter(arg0, arg1);
		
	}

}
