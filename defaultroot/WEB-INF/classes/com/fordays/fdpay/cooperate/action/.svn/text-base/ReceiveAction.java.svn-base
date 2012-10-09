package com.fordays.fdpay.cooperate.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;



public class ReceiveAction extends DispatchAction {

	public ActionForward unspecified(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		 String trade_status=request.getParameter("trade_status");
		 PrintWriter out = response.getWriter();
		 try{
			 if(trade_status!=null&&trade_status.equals(com.fordays.fdpay.cooperate.Constant.TRADE_SUCCESS))
				{
				 out = response.getWriter();
				 out.write("SUCCESS");
				 out.close();
				 out = null;
				}
			 else
			 {
				 out = response.getWriter();
				 out.write("FAIL");
				 out.close();
				 out = null;
			 }
		 }
	     catch(Exception e){
	    	e.printStackTrace();
	     }
		return null;
		 
	}

}