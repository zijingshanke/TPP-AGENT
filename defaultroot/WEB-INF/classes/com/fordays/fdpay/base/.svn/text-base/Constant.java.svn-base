package com.fordays.fdpay.base;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.Coterie;

public class Constant
{
	public static String SERVLET_PATH = "";
	public static String SERVLET_MAIN_PATH = "";
	public static String SERVLET_XML_PATH = "";
	public static String CONNECTION_STRING_NAME = "";
	public static String PROJECT_UPLOAD_PATH = "";

	public static Agent platAgent=null;
	public static Agent platChargeAgent=null;
	public static Agent platRateAgent=null;	
	public static ArrayList<Coterie> coteries=null;	
	public static ArrayList<String> url=null;
	public static ArrayList<String> certUrl=null;
	
	/**
	 * 商户状态
	 */
//已注册
	public static Long REGISTER_PASS=new Long(0);
	//已激活
	public static Long ACTIVATE_PASS=new Long(1);
	//认证通过
	public static Long CERT_PASS=new Long(2);
	//冻结
	public static Long FROZEN=new Long(3);
	//注销
	public static Long WRITEOFF=new Long(4);
	public static Long LOGINLOG_BACKGROUND=new Long(0);
	public static Long LOGINLOG_CLIENT =new Long(1);
		
	public static int toInt(String value)
	{
		int temp = 0;
		try
		{
			temp = Integer.parseInt(value);
		}
		catch (Exception ex)
		{
			temp = 0;
			 
		}
		return temp;
	}

	public static Integer toInteger(String value)
	{
		Integer temp;
		try
		{
			temp = new Integer(value);
		}
		catch (Exception ex)
		{
			temp = new Integer(0);
			 
		}
		return temp;
	}

	public static float toFloat(String value)
	{
		float temp = 0.00f;
		try
		{
			temp = Float.parseFloat(value);
		}
		catch (Exception ex)
		{
			temp = 0.00f;
			 
		}
		return temp;
	}

	public static int getRandomNum()
	{
		double temp = Math.random();
		return (int) (temp * 100);

	}

	public final static java.sql.Timestamp string2Time(String dateString)
			throws java.text.ParseException
	{
		DateFormat dateFormat;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss.SSS", Locale.ENGLISH);
		// dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
		dateFormat.setLenient(false);
		java.util.Date timeDate = dateFormat.parse(dateString);
		java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
		return dateTime;
	}


	public void setUrl(ArrayList<String> url)
  {
		Constant.url = url;
  }

	public void setCertUrl(ArrayList<String> certUrl)
  {
  	Constant.certUrl = certUrl;
  }





}