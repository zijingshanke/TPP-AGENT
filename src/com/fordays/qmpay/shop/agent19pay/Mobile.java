package com.fordays.qmpay.shop.agent19pay;

import com.neza.encrypt.MD5;

import sun.awt.image.ShortInterleavedRaster;

/**
 * 19pay手机充值接口提交与返回的数据类型
 */
public class Mobile extends org.apache.struts.action.ActionForm {

	/**
	 * 提交请求的数据
	 */
	private String isptype;// 运营商名称(移动，联通,电信)
	private String provincename;// 省份
	private String citycode;// 城市代码
	private String detail;// 描述
	private int prodContent;
	private String prodType;
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public int getProdContent() {
		return prodContent;
	}
	public void setProdContent(int prodContent) {
		this.prodContent = prodContent;
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
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

}
