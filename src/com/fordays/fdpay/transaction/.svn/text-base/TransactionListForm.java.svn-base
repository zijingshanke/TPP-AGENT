package com.fordays.fdpay.transaction;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.fordays.fdpay.agent.Agent;
import com.neza.base.ListActionForm;

public class TransactionListForm extends ListActionForm {
	private int transactionId;
	private int agentId;
	private int agentType = 0;
	private String agentName = "";
	private String no = "";
	private String beginDate;
	private String endDate;
	private String dateType;
	private String searchAgentName;
	private long orderId;
	private String searchKey;
	private String status;
	private String paytype;
	private Agent agent;
	private String searchOrderNo;
	private String searchOutOrderNo;
	private String searchShopName;
	private BigDecimal searchBeginMoney = new BigDecimal(0);
	private BigDecimal searchEndMoney = new BigDecimal(1000000);
	private long aId;
	private String Hql = "";
	private String selectDealDate;
	private long toAgentId;
	private long orderDetailsType;
	private String check="";
	
	private int balanceType=0;
	
	public int getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(int balanceType) {
		this.balanceType = balanceType;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getSelectDealDate() {
		return selectDealDate;
	}

	public void setSelectDealDate(String selectDealDate) {
		this.selectDealDate = selectDealDate;
		if("1".equals(selectDealDate)){
			Calendar ca = Calendar.getInstance();
			int YY = ca.get(Calendar.YEAR);//获取年份
		    int MM=ca.get(Calendar.MONTH)+1;//获取月份
		    int DD=ca.get(Calendar.DATE);//获取日
		    int HH = ca.get(Calendar.HOUR_OF_DAY);//获得时
		    int NN = ca.get(Calendar.MINUTE);//获得分
		    int SS = ca.get(Calendar.SECOND);//获得秒
		    
		    String month="";
		    if(MM<10){month="0"+MM; }else{month=MM+"";}
		    String day="";
		    if(DD<10){day="0"+DD; }else{day=DD+"";}
		    String hours="";
		    if(HH<10){hours="0"+HH; }else{hours=HH+"";}
		    String minutes="";
		    if(NN<10){minutes="0"+NN; }else{minutes=NN+"";}
		    String seconds="";
		    if(SS<10){seconds="0"+SS; }else{seconds=SS+"";}
			this.beginDate=YY+"-"+month+"-"+day+" 00:00:00";
			this.endDate=YY+"-"+month+"-"+day+" 23:59:59";
		}
	}

	public long getAId() {
		return aId;
	}

	public void setAId(long id) {
		aId = id;
	}

	public String getSearchOrderNo() {
		return searchOrderNo;
	}

	public void setSearchOrderNo(String searchOrderNo) {
		this.searchOrderNo = searchOrderNo;
	}

	public String getSearchOutOrderNo() {
		return searchOutOrderNo;
	}

	public void setSearchOutOrderNo(String searchOutOrderNo) {
		this.searchOutOrderNo = searchOutOrderNo;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getSearchAgentName() {
		return searchAgentName;
	}

	public void setSearchAgentName(String searchAgentName) {
		this.searchAgentName = searchAgentName;
	}

	public String getBeginDate() {
		if(beginDate==null){
			this.beginDate="";
			}
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		if(endDate==null){
			this.endDate="";
			}
		return endDate;
	}

	public void setEndDate(String endDate) {

		this.endDate = endDate;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public int getAgentType() {
		return agentType;
	}

	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {

		thisAction = "";
		beginDate = "";
		endDate = "";
		this.searchAgentName = "";
		this.searchBeginMoney = new BigDecimal("0");
		this.searchEndMoney = new BigDecimal("1000000");
		this.dateType="当日";
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getSearchShopName() {
		return searchShopName;
	}

	public void setSearchShopName(String searchShopName) {
		this.searchShopName = searchShopName;
	}

	public BigDecimal getSearchBeginMoney() {
		return searchBeginMoney;
	}

	public void setSearchBeginMoney(BigDecimal searchBeginMoney) {
		this.searchBeginMoney = searchBeginMoney;
	}

	public BigDecimal getSearchEndMoney() {
		return searchEndMoney;
	}

	public void setSearchEndMoney(BigDecimal searchEndMoney) {
		this.searchEndMoney = searchEndMoney;
	}

	public String getHql() {
		return Hql;
	}

	public void setHql(String hql) {
		Hql = hql;
	}

	public long getToAgentId() {
		return toAgentId;
	}

	public void setToAgentId(long toAgentId) {
		this.toAgentId = toAgentId;
	}

	public long getOrderDetailsType() {
		return orderDetailsType;
	}

	public void setOrderDetailsType(long orderDetailsType) {
		this.orderDetailsType = orderDetailsType;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

}
