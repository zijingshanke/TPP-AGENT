package com.fordays.qmpay.shop.agent19pay;

import java.math.BigDecimal;

import com.neza.encrypt.MD5;

import sun.awt.image.ShortInterleavedRaster;

/**
 * 19pay手机充值接口提交与返回的数据类型
 * */
public class Agent19pay extends org.apache.struts.action.ActionForm{

	
	/**
	 * 提交请求的数据
	 * */
	private String prodid;//产品ID
	private String agentid;//代理商ID
	private String backUrl="";//直冲返回的URL
	private int returntype;//1表示post返回 2表示返回XML信息
	private String orderid;//代理商订单号
	private String mobilenum;//充值手机号码
	private String mobilenum2;//临时充值手机号码
	private String source;//代理商来源
	private String mark="";//预留字段
	private String verifystring;//验证摘要串
	private String merchantKey="";
	private int command;
	private int prodContent;
	private String isptype;// 运营商名称(移动，联通,电信)
	private String provincename;// 省份
	private BigDecimal price;
	private Long qmagentId;
	private int numType;//号码类型(1:手机,2:小灵通,3:固定电话)
	public static int TYPE_1=1;//移动电话
	public static int TYPE_2=2;//小灵通
	public static int TYPE_3=3;//固定电话
	public String prodType;//号码类型
	public static final String QMACCOUNT="qmpayyz@126.com";//钱门收款帐号
	public static final float rate=0.002f;//手续费汇率
	public Long getQmagentId() {
		return qmagentId;
	}

	public void setQmagentId(Long qmagentId) {
		this.qmagentId = qmagentId;
	}

	public String getIsptype() {
		return isptype;
	}

	public void setIsptype(String isptype) {
		this.isptype = isptype;
	}

	public String getProvincename() {
		return provincename;
	}

	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}

	public Agent19pay() {
		super();
		this.agentid="qmpay@qmpay.com";
		this.merchantKey="50lkq58k1pikckr282jphzv6uiorcg57rdijh00jgvdtnb1m59zaljeb2m77r0m7wfd410onfzyhow0imfecqlb4we7wbgosr6wnyefvplhl0nk2d07x9vuecyam0oqn";
		this.source="esales";
	}
	
	public String getProductURL(){
		String url="http://huafei.19pay.cn/directProduct.do?";
//		String url="http://114.255.7.254:8086/directProduct.do?";		
		String verifystring="";
		String MD5Content="agentid="+this.agentid;
		MD5Content+="&source="+this.source;
		url+=MD5Content;
		MD5Content+="&merchantKey="+this.merchantKey;
		verifystring=MD5.encrypt(MD5Content);
		url+="&verifystring="+verifystring;
		return url;
	}
	
	public String getFillURL(){
		String url="http://huafei.19pay.cn/directFill.do?";
//		String url="http://114.255.7.254:8086/directFill.do?";		
		String verifystring="";
		String MD5Content="prodid="+this.prodid;
		MD5Content+="&agentid="+this.agentid;
		MD5Content+="&backurl="+this.backUrl;
		MD5Content+="&returntype="+this.returntype;
		MD5Content+="&orderid="+this.orderid;
		MD5Content+="&mobilenum="+this.mobilenum;
		MD5Content+="&source="+this.source;
		MD5Content+="&mark="+this.mark;		
		url+=MD5Content;		
		MD5Content+="&merchantKey="+this.merchantKey;
		System.out.println("加密:"+MD5Content);
		verifystring=MD5.encrypt(MD5Content);
		url+="&verifystring="+verifystring;
		return url;
	}
	
	public String getAccsegmentURL(){
		String url="http://huafei.19pay.cn/accsegment.do?";
//		String url="http://114.255.7.254:8086/accsegment.do?";
		String verifystring="";
		String MD5Content="agentid="+this.agentid;		
		MD5Content+="&source="+this.source;
		MD5Content+="&mobilenum="+this.mobilenum;
		url+=MD5Content;
		MD5Content+="&merchantKey="+this.merchantKey;
		verifystring=MD5.encrypt(MD5Content);
		url+="&verifystring="+verifystring;
		return url;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public int getReturntype() {
		return returntype;
	}

	public void setReturntype(int returntype) {
		this.returntype = returntype;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getMobilenum() {
		return mobilenum;
	}

	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getVerifystring() {
		return verifystring;
	}

	public void setVerifystring(String verifystring) {
		this.verifystring = verifystring;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getMobilenum2() {
		return mobilenum2;
	}

	public void setMobilenum2(String mobilenum2) {
		this.mobilenum2 = mobilenum2;
	}

	public int getProdContent() {
		return prodContent;
	}

	public void setProdContent(int prodContent) {
		this.prodContent = prodContent;
	}

	public BigDecimal getPrice() {
		//return new BigDecimal(1);
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public static void main(String[] s){
		Agent19pay a = new Agent19pay();
		System.out.println(a.getProductURL());
	}

	public String getProdType() {
		if(this.numType==Agent19pay.TYPE_1){
			return "移动电话";
		}else if(this.numType==Agent19pay.TYPE_2){
			return "小灵通";
		}else if(this.numType==Agent19pay.TYPE_3){
			return "固定电话";
		}
		return null;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public int getNumType() {
		return numType;
	}

	public void setNumType(int numType) {
		this.numType = numType;
	}


}
