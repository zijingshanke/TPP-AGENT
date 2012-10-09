package com.fordays.qmpay.shop.agent19pay.biz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.borland.jdbcx.dbviewers.BeginEditEvent;
import com.fordays.fdpay.agent.Agent;
import com.fordays.fdpay.agent.dao.AgentDAO;
import com.fordays.fdpay.transaction.OrderDetails;
import com.fordays.fdpay.transaction.Transaction;
import com.fordays.fdpay.transaction.dao.TransactionDAO;
import com.fordays.qmpay.shop.agent19pay.Agent19pay;
import com.fordays.qmpay.shop.agent19pay.Mobile;
import com.fordays.qmpay.shop.agent19pay.Product;
import com.neza.base.NoUtil;
import com.neza.encrypt.MD5;
import com.neza.encrypt.XmlUtil;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;



/**
 * 充值业务实现类
 */

public class Agent19payBizImp implements Agent19payBiz {
	private AgentDAO agentDAO;
	private TransactionDAO transactionDAO;
	public Document document = null;
	private NoUtil noUtil;
	//获取产品信息
	public Agent19pay getMobileProduct(HttpServletRequest request, HttpServletResponse response,Agent19pay agent19pay) {
		// TODO Auto-generated method stub
//		Agent19pay agent19pay = new Agent19pay();
//		
			
		Mobile mobile=getMobileNumInfo(agent19pay.getMobilenum());
		mobile.setProdContent(agent19pay.getProdContent());
		mobile.setProdType(agent19pay.getProdType());
		List<Product> list_product= getProductList();
		Product product=searchProduct(list_product,mobile);
		if(product==null){
			System.out.println("没有对应的产品Id="); 
			return null;
		}else{
		System.out.println("产品ID="+product.getProdId());
		Agent19pay ag19pay = new Agent19pay();
		ag19pay.setProdid(product.getProdId());
		ag19pay.setMobilenum(agent19pay.getMobilenum());
		ag19pay.setProvincename(product.getProdProvinceid());
		ag19pay.setIsptype(product.getProdIsptype());
		ag19pay.setProdContent(Integer.parseInt(product.getProdContent()));
		ag19pay.setPrice(new BigDecimal(product.getProdPrice()));
		return ag19pay;
		}
		
	}
	
