package com.fordays.fdpay.system.biz;



import com.fordays.fdpay.system.LoginLog;
import com.neza.exception.AppException;

public interface LoginLogBiz {
	public void saveLoginLog(LoginLog loginlog) throws AppException;
}