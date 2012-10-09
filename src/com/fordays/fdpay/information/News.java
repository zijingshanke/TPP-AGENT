package com.fordays.fdpay.information;


import com.fordays.fdpay.information._entity._News;

public class News extends _News{
  	private static final long serialVersionUID = 1L;
  	 protected String tmpeTitle;
	public String getTmpeTitle() {
		if(title.length()>10){
			String countxing = "";
			return countxing+title.substring(0,10)+"***";
		}else{
			return title;
		}
	}
  

}
