package com.fordays.fdpay.cooperate;

public class GatewayMessageFactory {
	public static GatewayMessage getMessage(boolean isSync){
		if(isSync){
			return new GatewayMessageCn();
		}else{
			return new GatewayMessageEn();			
		}
	}
	
	public static FreezeMessage getFreeMessage(boolean isSync){
		if(isSync){
			return new FreezeMessageCn();
		}else{
			return new FreezeMessageEn();			
		}
	}
}
