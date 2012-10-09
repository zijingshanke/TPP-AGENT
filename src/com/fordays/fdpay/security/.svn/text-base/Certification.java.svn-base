package com.fordays.fdpay.security;

import javax.security.cert.Certificate;
import javax.security.cert.X509Certificate;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.protocol.Protocol;

import com.fordays.fdpay.agent.*;
import com.neza.encrypt.BASE64;
import com.neza.encrypt.MD5;
import com.neza.security.MyX509Certificate;
import com.neza.tool.SSLURLUtil;
import com.neza.tool.URLUtil;
import java.lang.Runtime;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.ibm.misc.BASE64Encoder;

public class Certification
{

	public static final String url = "https://qm.qmpay.com/security/certificate.do";
 	// "http://192.168.1.89:1234/fdpay/security/certificate.do";
 	// "http://qm.qmpay.com/security/certificate.do";
	// "http://192.168.1.87:3692/fdpay/security/certificate.do";

	public static final String urlOfCmd = url + "?";
	public static final String urlAction = "thisAction=execIkeymanCmd";
 	// "http://192.168.1.87:7080/fdpay/security/certificate.do?thisAction=execIkeymanCmd";
	public static String CERT_ROOT = "/opt/IBM/CertRoot";
//		public static String CERT_ROOT = "C:\\";

	public static String CERT_ROOT_PASSWORD = "123456";
	public static String CERT_ROOT_KDB = "key.kdb";
	public static String SIGN_KEY = "djsakdh3245sdasd";
	public static String CERT_MD5_KEY = "dssduhoas8832=p-c";

	public static String CERT_ROOT_COMMON = CERT_ROOT + "/common";
	public static String CERT_ROOT_IKEYCMD = "/opt/IBM/ikeyman/java/jre/bin/ikeycmd";
	// public static String finalString="";

	// private static String distingName =
	// "O=钱门,OU=钱门技术部,L=珠海,ST=广东省,C=CN,POSTALCODE=519000";

	public static boolean valid(HttpServletRequest request, Agent agent)
	{
		try
		{
			String certAttribute = "javax.servlet.request.X509Certificate";
			X509Certificate[] certificates = (X509Certificate[]) request
			    .getAttribute(certAttribute);
			if ("https".equals(request.getScheme()))
				if (certificates != null)
				{
					for (X509Certificate certificate : certificates)
					{
						if (certificate.getSerialNumber().equals(
						    Integer.parseInt(agent.getCertInfo().getSerialNo(), 16)))

						{ return true; }
					}
				}
				else
				{
					return false;
				}
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return false;
	}

	/**
	 * 个人证书请求 /opt/IBM/CertRoot/personal
	 * 
	 * @param certInfo
	 * @throws UnsupportedEncodingException
	 */
	public static String reqPersonCert(CertInfo certInfo) throws Exception
	{
		// String cmd = "ikeycmd -certreq -create -db /cert/qm_qmpay_com.kdb -pw
		// 123456 -size 1024 -dn CN="
		// + certInfo.getCertName()
		// + ",O=钱门,OU=钱门技术部,L=珠海,ST=广东省,C=CN,POSTALCODE=519000,EMAIL="
		// + certInfo.getEmail()
		// + " -file /cert/personal/"
		// + certInfo.getEmail()
		// + ".arm -label " + certInfo.getEmail() + "";
		String cmd = CERT_ROOT_IKEYCMD+" -certreq -create -db " + Certification.CERT_ROOT
		    + "/key.kdb -pw " + Certification.CERT_ROOT_PASSWORD
		    + " -size 1024 -dn CN=" + certInfo.getCertName()
		    + ",O=珠海市钱门网络科技有限公司,OU=技术部,L=珠海,ST=广东省,C=CN,POSTALCODE=519000,EMAIL="
		    + certInfo.getEmail() + " -file " + Certification.CERT_ROOT
		    + "/personal/" + certInfo.getEmail() + ".arm -label "
		    + certInfo.getEmail() + "";
		System.out.println("cmd编码之前: " + cmd);
		String finalString = "";
		cmd = encryptPlus0(cmd);
		cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
		System.out.println("cmd编码之后: " + cmd);
		String queryString = "";
		queryString = urlAction + "&cmd=" + cmd;
		HashMap map = (HashMap) getSignUrl(queryString, "thisAction");
		System.out.println("个人证书请求--拼接签名后的参数字符:" + finalString);
		// String result = URLUtil.getResponseBodyAsGet(urlOfCmd +
		// finalString).toString();
//		String result = URLUtil.getResponseBodyAsPost(urlOfCmd, map);
		String result = SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map,
			    Certification.getProtocol());

		// return CmdUtil.exec(cmd);
		result = BASE64.dencrypt(result, "UTF-8");
		return result;
	}

