package com.fordays.fdpay.transaction.action;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class UtilXml {

public static void main(String[] args){
   
   String v_strXML="<scheme><normal><time><start>08:30</start><end>12:00</end></time><time><start>13:30</start><end>17:30</end></time></normal><weekend><saturday><rest>1</rest></saturday><sunday><rest>1</rest></sunday></weekend><special><workday><date>2009-02-27</date></workday><holiday><date>2009-04-22</date></holiday><holiday><date>2009-04-26</date></holiday><workday><date>2009-04-28</date><time><start>10:00</start><end>12:00</end></time></workday><holiday><date>2009-04-29</date></holiday><workday><date>2009-04-30</date><time><start>10:30</start><end>17:00</end></time></workday></special></scheme>";
   String s_xml="";
 

   Document doc = null;
   try {
    doc = DocumentHelper.parseText(v_strXML);
   } catch (DocumentException e2) {
    // TODO 自动生成 catch 块
    e2.printStackTrace();
   }
   Element root = doc.getRootElement();// 指向根节点
   
   //normal解析
   Element normal = root.element("normal");
   try {    
    List lstTime = normal.elements("time");// 所有的Item节点
    for(int i=0;i<lstTime.size();i++){
     Element etime = (Element)lstTime.get(i);
     Element start = etime.element("start");
     Element end = etime.element("end");
     System.out.println("start.getTextTrim()="+start.getTextTrim());
     System.out.println("end.getTextTrim()="+end.getTextTrim());
    }
//    Iterator Itr = lstTime.iterator();        
//    while (Itr.hasNext()) {
//     Element etime = (Element) Itr.next();// 一个Item节点    
//     Element start = etime.element("start");
//     Element end = etime.element("end");
//     System.out.println(start.getTextTrim());
//     System.out.println(end.getTextTrim());
//    }
   }catch(Exception e){
    e.printStackTrace();
   }
   
   //weekend解析
   Element weekend = root.element("weekend");
   try {
    //周6
    List lstSaturday = weekend.elements("saturday");// 所有的Item节点
    Iterator Itr1 = lstSaturday.iterator();        
    while (Itr1.hasNext()) {
     Element eSaturday = (Element) Itr1.next();// 一个Item节点    
     Element rest = eSaturday.element("rest");
     System.out.println("周六休息"+rest.getTextTrim());
    }
    
    //周日
    List lstSunday = weekend.elements("sunday");// 所有的Item节点
    Iterator Itr2 = lstSunday.iterator();        
    while (Itr2.hasNext()) {
     Element eSunday = (Element) Itr2.next();// 一个Item节点    
     Element rest = eSunday.element("rest");
     System.out.println("周日休息"+rest.getTextTrim());
    }
   }catch(Exception e){
    e.printStackTrace();
   }

   //special解析
   Element special = root.element("special");
   try {
    //特殊工作日workday
    List lstWorkday = special.elements("workday");// 所有的Item节点
    Iterator Itr1 = lstWorkday.iterator();        
    while (Itr1.hasNext()) {
     Element eWorkday = (Element) Itr1.next();// 一个Item节点    
     Element date = eWorkday.element("date");
     System.out.println("特殊工作日==="+date.getText());
     List time = eWorkday.elements("time");
     Iterator Itr2 = time.iterator(); 
     while(Itr2.hasNext()){
      Element etime = (Element) Itr2.next();
      Element start = etime.element("start");
      Element end = etime.element("end");
      System.out.println("特殊开始时间"+start.getTextTrim());
      System.out.println("特殊结束时间"+end.getTextTrim());
     }
    }
    
    //节假日holiday
    List lstHoliday = special.elements("holiday");// 所有的Item节点
    Iterator Itr3 = lstHoliday.iterator();        
    while (Itr3.hasNext()) {
     Element eHoliday = (Element) Itr3.next();// 一个Item节点    
     Element date = eHoliday.element("date");
     System.out.println("节假日==="+date.getTextTrim());
    }
   
   }catch(Exception e){
    e.printStackTrace();
   }
}
}

