package com.fordays.fdpay.bank.icbc;

/*******************************************************************************
 * 工商银行电话银行B2c
 * 
 ******************************************************************************/
public class IcbcTelB2CcmdToBank extends com.fordays.fdpay.base.Bank {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String interfaceName = "";// 接口名称
	private String interfaceVersion = "";// 接口版本号
	private String orderid = ""; // 订单号
	private String orderDate = ""; // 订单提交时间
	private String cutomerTel = ""; // 客户电话号码
	private String merID = ""; // 商户代码
	private String merAcct = ""; // 商城账号
	private String amount = ""; // 订单金额
	private String curType = ""; // 支付币种
	private String goodsName = ""; // 商品名称
	private String goodsNum = ""; // 商品数量
	private String merSignMsg = ""; // 订单签名数据
	private String merCert = ""; // 商城证书公钥
	private String remark1 = ""; // 备注字段1
	private String remark2 = "";// 备注字段2

	public IcbcTelB2CcmdToBank() {
		init();
	}

	private void init() {
		this.interfaceName = "ICBC_TEL_B2C_SUBMIT";
		this.interfaceVersion = "1.0.0.0";
		this.curType = "001";
		this.cutomerTel = "123456";
		this.goodsName = "充值";
		this.goodsNum = "1";
		this.merAcct = "商户帐号";
		this.merCert = "商户公钥";
		this.merID = "商户代码";
	}

	public String getIcbcurl() {
		String icbcurl = "";
		// PublicKey pubkey = null;// 商户公钥
		// String prefix =
		// "https://b2c.icbc.com.cn/servlet/ICBCINBSEBusinessServlet?";
		//		
		// // 组合参数，MD5运算
		// String orderinfo = "interfaceName=" + this.interfaceName
		// + "&interfaceVersion=" + this.interfaceVersion + "&orderID="
		// + this.orderid + "&orderDate=" + this.orderDate
		// + "&cutomerTel=" + this.cutomerTel + "&merID=" + this.merID
		// + "&merAcct=" + this.merAcct + "&amount=" + this.amount
		// + "&curType=" + this.curType + "&goodsName=" + this.goodsName
		// + "&goodsNum=" + this.goodsNum;
		//
		// String strSrc = this.interfaceName + this.interfaceVersion
		// + this.getOrderid() + this.getOrderDate() + this.cutomerTel
		// + this.merID + this.merAcct + this.amount + this.curType
		// + this.goodsName + this.goodsNum;
		//
		// // --------------------
		// String signMsg = strSrc + "";//
		// //
		// // -----------证书签名
		// //
		// merSignMsg = BASE64.encrypt(strSrc);// base64
		// // ----
		//
		// icbcurl = prefix + orderinfo + "&merSignMsg=" + merSignMsg
		// + "&merCert=" + merCert + "&remark1=" + remark1 + "&remark2="
		// + remark2;
		return icbcurl;
	}

	public String getMerCert() {
		// 1、二进制方式读取证书公钥文件
		// 2、base64编码
		merCert = "ddddd";
		return merCert;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	public String getCutomerTel() {
		return cutomerTel;
	}

	public void setCutomerTel(String cutomerTel) {
		this.cutomerTel = cutomerTel;
	}

	public String getMerID() {
		return merID;
	}

	public void setMerID(String merID) {
		this.merID = merID;
	}

	public String getMerAcct() {
		return merAcct;
	}

	public void setMerAcct(String merAcct) {
		this.merAcct = merAcct;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(String goodsNum) {
		this.goodsNum = goodsNum;
	}

	public void setMerSignMsg(String merSignMsg) {
		this.merSignMsg = merSignMsg;
	}

	public String getMerSignMsg() {
		return merSignMsg;
	}

	public void setMerCert(String merCert) {
		this.merCert = merCert;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
}