	/**
	 * 签署个人证书
	 * 
	 * @param certInfo
	 * @throws Exception
	 */
	public static String signPersonCert(CertInfo certInfo) throws Exception
	{
		// String cmd = "ikeycmd -cert -sign -file /cert/personal/"
		// + certInfo.getEmail()
		// + ".arm -db /cert/qm_qmpay_com.kdb -pw 123456 -label QmCARoot -target
		// /cert/personal/"
		// + certInfo.getEmail() + ".cer -expire 365";
		String cmd = CERT_ROOT_IKEYCMD+" -cert -sign -file " + Certification.CERT_ROOT
		    + "/personal/" + certInfo.getEmail() + ".arm -db "
		    + Certification.CERT_ROOT + "/key.kdb -pw " + CERT_ROOT_PASSWORD
		    + " -label QmCARoot  -target " + Certification.CERT_ROOT + "/personal/"
		    + certInfo.getEmail() + ".cer -expire 365";
		System.out.println(cmd);
		cmd = encryptPlus0(cmd);
		cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
		System.out.println("cmd编码之后: " + cmd);

		String queryString = "";
		queryString = urlAction + "&cmd=" + cmd;
		HashMap map = (HashMap) getSignUrl(queryString, "thisAction");

		System.out.println("签署个人证书--拼接签名后的参数字符:" + map);

//		String result = URLUtil.getResponseBodyAsPost(urlOfCmd, map);
		String result = SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map,
			    Certification.getProtocol());