	public List<Product> getProductList(){
		Agent19pay agent19pay = new Agent19pay();
		String S_url=agent19pay.getProductURL();

		java.io.InputStream in;
		String res = null;
		List<Product> list_product=null;
		URL url;
			
				try {
					url = new URL(S_url);
					HttpURLConnection connection;
					connection = (HttpURLConnection) url.openConnection();
					// 模拟成ie
					connection.connect();
					in = connection.getInputStream();
					java.io.BufferedReader breader = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					String str = breader.readLine();
					
					while (str != null) {

						res=str;
						str = breader.readLine();
					}
					list_product=parseProductListXML(res);
				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("========================远程调用19pay异常==================");
					e.printStackTrace();
				}
		return list_product;
	}
	//解析XML
	public List<Product> parseProductListXML(String xml){
		
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e2) {
			// TODO 自动生成 catch 块
			e2.printStackTrace();
		}
		Element rootElm = document.getRootElement();
		List nodes = rootElm.elements("products");
		   List<Product> list_product=new ArrayList<Product>();
		for (Iterator it = nodes.iterator(); it.hasNext();) {
		   Element elm = (Element) it.next();
		   List nodesChild =elm.elements("product");
		   Product product = new Product();
		   for (Iterator it2 = nodesChild.iterator(); it2.hasNext();) {
			   Element elmChild = (Element) it2.next(); 
			  
//	            for(Iterator it3=elmChild.attributeIterator();it3.hasNext();){
//	                Attribute attribute = (Attribute) it3.next();
	            //    打印所有属性
//	                System.out.println(attribute.getName());
//	                String text=attribute.getText();
//	                System.out.println(text);
	               
	                String attributeKey=elmChild.attribute("name").getText();
	                
	                String attributeValue=elmChild.attribute("value").getText();
	                if("prodId".equals(attributeKey)){
	                	product.setProdId(attributeValue);
	                }
	                if("prodContent".equals(attributeKey)){
	                	product.setProdContent(attributeValue);
	                }
	                if("prodPrice".equals(attributeKey)){
	                	product.setProdPrice(attributeValue);
	                }
	                if("prodIsptype".equals(attributeKey)){
	                	
	                	try {
						//	product.setProdIsptype(new String(attributeValue.getBytes("GB2312"),"UTF-8"));
							product.setProdIsptype(URLDecoder.decode(attributeValue, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                if("prodDelaytimes".equals(attributeKey)){
	                	try {
							product.setProdDelaytimes(URLDecoder.decode(attributeValue, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                if("prodProvinceid".equals(attributeKey)){
	                	try {
							product.setProdProvinceid(URLDecoder.decode(attributeValue, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                if("prodType".equals(attributeKey)){
	                	try {
							product.setProdType(URLDecoder.decode(attributeValue, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	                
//	            }
	            //指定属性打印
	          
		   }
	
           list_product.add(product);
		  
		   // do something
          
		}
		 return list_product;
	}


	//号段查询
	public Mobile getMobileNumInfo(String mobilenum){
		Agent19pay agent19pay =new Agent19pay();
		agent19pay.setMobilenum(mobilenum);
		String S_url=agent19pay.getAccsegmentURL();
		System.out.println("请求地址:"+S_url);
		java.io.InputStream in;
		String res = null;
		Mobile mobile=null;
		URL url;
			
				try {
					url = new URL(S_url);
					HttpURLConnection connection;
					connection = (HttpURLConnection) url.openConnection();
					// 模拟成ie
					connection.connect();
					in = connection.getInputStream();
					java.io.BufferedReader breader = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					String str = breader.readLine();
					
					while (str != null) {

						res=str;
						str = breader.readLine();
					}
					
					 mobile=parseMobileXML(res);
					 
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("========================远程调用19pay异常==================");
					e.printStackTrace();
				}
			return mobile;
		
	}
	
	
	public Mobile parseMobileXML(String S_XML){
		try {
			if(S_XML!=null){
			document = DocumentHelper.parseText(S_XML);
			}else{
				return null;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root =document.getRootElement();
		List nodelist = root.elements("acc");
		Mobile mobile=null;
		for (Iterator it = nodelist.iterator(); it.hasNext();) {
			   Element elm = (Element) it.next();
			   List nodesChild =elm.elements("mobile");
			    mobile = new Mobile();
			   for (Iterator it2 = nodesChild.iterator(); it2.hasNext();) {
				   Element elmChild = (Element) it2.next(); 
	                String attributeKey=elmChild.attribute("name").getText();	                
	                String attributeValue=elmChild.attribute("value").getText();
	                if("isptype".equals(attributeKey)){
	                	try {
							mobile.setIsptype(URLDecoder.decode(attributeValue,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }else if("provincename".equals(attributeKey)){
	                	try {
							mobile.setProvincename(URLDecoder.decode(attributeValue,"UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }else if("citycode".equals(attributeKey)){
	                	mobile.setCitycode(attributeValue);
	                }else if("detail".equals(attributeKey)){
	                	try {
							mobile.setDetail(URLDecoder.decode(attributeValue, "UTF-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
			   }

		}
		return mobile;
	}
	
	//查找符合类型的产品号
	public Product searchProduct(List<Product> list_product,Mobile mobile){
		Product product=null;
		for(int i=0;i<list_product.size();i++){
			Product pro =list_product.get(i);
			if(pro.getProdType().equals(mobile.getProdType())&&pro.getProdIsptype().equals(mobile.getIsptype())&&
			pro.getProdContent().equals(mobile.getProdContent()+"")&&
			pro.getProdProvinceid().equals(mobile.getProvincename())){
				return pro;
			}
		}
		return product;
	}
    
	//创建一笔手机充值交易记录（本地）
	public Transaction createTransaction(Agent19pay agent19pay,Agent agent) throws AppException{
		BigDecimal ratePrice=new BigDecimal(0);
		BigDecimal rate=new BigDecimal(0);
		
			System.out.println(agent.getLoginName()+"------------账户");
			//System.out.println(agent19pay.getPrice().multiply(new BigDecimal(Agent19pay.rate)).setScale(2, BigDecimal.ROUND_HALF_UP)+"------------手续费");
			System.out.println(agent19pay.getPrice()+"------------没加手续费");
			if(agent19pay.getPrice().compareTo(new BigDecimal(50))<=0){
				rate=new BigDecimal(0.1).setScale(2, BigDecimal.ROUND_HALF_UP);
				ratePrice=ratePrice.add(rate.add(agent19pay.getPrice()));
			}else
			if(agent19pay.getPrice().compareTo(new BigDecimal(100))<=0 ){
				rate=new BigDecimal(0.2).setScale(2, BigDecimal.ROUND_HALF_UP);
				ratePrice=ratePrice.add(rate).add(agent19pay.getPrice());
			}else if(agent19pay.getPrice().compareTo(new BigDecimal(100))==1){
				rate=agent19pay.getPrice().multiply(new BigDecimal(Agent19pay.rate)).setScale(2, BigDecimal.ROUND_HALF_UP);
				ratePrice=ratePrice.add(rate).add(agent19pay.getPrice());
			}
			System.out.println(rate+"------------手续费");
			System.out.println(ratePrice+"------------加上手续费的");
			OrderDetails orderDetails = new OrderDetails();
			orderDetails.setShopName("手机充值:"+agent19pay.getProdid()+"-"+agent19pay.getMobilenum()+"$"+rate);
			orderDetails.setShopPrice(ratePrice);
			orderDetails.setShopTotal(new Long(1));
			orderDetails.setDetailsContent("运营商:"+agent19pay.getIsptype()+"   充值地区:"+agent19pay.getProvincename()+"   充值面额:"+agent19pay.getProdContent());
			orderDetails.setPaymentPrice(ratePrice);
			orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
			orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());	
			orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
			orderDetails.setBuyType(OrderDetails.BUY_TYPE_1);
			transactionDAO.addOrderDetails(orderDetails);
			Transaction transaction = new Transaction();
			transaction.setStatus(Transaction.STATUS_1);
			transaction.setType(Transaction.TYPE_4);
			//String loginName="qmlinmq@163.com";
			Agent toAgent=null;
				toAgent = agentDAO.getAgentByEmail(Agent19pay.QMACCOUNT);		
			transaction.setFromAgent(agent);
			transaction.setToAgent(toAgent);
			transaction.setOrderDetails(orderDetails);
			transaction.setAmount(ratePrice);
			transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
			transaction.setNo(noUtil.getTransactionNo());
			transactionDAO.saveTransaction(transaction);
			
			return transaction;
		
	}
	//创建一笔手机充值交易记录（接口）
	public Transaction createTransaction(Agent19pay agent19pay) throws AppException{
		BigDecimal ratePrice=new BigDecimal(0);
		BigDecimal rate=new BigDecimal(0);
		System.out.println("===================开始创建手机充值的交易记录============");
		System.out.println("====商户ID=="+agent19pay.getQmagentId());
		Agent agent =agentDAO.getAgentById(agent19pay.getQmagentId());
		if(agent!=null){
				System.out.println(agent.getLoginName()+"------------账户");
				//System.out.println(agent19pay.getPrice().multiply(new BigDecimal(Agent19pay.rate)).setScale(2, BigDecimal.ROUND_HALF_UP)+"------------手续费");
				System.out.println(agent19pay.getPrice()+"------------没加手续费");
				if(agent19pay.getPrice().compareTo(new BigDecimal(50))<=0){
					rate=new BigDecimal(0.1).setScale(2, BigDecimal.ROUND_HALF_UP);
					ratePrice=ratePrice.add(rate.add(agent19pay.getPrice()));
				}else
				if(agent19pay.getPrice().compareTo(new BigDecimal(100))<=0 ){
					rate=new BigDecimal(0.2).setScale(2, BigDecimal.ROUND_HALF_UP);
					ratePrice=ratePrice.add(rate).add(agent19pay.getPrice());
				}else if(agent19pay.getPrice().compareTo(new BigDecimal(100))==1){
					rate=agent19pay.getPrice().multiply(new BigDecimal(Agent19pay.rate)).setScale(2, BigDecimal.ROUND_HALF_UP);
					ratePrice=ratePrice.add(rate).add(agent19pay.getPrice());
				}
				System.out.println(rate+"------------手续费");
				System.out.println(ratePrice+"------------加上手续费的");
				Transaction transaction = new Transaction();
				
				OrderDetails orderDetails = new OrderDetails();
				orderDetails.setShopName("手机充值:"+agent19pay.getProdid()+"-"+agent19pay.getMobilenum()+"$"+rate);
				orderDetails.setShopPrice(ratePrice);
				orderDetails.setShopTotal(new Long(1));
				orderDetails.setDetailsContent("运营商:"+agent19pay.getIsptype()+"   充值地区:"+agent19pay.getProvincename()+"   充值面额:"+agent19pay.getProdContent());
				orderDetails.setPaymentPrice(ratePrice);
				orderDetails.setCreateDate(new Timestamp(System.currentTimeMillis()));
				orderDetails.setOrderDetailsNo(noUtil.getOrderDetailsNo());
				orderDetails.setOrderDetailsType(OrderDetails.ORDER_DETAILS_TYPE_1);
				transactionDAO.addOrderDetails(orderDetails);		
				transaction.setStatus(Transaction.STATUS_1);
				transaction.setType(Transaction.TYPE_4);
				//String loginName="qmlinmq@163.com";
				Agent toAgent=null;
					toAgent = agentDAO.getAgentByEmail(Agent19pay.QMACCOUNT);		
				transaction.setFromAgent(agent);
				transaction.setToAgent(toAgent);
				transaction.setOrderDetails(orderDetails);
				transaction.setAmount(ratePrice);
				transaction.setAccountDate(new Timestamp(System.currentTimeMillis()));
				transaction.setNo(noUtil.getTransactionNo());
				transactionDAO.saveTransaction(transaction);
				
				System.out.println("===订单编号==="+orderDetails.getOrderDetailsNo());
				System.out.println("===FromAgent==="+agent.getLoginName());
				System.out.println("===ToAgent==="+toAgent.getEmail());
				System.out.println("===详细:==="+orderDetails.getDetailsContent());
				System.out.println("===时间==="+DateUtil.getDateString(transaction.getAccountDate(), "yyyy-MM-dd HH:mm:ss"));
				System.out.println("===================结束创建手机充值的交易记录============");
				
				return transaction;
			}
		else{
			System.out.println("===================创建交易失败============");
			System.out.println("===================买家账户不存在============");
			return null;
		}
	}

	public String pay(Agent19pay agent19pay){
		String S_url=agent19pay.getFillURL();
		String res = null;
		System.out.println("=============19pay手机充值:开始取货==============");
		java.io.InputStream in;
		String resultno=null;
		URL url;			
				try {
					url = new URL(S_url);
					HttpURLConnection connection;
					connection = (HttpURLConnection) url.openConnection();
					// 模拟成ie
					connection.connect();
					in = connection.getInputStream();
					java.io.BufferedReader breader = new BufferedReader(
							new InputStreamReader(in, "utf-8"));
					String str = breader.readLine();
					
					while (str != null) {
						res=str;
						str = breader.readLine();
					}
//					System.out.println(res);
					 resultno=parsePayResultnoXML(res);
					System.out.println("=========19pay手机充值:充值结果代码:"+resultno+"=========");
				
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("========================远程调用19pay异常==================");
					e.printStackTrace();
				}
				return resultno;
	}
	
	public String parsePayResultnoXML(String S_XML){
		try {
			document = DocumentHelper.parseText(S_XML);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root =document.getRootElement();
		List nodelist = root.elements("items");
		
		for (Iterator it = nodelist.iterator(); it.hasNext();) {
			   Element elm = (Element) it.next();
			   List nodesChild =elm.elements("item");			  
			   for (Iterator it2 = nodesChild.iterator(); it2.hasNext();) {
				   Element elmChild = (Element) it2.next(); 
	                String attributeKey=elmChild.attribute("name").getText();	                
	                String attributeValue=elmChild.attribute("value").getText();
	                if("resultno".equals(attributeKey)){
	                	return attributeValue;
	                }
			   }

		}
		return null;
	}
	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setTransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	public String return19payNotice(String orderid, Long status,
			BigDecimal ordermoney,String verifystring) throws AppException {
		Agent19pay _19pay = new Agent19pay();
		
		String key="orderid="+orderid+"&status="+status+"&ordermoney="+ordermoney+"&merchantKey="+_19pay.getMerchantKey();
		if(MD5.encrypt(key).equals(verifystring)){
			System.out.println("====================19pay回调验证通过==================");
			OrderDetails order =transactionDAO.getOrderDetailByOrderNo(orderid);			
			if(order!=null){
				String price =order.getDetailsContent();
				String ss=price.substring(price.lastIndexOf("充值面额:"),price.length());
				BigDecimal tempordermoney=new BigDecimal(ss.substring(price.indexOf(":")+2));
				System.out.println("==================面额:"+tempordermoney);
				List list=transactionDAO.getTransactionByOrderDetailsNo(orderid);
				Transaction ts=null;
				if(list==null&&list.size()>0){
					System.out.println("===================请求的订单号:"+orderid+"==================");
					System.out.println("====================19pay回调,交易失败,请求的订单号不存在!==================");
					return null;
				}else{
					 ts =(Transaction)list.get(0);
				}
				
				if(ts!=null&&tempordermoney.compareTo(ordermoney)==0){
					if(status==4){
						ts.setStatus(Transaction.STATUS_4);
						ts.setMark("交易失败,应全额退款给机主!");
						System.out.println("====================19pay回调,交易失败,应全额退款给机主==================");
					}
					
					return status.toString();					
				}else{
					if(ts!=null&&status==4){
						ts.setStatus(Transaction.STATUS_4);
						BigDecimal temPrice=tempordermoney.subtract(ordermoney);
						ts.setMark("部分退款成功,应退回机主价值"+temPrice+"充值面额的钱!");
						System.out.println("====================19pay回调,交易失败,部分退款成功,应退回机主价值"+temPrice+"充值面额的钱!==================");
					}
					return status.toString();					
				}
			}
		}else{
			return null;
		}
		return null;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public NoUtil getNoUtil() {
		return noUtil;
	}
	
	public static void main(String[] s){
		Agent19payBizImp a = new Agent19payBizImp();
		List<Product> list= a.getProductList();
		for(int i=0;i<list.size();i++){
			Product product = list.get(i);
			System.out.println(product.getProdId());
			System.out.println(product.getProdContent());
			System.out.println(product.getProdPrice());
			System.out.println(product.getProdProvinceid());			
			System.out.println(product.getProdType());
			System.out.println("------------------------");
		}
	}

}
