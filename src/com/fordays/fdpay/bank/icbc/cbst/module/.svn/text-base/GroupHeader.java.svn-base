package com.fordays.fdpay.bank.icbc.cbst.module;

/**
 * 消息包头
 */
public class GroupHeader {

	/**
	 * 要素 注释顺序：要素名称,数据类型备注,XMLTag
	 */
	private Version version = new Version();// 版本,<Version>
	private SystemType systemType = new SystemType();// 应用系统类型,枚举,<SysType>
	private BusinessCode businessCode = new BusinessCode();// 业务功能号,枚举,<BusCd>
	private TradeSource tradeSource = new TradeSource();// 交易发起方,枚举,<TradSrc>
	private Sender sender = new Sender();// 发送机构,组件,<Sender>
	private Recver recver = new Recver();// 接收机构,组件,<Recver>
	private TransactionDate tradeDate = new TransactionDate();// 交易日期,<Date>
	private TransactionTime tradeTime = new TransactionTime();// 交易时间,<Time>

	/**
	 * 签名时用
	 */
	private String buscode = "";// 业务功能码
	private String sponsor = "";// 发起方
	private String sponsorInstId = "";// 发送机构机构标识
	private String inceptInstId = "";// 接收机构机构标识
	private String tradDate = "";// 交易日期
	private String headPlainText = "";// 部分签名原文

	public GroupHeader() {
		init();
	}

	public GroupHeader(String buscode) {
		init(buscode);
	}

	public void init() {

	}

	public void init(String buscode) {
		businessCode.setCode(buscode);
	}

	public String getGroupHeaderXML_Test() {
		// <GrpHdr>
		// <Version>1.0.0</Version>
		// <SysType>8</SysType>
		// <BusCd>20001</BusCd>
		// <TradSrc>F</TradSrc>
		// <Sender>
		// <InstId>10800000</InstId>
		// </Sender>
		// <Recver>
		// <InstId>1020000</InstId>
		// </Recver>
		// <Date>20070101</Date>
		// <Time>120000</Time>
		// </GrpHdr>

		StringBuffer xmlCon = new StringBuffer("<GrpHdr>");
		xmlCon.append(version.getVersionXML());
		xmlCon.append(systemType.getSystemTypeXML());
		xmlCon.append(businessCode.getBusinessCodeXML());
		xmlCon.append(tradeSource.getTradeSourceXML());
		xmlCon.append(sender.getSenderXML());
		xmlCon.append(recver.getRecverXML());
		xmlCon.append(tradeDate.getTradDateXML());
		xmlCon.append(tradeTime.getTradTimeXML());
		xmlCon.append("</GrpHdr>");

		return xmlCon.toString();
	}

	public StringBuffer getGroupHeaderXML() {
		StringBuffer xmlCon = new StringBuffer("<GrpHdr>");
		xmlCon.append(version.getVersionXML());
		xmlCon.append(systemType.getSystemTypeXML());
		xmlCon.append(businessCode.getBusinessCodeXML());
		xmlCon.append(tradeSource.getTradeSourceXML());
		xmlCon.append(sender.getSenderXML());
		xmlCon.append(recver.getRecverXML());
		xmlCon.append(tradeDate.getTradDateXML());
		xmlCon.append(tradeTime.getTradTimeXML());
		xmlCon.append("</GrpHdr>");

		// -----签名项
		buscode = businessCode.getCode();
		sponsor = tradeSource.getSponsor();
		sponsorInstId = sender.getInstId();
		inceptInstId = recver.getInstId();
		tradDate = tradeDate.getTradDate();
		// ------组装部分签名原文
		headPlainText = buscode + "|" + sponsor + "|" + sponsorInstId + "|"
				+ inceptInstId + "|" + tradDate + "|";

		return xmlCon;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public SystemType getSystemType() {
		return systemType;
	}

	public void setSystemType(SystemType systemType) {
		this.systemType = systemType;
	}

	public BusinessCode getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(BusinessCode businessCode) {
		this.businessCode = businessCode;
	}

	public TradeSource getTradeSource() {
		return tradeSource;
	}

	public void setTradeSource(TradeSource tradeSource) {
		this.tradeSource = tradeSource;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public Recver getRecver() {
		return recver;
	}

	public void setRecver(Recver recver) {
		this.recver = recver;
	}

	public TransactionDate getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(TransactionDate tradeDate) {
		this.tradeDate = tradeDate;
	}

	public TransactionTime getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(TransactionTime tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getBuscode() {
		return buscode;
	}

	public void setBuscode(String buscode) {
		this.buscode = buscode;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getSponsorInstId() {
		return sponsorInstId;
	}

	public void setSponsorInstId(String sponsorInstId) {
		this.sponsorInstId = sponsorInstId;
	}

	public String getInceptInstId() {
		return inceptInstId;
	}

	public void setInceptInstId(String inceptInstId) {
		this.inceptInstId = inceptInstId;
	}

	public String getTradDate() {
		return tradDate;
	}

	public void setTradDate(String tradDate) {
		this.tradDate = tradDate;
	}

	public String getHeadPlainText() {
		return headPlainText;
	}

	public void setHeadPlainText(String headPlainText) {
		this.headPlainText = headPlainText;
	}
}