		result = BASE64.dencrypt(result, "UTF-8");
		return result;
	}

	/**
	 * 获得证书序列号
	 * 
	 * @throws Exception
	 */
	public static String getSerialNumber(CertInfo certInfo) throws Exception
	{
		String email = encryptPlus0(certInfo.getEmail());
		email = java.net.URLEncoder.encode(email, "UTF-8"); // cmd URLEncoder
		System.out.println("cmd编码之后: " + email);

		String queryString = "";
		queryString = "thisAction=getSerialNumber&email=" + email;
		HashMap map = (HashMap) getSignUrl(queryString, "thisAction");
		System.out.println("邮箱：" + email);
//		String result = URLUtil.getResponseBodyAsPost(url + "?", map);
		String result = SSLURLUtil.getResponseBodyAsPost(url + "?", map,
			    Certification.getProtocol());

		// System.out.println(url + "?thisAction=getSerialNumber&email=" + email);
		System.out.println("获取证书序列号--拼接签名后的字符:" + url + "?" + queryString);
		result = BASE64.dencrypt(result, "UTF-8");
		return result;
	}

	/**
	 * 获得根证书
	 */
	public static String getRootCertInfo()
	{
		String queryString = "thisAction=getRootCertInfo" + "&test=test";
		HashMap map = (HashMap) getSignUrl(queryString, "thisAction");
		// String result = URLUtil.getResponseBodyAsPost(url + "?", map);
		String result = SSLURLUtil.getResponseBodyAsPost(url + "?", map,
		    Certification.getProtocol());

		System.out.println(url + "?thisAction=getRootCertInfo");
		return result;
	}

	/**
	 * 接收个人证书
	 * 
	 * @param certInfo
	 * @throws Exception
	 */
	public static String receivePersonCert(CertInfo certInfo) throws Exception
	{
		// String cmd = "ikeycmd -cert -receive -file /cert/personal/"
		// + certInfo.getEmail()
		// + ".cer -db /cert/qm_qmpay_com.kdb -pw 123456 -default_cert no";
		String cmd = CERT_ROOT_IKEYCMD+" -cert -receive -file " + Certification.CERT_ROOT
		    + "/personal/" + certInfo.getEmail() + ".cer -db "
		    + Certification.CERT_ROOT + "/key.kdb -pw "
		    + Certification.CERT_ROOT_PASSWORD + " -default_cert no";
		System.out.println(cmd);
		cmd = encryptPlus0(cmd);
		cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
		System.out.println("cmd编码之后: " + cmd);

		String queryString = "";
		queryString = urlAction + "&cmd=" + cmd;
		HashMap map = (HashMap) getSignUrl(queryString, "thisAction");
		System.out.println("接收个人证书--拼接签名后的参数字符:" + queryString);

//		String result = URLUtil.getResponseBodyAsPost(urlOfCmd, map);
		String result = SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map,
			    Certification.getProtocol());

		// return CmdUtil.exec(cmd);
		result = BASE64.dencrypt(result, "UTF-8");
		return result;
	}

	/**
	 * 导出p12个人证书
	 * 
	 * @param certInfo
	 * @return
	 * @throws Exception
	 */
	public static String getP12Cert(CertInfo certInfo) throws Exception
	{
		// String cmd = "ikeycmd -cert -export -db /cert/qm_qmpay_com.kdb -pw
		// 123456 -label "
		// + certInfo.getEmail()
		// + " -type CMS -target /cert/personal/"
		// + certInfo.getEmail()
		// + ".p12 -target_pw "
		// + certInfo.getPassword()
		// + " -target_type pkcs12 -encryption strong";
		String cmd = CERT_ROOT_IKEYCMD+" -cert -export -db " + Certification.CERT_ROOT
		    + "/key.kdb -pw " + Certification.CERT_ROOT_PASSWORD + " -label "
		    + certInfo.getEmail() + " -type CMS -target " + Certification.CERT_ROOT
		    + "/personal/" + certInfo.getEmail() + ".p12 -target_pw "
		    + certInfo.getPassword() + " -target_type pkcs12 -encryption strong";
		System.out.println(cmd);
		cmd = encryptPlus0(cmd);
		cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
		System.out.println("cmd编码之后: " + cmd);

		String queryString = "";
		queryString = urlAction + "&cmd=" + cmd;
		HashMap map = (HashMap) getSignUrl(queryString, "thisAction");
		System.out.println("导出p12个人证书--拼接签名后的参数字符:" + queryString);

//		String result = URLUtil.getResponseBodyAsPost(urlOfCmd, map);
		String result = SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map,
			    Certification.getProtocol());
		
		// String result = CmdUtil.exec(cmd);
		// File file = new File("/cert/personal/" + certInfo.getEmail() +
		// ".p12");
		// System.out.println("路径：===" + "/cert/personal/" + certInfo.getEmail()
		// + ".p12");
		// File file = new File("/cert/hitop.p12");
		result = BASE64.dencrypt(result, "UTF-8");
		return result;
	}

	/**
	 * 删除个人证书文件
	 * 
	 * @param certInfo
	 * @throws Exception
	 */
	public static String deletePersonCert(CertInfo certInfo, String certExName)
	    throws Exception
	{
		StringBuffer result = new StringBuffer();
		String cmd = "";
		String queryString = "";

		if (certExName.equalsIgnoreCase("arm"))
		{
			cmd = CERT_ROOT_IKEYCMD+" -certreq -delete -db " + Certification.CERT_ROOT

			+ "/key.kdb -pw " + Certification.CERT_ROOT_PASSWORD + " -label "
			    + certInfo.getEmail() + "";

			cmd = encryptPlus0(cmd);
			cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
			System.out.println("cmd编码之后: " + cmd);

			queryString = urlAction + "&cmd=" + cmd;
			HashMap map1 = (HashMap) getSignUrl(queryString, "thisAction");
			System.out.println("删除arm物理证书--拼接签名后的参数字符:" + queryString);

//			result.append(URLUtil.getResponseBodyAsPost(urlOfCmd, map1));
			result.append(SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map1,
				    Certification.getProtocol()));

			cmd = "rm -rf " + Certification.CERT_ROOT + "/personal/"
			    + certInfo.getEmail() + ".arm";
			cmd = encryptPlus0(cmd);
			cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
			System.out.println("cmd编码之后: " + cmd);

			queryString = urlAction + "&cmd=" + cmd;
			HashMap map2 = (HashMap) getSignUrl(queryString, "thisAction");
			System.out.println("删除arm证书--拼接签名后的参数字符:" + queryString);

//			result.append(URLUtil.getResponseBodyAsPost(urlOfCmd, map2));
			result.append(SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map2,
				    Certification.getProtocol()));

			
		}
		else if (certExName.equalsIgnoreCase("cer"))
		{
			cmd = CERT_ROOT_IKEYCMD+" -cert -delete -db " + Certification.CERT_ROOT
			    + "/key.kdb -pw " + Certification.CERT_ROOT_PASSWORD + " -label "
			    + certInfo.getEmail() + "";
			cmd = encryptPlus0(cmd);
			cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
			System.out.println("cmd编码之后: " + cmd);

			queryString = urlAction + "&cmd=" + cmd;
			HashMap map1 = (HashMap) getSignUrl(queryString, "thisAction");
			System.out.println("删除cer物理证书--拼接签名后的参数字符:" + queryString);

//			result.append(URLUtil.getResponseBodyAsPost(urlOfCmd, map1));
			result.append(SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map1,
				    Certification.getProtocol()));

			cmd = "rm -rf " + Certification.CERT_ROOT + "/personal/"
			    + certInfo.getEmail() + ".cer ";
			cmd = encryptPlus0(cmd);
			cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
			System.out.println("cmd编码之后: " + cmd);

			queryString = urlAction + "&cmd=" + cmd;
			HashMap map2 = (HashMap) getSignUrl(queryString, "thisAction");
			System.out.println("删除cer证书--拼接签名后的参数字符:" + queryString);

