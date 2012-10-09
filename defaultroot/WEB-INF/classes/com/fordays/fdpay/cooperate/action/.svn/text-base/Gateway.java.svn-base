package com.fordays.fdpay.cooperate.action;

import java.math.BigDecimal;

public class Gateway
{
	public static final BigDecimal baseAmount = new BigDecimal("0.20");

	public static boolean equals(BigDecimal b1, BigDecimal b2)
	{
		System.out.println("------------价钱1："+b1);
		System.out.println("------------价钱2："+b2);
		int b3=b1.add(baseAmount).compareTo(b2);
		System.out.println(b3);
		if (b1.add(baseAmount).compareTo(b2) >= 0)
			return true;
		else
			return false;
	}
	
	public static boolean equals2(BigDecimal b1, BigDecimal b2)
	{
		if (b1.compareTo(b2) >= 0)
			return true;
		else
			return false;
	}

	public static int compareTo(BigDecimal b1, BigDecimal b2)
	{
		return b1.add(baseAmount).compareTo(b2);

	}	
	
	public static boolean equals(double b1, double b2)
	{
		if (b1+0.2>=b2)
			return true;
		else
			return false;
	}
}
