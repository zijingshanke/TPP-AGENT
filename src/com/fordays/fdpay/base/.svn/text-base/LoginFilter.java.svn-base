package com.fordays.fdpay.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.right.UserRightInfo;

public class LoginFilter extends HttpServlet implements Filter{
	protected FilterConfig filterConfig;
	
	
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		UserRightInfo session = request.getSession().getAttribute("URI")==null?null:(UserRightInfo) request.getSession().getAttribute("URI");
		Agent agent=null;
		if(session!=null)
			agent = session.getAgent();
			String method="";
			if(request.getParameter("method")!=null)
				method = "?method="+request.getParameter("method");
			String url = request.getRequestURI()+method;
			if(url.equals("/index.jsp")||url.equals("/login.jsp")||url.equals("/agent/register.jsp")
					||url.equals("/agent/agent.do?method=register")
					||url.equals("/agent/agent.do?method=login")
					||url.equals("/bank/ccbResult.do?thisAction=receive")
						||url.equals("/agent/createImage.jsp")
					||url.equals("/agent/agent.do?method=checkExist")){
				filterChain.doFilter(request, response);
				return;
			}
			else if(agent==null || agent.getId()<1){
				response.sendRedirect("/fdpay-client/login.jsp");
				return;
			}
			else{
				filterChain.doFilter(request, response);
				return;
			}
//			ServletRequest.getRequestDispatcher("");
//			request.getRequestDispatcher("index.jsp");
	}
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
	public void destory(){
		
	}
}
