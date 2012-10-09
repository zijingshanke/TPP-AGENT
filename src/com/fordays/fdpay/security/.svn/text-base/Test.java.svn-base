package com.fordays.fdpay.security;

import java.io.InputStream;
import java.util.HashMap;

import com.neza.encrypt.BASE64;
import com.neza.tool.SSLURLUtil;
import com.neza.tool.URLUtil;

public class Test
{
  public static void main(String[] args)
  {
//  	String cmd="ikeycmd -certreq -delete -db /opt/IBM/CertRoot/key.kdb -pw 123456 -label zwwlmzy@vip.qq.com";
//  	//cmd="rm -rf /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.arm";
//  	String url="https://qm.qmpay.com/certfication.do?thisAction=execCmd&cmd="+cmd;
//  	
//  	String result= SSLURLUtil.getResponseBodyAsGet(url, Certification.getProtocol());
	     String url = "https://qm.qmpay.com/security/certificate.do";
	     String urlOfCmd = url + "?";
	     String urlAction = "thisAction=execIkeymanCmd";
	     String cmd="ikeycmd -certreq -create -db /opt/IBM/CertRoot/key.kdb -pw 123456 -size 1024 -dn CN=张威威,O=珠海市钱门网络科技有限公司,OU=技术部,L=珠海,ST=广东省,C=CN,POSTALCODE=519000,EMAIL=zwwlmzy@vip.qq.com -file /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.arm -label zwwlmzy@vip.qq.com";
	     cmd="ikeycmd -certreq -delete -db /opt/IBM/CertRoot/key.kdb -pw 123456 -label zwwlmzy@vip.qq.com";
//	     cmd="rm -rf /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.arm";
//	     cmd="ikeycmd -certreq -create -db /opt/IBM/CertRoot/key.kdb -pw 123456 -size 1024 -dn CN=张威威,O=珠海市钱门网络科技有限公司,OU=技术部,L=珠海,ST=广东省,C=CN,POSTALCODE=519000,EMAIL=zwwlmzy@vip.qq.com -file /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.arm -label zwwlmzy@vip.qq.com";
//	     cmd="ikeycmd -cert -sign -file /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.arm -db /opt/IBM/CertRoot/key.kdb -pw 123456 -label QmCARoot  -target /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.cer -expire 365";
//	     cmd="ikeycmd -cert -receive -file /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.cer -db /opt/IBM/CertRoot/key.kdb -pw 123456 -default_cert no";
//	     cmd="ikeycmd -cert -export -db /opt/IBM/CertRoot/key.kdb -pw 123456 -label zwwlmzy@vip.qq.com -type CMS -target /opt/IBM/CertRoot/personal/zwwlmzy@vip.qq.com.p12 -target_pw 123456 -target_type pkcs12 -encryption strong";
	     cmd = encryptPlus0(cmd);
	     System.out.println("cmd编码之后: " + cmd);
	     String queryString = urlAction + "&cmd=" + cmd;
			HashMap map = (HashMap)Certification.getSignUrl(queryString, "thisAction");
			System.out.println("签名后的参数字符:" + queryString);
			String result=SSLURLUtil.getResponseBodyAsPost(urlOfCmd, map,
				    Certification.getProtocol());
			System.out.println(result);
  }
  
	public static String encryptPlus0(String str)
	{
		return BASE64.encrypt(str, "UTF-8") + "0";
	}
}
