package com.fordays.fdpay.transaction.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class TransactionRightAdvice implements MethodBeforeAdvice
{
	public void before(Method method, Object[] args, Object target)
	    throws Throwable
	{
		System.out.println("Before method: " + method.getName());
	}

}
