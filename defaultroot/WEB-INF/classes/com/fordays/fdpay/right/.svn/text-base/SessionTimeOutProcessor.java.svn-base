package com.fordays.fdpay.right;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.RequestProcessor;
import com.fordays.fdpay.base.Constant;

public class SessionTimeOutProcessor extends RequestProcessor
{

	public boolean processPreprocess(HttpServletRequest request,
	    HttpServletResponse response)
	{
		UserRightInfo URI=(UserRightInfo)request.getSession().getAttribute("URI");		
		String actionName = request.getQueryString();
		String path = request.getServletPath();
		String url = path + "?" + actionName;
if(url.indexOf("security.do")>0)
{
	System.out.print(url);
}
		
		/*
		 * if (URI == null || URI.getAgent() == null) { try { for (int i = 0; i <
		 * Constant.url.size(); i++) { String temp = Constant.url.get(i);
		 * 
		 * if(temp.startsWith("gateway")) temp =temp.replace(".", ".do?service=");
		 * else temp =temp.replace(".", ".do?thisAction="); if (url.indexOf(temp) >=
		 * 0) return true; } response.sendRedirect(request.getContextPath() +
		 * "/login.jsp"); }
		 */

		if (path.indexOf("/cooperate/gateway.do") >= 0)
		{
			return true;
		} else if(path.indexOf("/cooperate/credit.do") >= 0){
			return true;
		}
		else if (path.indexOf("/cooperate/agentBind.do") >= 0)
		{
			return true;
		}else if (path.indexOf("/cooperate/freeze.do") >= 0)
		{
			return true;
		}
		else
		{
			for (int i = 0; i < Constant.url.size(); i++)
			{
				String temp = Constant.url.get(i).replace(".", ".do?thisAction=");
				if (url.indexOf(temp) >= 0)
					return true;
			}
			
			if (URI == null || URI.getAgent() == null)
			{
				try
				{
					response.sendRedirect(request.getContextPath() + "/login.jsp");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				return false;
			}
			else{
				for (int i = 0; i < Constant.certUrl.size(); i++)
				{
					String temp = Constant.certUrl.get(i).replace(".", ".do?thisAction=");
					if (url.indexOf(temp) >= 0 && URI.getAgent().getValidCertStatus()!=1)
					{
						try
						{							
							response.sendRedirect(request.getContextPath() + "/page/accessDeniedCert.jsp");
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						return false;
					}
				}
				
			}
				return true;
		}
	}
}
