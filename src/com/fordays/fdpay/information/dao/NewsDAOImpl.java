package com.fordays.fdpay.information.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fordays.fdpay.information.News;
import com.fordays.fdpay.information.NewsListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;
public class NewsDAOImpl extends BaseDAOSupport implements NewsDAO{
	//news主页
	public List getNews(NewsListForm clf) throws AppException {
		Hql hql=new Hql();
		hql.add("from News n where status=?");
		hql.addParamter(new Long(1));
		hql.add(" order by n.createDate desc");
		System.out.println("hql==="+hql);
		return this.list(hql, clf);
	}
	//login使用
	public List list() throws AppException {
		String hql="FROM News news where news.status=1 ORDER BY news.createDate desc ";
		System.out.println("hql==="+hql);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0){
			//if(query.list().size()>=3){
			List newsList = new ArrayList();
			for(int i=0;i<query.list().size();i++){
				newsList.add(query.list().get(i));
			}
			return newsList;
//			}
//			else{
//				return query.list();
//			}
		}	
		return null;
	}
	//showNews左
	public List listNews(NewsListForm clf) throws AppException {
		String hql="FROM News news where news.status=1 ORDER BY news.createDate desc ";
		System.out.println("hql==="+hql);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0){
			
			if(query.list().size()>=7){
				List newsList = new ArrayList();
				for(int i=0;i<7;i++){
					newsList.add(query.list().get(i));
			}
			return newsList;
			}
			else{
				return query.list();
			}
		}	
		return null;
	}

	public News getNewsById(int id) throws AppException {
		Hql hql = new Hql();
		hql.add("from News where id=?");
		hql.addParamter(id);
		hql.add(" order by rank desc");
		System.out.println("HQL = " + hql);
		Query query = NewsDAOImpl.this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0)
			return (News) query.list().get(0);
		else
			return new News();
	}

	public long updateInfo(News news) throws AppException {
		System.out.println(news.getId());
		if (news.getId() > 0)
			return save(news);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public long save(News news) throws AppException {
		this.getHibernateTemplate().update(news);
		return news.getId();
	}

	public void deleteNewsById(int id) throws AppException {
		this.getHibernateTemplate().delete(this.getNewsById(id));
	}
}
