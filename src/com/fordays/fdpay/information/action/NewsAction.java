package com.fordays.fdpay.information.action;


import com.fordays.fdpay.information.biz.NewsBiz;
import com.neza.base.BaseAction;

public class NewsAction extends BaseAction{

	private NewsBiz newsBiz;
	
	
  	public NewsBiz getNewsBiz() {
		return newsBiz;
	}
	public void setNewsBiz(NewsBiz newsBiz) {
		this.newsBiz = newsBiz;
	}
	
}
