<%@page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.fordays.fdpay.bank.icbc.cbst.module.*"%>
<%@page import="com.fordays.fdpay.bank.icbc.cbst.biz.IcbcCbstBizImp"%>
<%@page import="com.fordays.fdpay.bank.LogUtil"%>
<%@page import="com.fordays.fdpay.bank.icbc.cbst.util.CbstLogUtil"%>

<%
	out.clear();
	//GroupHeader header = new GroupHeader("20003");
	//out.print("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>");
	//out.println(header.getGroupHeaderXML_Test());
	
	IcbcCbstBizImp biz=new IcbcCbstBizImp();
	out.print("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>");
	String xmlcon=biz.getBussinessXML("20003");
	
	LogUtil myLog=new CbstLogUtil(false,false,biz.getClass(),"");
	
	myLog.info(xmlcon);
	
	out.println(xmlcon);
%>
