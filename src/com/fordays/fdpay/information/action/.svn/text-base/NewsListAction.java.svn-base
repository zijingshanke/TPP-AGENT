package com.fordays.fdpay.information.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.fordays.fdpay.information.News;
import com.fordays.fdpay.information.NewsListForm;
import com.fordays.fdpay.information.biz.NewsBiz;
import com.neza.exception.AppException;

public class NewsListAction extends DispatchAction {
	private NewsBiz newsBiz;
	
	public NewsBiz getNewsBiz() {
		return newsBiz;
	}


	public void setNewsBiz(NewsBiz newsBiz) {
		this.newsBiz = newsBiz;
	}


	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		NewsListForm nlf = (NewsListForm) form;
		if (nlf == null)
			nlf = new NewsListForm();
		List list = newsBiz.list();
		nlf.setList(list);
		request.setAttribute("nlf", nlf);
		News Latest=(News) list.get(0);
		request.setAttribute("Latest", Latest);
		forwardPage = "news";
		return mapping.findForward(forwardPage);
	}
	public ActionForward listNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws AppException {
		String forwardPage = "";
		NewsListForm nlf = (NewsListForm) form;
		nlf.setList(newsBiz.getNews(nlf));
		request.setAttribute("nlf", nlf);
		forwardPage = "listNews";
		return mapping.findForward(forwardPage);
	}
	
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws AppException{
		String forwardPage = "";
		int id=Integer.parseInt(request.getParameter("id"));
		
			News news = (News) newsBiz.getNewsById(id);
			
			news.setThisAction("view");
			request.setAttribute("news", news);
			
		forwardPage = "viewNews";
		return (mapping.findForward(forwardPage));
	}
	
	
	public ActionForward showNews(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws AppException
	{
		String forwardPage = "";
		int id=Integer.parseInt(request.getParameter("id"));
		NewsListForm nlf = (NewsListForm) form;
		if (nlf == null)
			nlf = new NewsListForm();
		List list = newsBiz.listNews(nlf);
		nlf.setList(list);
		request.setAttribute("listSize",list.size());
		request.setAttribute("nlf", nlf);
		if(id>0){
			News news =newsBiz.getNewsById(id);
			long readnum=0;
			if(news.getReadNum()!=null)
				readnum=news.getReadNum();
			else 
				readnum=0;
			news.setReadNum(readnum+1);
			newsBiz.save(news);
			request.setAttribute("news", news);
			forwardPage="showNews";
			return mapping.findForward(forwardPage);
		}
		forwardPage="login";
		return mapping.findForward(forwardPage);
	}
}
