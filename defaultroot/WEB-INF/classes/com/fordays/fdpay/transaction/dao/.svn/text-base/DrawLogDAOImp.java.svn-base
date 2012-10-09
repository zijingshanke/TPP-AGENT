package com.fordays.fdpay.transaction.dao;

import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fordays.fdpay.transaction.DrawLog;
import com.neza.base.BaseDAOSupport;
import com.neza.tool.DateUtil;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class DrawLogDAOImp extends BaseDAOSupport implements DrawLogDAO {
	private TransactionTemplate drawLogTemplate;

	public void setChargeManager(
			PlatformTransactionManager drawLogManager) {
		this.drawLogTemplate = new TransactionTemplate(drawLogManager);
	}

	public long save(DrawLog  drawLog)throws AppException {
		this.getHibernateTemplate().saveOrUpdate(drawLog);
		return drawLog.getId();
	}

	public long merge(DrawLog drawLog) throws AppException{
		this.getHibernateTemplate().merge(drawLog);
		return drawLog.getId();
	}

	public long update(DrawLog drawLog) throws AppException{
		if (drawLog.getId() > 0)
			return save(drawLog);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException{
		if (id > 0) {
			DrawLog drawLog = (DrawLog) this.getHibernateTemplate()
					.get(DrawLog.class, new Long(id));
			this.getHibernateTemplate().delete(drawLog);

		}
	}

	public DrawLog getDrawLogById(long id) throws AppException{

		DrawLog drawLog;
		if (id > 0) {
			drawLog = (DrawLog) this.getHibernateTemplate().get(
					DrawLog.class, new Long(id));
			return drawLog;

		} else
			return new DrawLog();
	}

	public DrawLog queryDrawLogById(long id) throws AppException{

		if (id > 0) {
			DrawLog drawLog = (DrawLog) this.getQuery(
					"from DrawLog where id=" + id).uniqueResult();
			if (drawLog != null) {
				return drawLog;
			} else {
				return new DrawLog();
			}

		} else
			return new DrawLog();
	}

}