//			result.append(URLUtil.getResponseBodyAsPost(urlOfCmd, map2));
			result.append(SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map2,
				    Certification.getProtocol()));
			
		}
		else if (certExName.equalsIgnoreCase("p12"))
		{
			cmd = "rm -rf " + Certification.CERT_ROOT + "/personal/"
			    + certInfo.getEmail() + ".p12";
			cmd = encryptPlus0(cmd);
			cmd = java.net.URLEncoder.encode(cmd, "UTF-8"); // cmd URLEncoder
			System.out.println("cmd编码之后: " + cmd);

			queryString = urlAction + "&cmd=" + cmd;
			HashMap map = (HashMap) getSignUrl(queryString, "thisAction");
			System.out.println("删除p12证书--拼接签名后的参数字符:" + queryString);

//			result.append(URLUtil.getResponseBodyAsPost(urlOfCmd, map));
			result.append(SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map,
				    Certification.getProtocol()));
			
		}

		return result.toString();

	}

	private static String sign(String url)
	{
		return url;
	}

	/**
	 * 导入证书
	 * 
	 * @param cmd
	 */
	public static void importCertificate(String cmd)
	{
		Runtime run = Runtime.getRuntime();// 返回与当前 Java 应用程序相关的运行程序
		try
		{
			Process p = run.exec(cmd);// 启动另一个进程来执行命令
			BufferedInputStream in = new BufferedInputStream(p.getInputStream());
			BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = inBr.readLine()) != null)
				// 获得命令执行后在控制台的输出信息
				System.out.println(lineStr); // 打印输出信息
			// 检查命令是否执行失败。
			if (p.waitFor() != 0)
			{
				if (p.exitValue() == 1) // p.exitValue()==0表示正常结束，1：非正常结束
					System.err.println("命令执行失败!");

			}
			inBr.close();
			in.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void _main(String[] args)
	{
		String url = "http://qm.qmpay.com/security/certificate.do?thisAction=execIkeymanCmd";

		String cmd = "rm -rf " + Certification.CERT_ROOT + "/personal/"
		    + "hl_527@yeah.net.cer hl_527@yeah.net.p12";

		// cmd = "ikeycmd -cert -delete -db " + Certification.CERT_ROOT + "/key.kdb
		// -pw 123456 -label Ling9514@126.com";
		cmd =CERT_ROOT_IKEYCMD+" -cert -list -db " + Certification.CERT_ROOT
		    + "/key.kdb -pw 123456";
		cmd =CERT_ROOT_IKEYCMD+" -cert -details -db " + Certification.CERT_ROOT
		    + "/key.kdb -pw 123456 -label Ling9514@126.com";
		// cmd = "ikeycmd -certreq -delete -db " + Certification.CERT_ROOT +
		// "/key.kdb -pw 123456 -label Ling9514@126.com";

		cmd = encryptPlus0(cmd);
		String quertString = "&cmd=" + cmd;

		String result = URLUtil.getResponseBodyAsGet(url + "&cmd=" + cmd)
		    .toString();
		result = BASE64.dencrypt(result, "UTF-8");
		System.out.print(cmd + "    \n");
		System.out.print(result + "    \n");
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		// System.out.println("根证书信息：");
		// String rootcertInfo=Certification.getRootCertInfo();
		// System.out.println("根证书信息："+rootcertInfo);

		// String email = BASE64.encrypt("zwwlmzy@vip.qq.com", "UTF-8");
		// System.out.println("邮箱：" + email);
		// String result = URLUtil.getResponseBodyAsGet(
		// url + "?thisAction=getSerialNumber&email=" + email).toString();
		// System.out.println(url + "?thisAction=getSerialNumber&email=" + email);
		// result = BASE64.dencrypt(result, "UTF-8");
		// System.out.println("后台返回信息:"+result);

		// CertInfo certInfo = new CertInfo();
		// certInfo.setEmail("ling9514@126.com");
		// certInfo.setPassword("123456");
		// certInfo.setCertName("郭玲");
		// Certification.deletePersonCert(certInfo,"arm");
		// Certification.deletePersonCert(certInfo,"cer");
		// Certification.deletePersonCert(certInfo,"p12");
		// Certification.reqPersonCert(certInfo);
		// Certification.signPersonCert(certInfo);
		// Certification.receivePersonCert(certInfo);
		// Certification.getP12Cert(certInfo);
		// String result = Certification.getSerialNumber(certInfo);
		// System.out.println("result=" + result);

		// CertInfo certInfo=new CertInfo ();
		// certInfo.setEmail("Ling9514@126.com");
		// String result= Certification.getSerialNumber(certInfo);
		// System.out.println("result="+result);
		/*
		 * CertificateFactory cf=CertificateFactory.getInstance("X.509");
		 * InputStream in=new FileInputStream("E:\\test\\hl_527%40yeah.net.p12");
		 * Certificate c=cf.generateCertificate(in); in.close(); String
		 * s=c.toString( ); System.out.println("未加密："+s); s=BASE64.encrypt(s);
		 * //s=BASE64.dencrypt(s,"UTF-8"); System.out.println("BASE64加密后："+s);
		 */
		// String url="a=4545454&b=4564654&c=14564654";
		// Certification.getSignUrl(url);
		// public static String getResponseBodyAsPost(String strURL, NameValuePair[]
		// nvp)

		// NameValuePair[] nvp=new NameValuePair[3];
		// nvp[0]=new NameValuePair("thisAction", "execIkeymanCmd0");
		// nvp[1]=new NameValuePair("cmd", "send");
		// String a="http://qm.qmpay.com/security/certificate.do";
		// public static String getResponseBodyAsPost(String strURL,
		// HashMap<String, String> params)
		// String result=URLUtil.getResponseBodyAsPost(url,nvp);
		// ?thisAction=execIkeymanCmd0&cmd=aWtleWNtZCAtY2VydHJlcSAtZGVsZXRlIC1kYiAvb3B0L0lCTS9DZXJ0Um9vdC9rZXkua2RiIC1wdyAxMjM0NTYgLWxhYmVsIGxpbkBxcS5jb200&cmd=cm0gLXJmIC9vcHQvSUJNL0NlcnRSb290L3BlcnNvbmFsL2xpbkBxcS5jb20uYXJt0&cmd=aWtleWNtZCAtY2VydCAtZGVsZXRlIC1kYiAvb3B0L0lCTS9DZXJ0Um9vdC9rZXkua2RiIC1wdyAxMjM0NTYgLWxhYmVsIGxpbkBxcS5jb200&cmd=cm0gLXJmIC9vcHQvSUJNL0NlcnRSb290L3BlcnNvbmFsL2xpbkBxcS5jb20uY2VyIA0&cmd=cm0gLXJmIC9vcHQvSUJNL0NlcnRSb290L3BlcnNvbmFsL2xpbkBxcS5jb20ucDEy0&sign=29c39e2594e2101e7f3097cf7d723b31";
		// System.out.println(a.length());
		//---------------------------------------------------------------------
//		String queryString = "thisAction=downloadP12&email=Mjc2NjI4QHFxLmNvbQ%3D%3D";
//		String email = BASE64.encrypt("276628@qq.com", "UTF-8");
//		email = java.net.URLEncoder.encode(email, "UTF-8");
//		System.out.println(email);
//		queryString = Certification.getSignString(queryString, "thisAction");
//		System.out.println("签名后字符串:" + queryString);
		//---------------------------------------------------------------------
//		String keyStorePath = CERT_ROOT_COMMON + "keystore";
//		java.io.File file= new java.io.File(keyStorePath);
//		System.out.println(file.exists());
	}

	/**
	 * 签名请求URL的参数
	 * 
	 * @param url
	 * @param param
	 * @return signQueryString
	 */

	public static Map getSignUrl(String url, String param)
	{

		if (url == null || url == "") { return null; }
		String[] params = url.split("&");
		// 循环出根据&分割出来的数
		for (int i = 0; i < params.length; i++)
		{
			System.out.println(i + "：" + params[i]);
		}
		Map map = new HashMap();
		List list = new ArrayList();
		String[] element = null;
		StringBuffer sb = new StringBuffer();
		for (String temp : params)
		{
			if (temp == null)
			{
				continue;
			}
			// 进一步分割
			element = temp.split("=");

			if (element == null || element.length != 2)
			{
				continue;
			}
			// 循环出根据=分割出来的数
			// for(int i=0;i<element.length;i++){
			// System.out.println(i+"："+element[i]);
			// }
			// if(element[0].equals(param)){
			// map.put(element[0], element[1]);
			// }else{
			map.put(element[0], element[1]);
			// }
			list.add(element[0]);
		}
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++)
		{
			sb.append(list.get(i)).append("=").append(map.get(list.get(i))).append(
			    "&");
		}

		System.out.println(sb.toString());
		String singn_string = sb.toString();
		singn_string = singn_string.substring(0, singn_string.length() - 1);
		System.out.println("singn_string:" + singn_string);
		String signQueryString = sb.toString() + "sign="
		    + MD5.encrypt(singn_string + Certification.SIGN_KEY);
		map.put("sign", MD5.encrypt(singn_string + Certification.SIGN_KEY));
		System.out.println(signQueryString);
		// System.out.println(signQueryString);
		// while(map.entrySet().iterator().hasNext()){
		// Map.Entry<String,String>
		// temp=(Map.Entry<String,String>)map.entrySet().iterator().next();
		// System.out.println(temp.getKey()+"="+temp.getValue());
		// }
		//		
		// for(Iterator it=map.entrySet().iterator();it.hasNext();){
		// Map.Entry<String,String> temp=(Map.Entry<String,String>)it.next();
		// System.out.println(temp.getKey()+"="+temp.getValue());
		// }
		return map;

	}

	public static String getSignString(String url, String param)
	{

		if (url == null || url == "") { return null; }
		String[] params = url.split("&");
		// 循环出根据&分割出来的数
		for (int i = 0; i < params.length; i++)
		{
			System.out.println(i + "：" + params[i]);
		}
		Map map = new HashMap();
		List list = new ArrayList();
		String[] element = null;
		StringBuffer sb = new StringBuffer();
		for (String temp : params)
		{
			if (temp == null)
			{
				continue;
			}
			// 进一步分割

			element = temp.split("=");
			if (element == null || element.length != 2)
			{

				continue;
			}
			// 循环出根据=分割出来的数
			// for(int i=0;i<element.length;i++){
			// System.out.println(i+"："+element[i]);
			// }
			// if(element[0].equals(param)){
			map.put(element[0], element[1]);
			// }else{
			// System.out.println("   || "+element[1]);
			// map.put(element[0], element[1]+"0");
			// }
			list.add(element[0]);
		}
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++)
		{
			sb.append(list.get(i)).append("=").append(map.get(list.get(i))).append(
			    "&");
		}

		String singn_string = sb.toString();
		singn_string = singn_string.substring(0, singn_string.length() - 1);
		System.out.println("签名字符串:" + singn_string);
		String signQueryString = sb.toString() + "sign="
		    + MD5.encrypt(singn_string + Certification.SIGN_KEY);
		map.put("sign", MD5.encrypt(singn_string + Certification.SIGN_KEY));
		return signQueryString;

	}

	public static String getQueryString(Map map)
	{
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		for (Iterator it = map.entrySet().iterator(); it.hasNext();)
		{
			Map.Entry<String, String> temp = (Map.Entry<String, String>) it.next();
			list.add(temp.getKey());
		}
		Collections.sort(list);
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
		{
			String[] map_values = (String[]) (map.get(list.get(i)));
			if (!list.get(i).equals("sign"))
			{
				sb.append(list.get(i)).append("=").append(map_values[0].toString())
				    .append("&");
			}
			else
			{
				sb2.append(list.get(i)).append("=").append(map_values[0].toString())
				    .append("&");
			}
		}
		sb.append(sb2);
		System.out.println("后台url queryString::  "
		    + sb.substring(0, sb.length() - 1));
		return sb.substring(0, sb.length() - 1);
	}

	public static boolean validateSignUrl(String urlQueryString)
	{
		if (urlQueryString == null || urlQueryString == "") { return false; }
		System.out.println(urlQueryString);
		String[] items = urlQueryString.split("&sign=");
		if (items.length != 2) { return false; }
		String queryString = items[0];
		String sign = items[1];
		System.out.println("签名string:" + queryString);
		String temp_sign = MD5.encrypt(queryString + Certification.SIGN_KEY);
		System.out.println("签名sign:" + sign);
		System.out.println(temp_sign);
		if (sign.equals(temp_sign))
		{
			System.out.println("验证签名成功!");
			return true;

		}
		System.out.println("验证签名失败!");
		return false;
	}

	public static String encryptPlus0(String str)
	{
		return BASE64.encrypt(str, "UTF-8") + "0";
	}

	public static Protocol getProtocol()
	{
		String keyStorePath = CERT_ROOT_COMMON + "/keystore";
//		 keyStorePath = "C:\\common\\keystore";

		String keypass = "1111111";
		String trustStorePath = CERT_ROOT_COMMON + "/qmTrust";
//		 trustStorePath = "C:\\common\\qmTrust";

		String trustpass = "changeit";
		String keyAlgorithm = "IBMX509";
		String trustAlgorithm = "IBMPKIX";
		String provider = "IBMJSSE";
//		keyAlgorithm = "SUNX509";
//		trustAlgorithm = "SUNX509";
//		provider = "SunJSSE";
		return MyX509Certificate.getProtocol(keyStorePath, keypass, trustStorePath,
		    trustpass, keyAlgorithm, trustAlgorithm, provider);

	}

}
