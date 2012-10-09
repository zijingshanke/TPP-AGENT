package com.fordays.fdpay.agent;

import java.util.ArrayList;
import java.util.List;

import com.fordays.fdpay.system.Bank;
import com.fordays.fdpay.system.City;
import com.fordays.fdpay.system.Province;
public class Josn {

	
	
	public static void main(String[] args) {
		
		Josn josn = new Josn();
		List<City> list = new ArrayList();
		City city = new City();
		city.setId(1);
		city.setCname("珠海");
		list.add(city);
		City city2 = new City();
		city2.setId(2);
		city2.setCname("深圳");
		list.add(city2);
		City city3 = new City();
		city3.setId(3);
		city3.setCname("中山");
		list.add(city3);
	}
	
	public String toJosnProvince(List<Province> list){
		String resjosn="";
		
		for(int i=0;i<list.size();i++){
			if(i==0){
				resjosn="{\"provinces\":[{\"id\":\""+list.get(i).getId()+"\",\"cname\":\""+list.get(0).getCname()+"\"}";
			}
			if(i>0){
				resjosn+=",{\"id\":\""+list.get(i).getId()+"\",\"cname\":\""+list.get(i).getCname()+"\"}";
			}
			if(i==list.size()-1){
				 	resjosn+="]}";
				}
	
		}

		return resjosn;
	}
	
	public String toJosnCitys(List<City> list){
		String resjosn="";
		
		for(int i=0;i<list.size();i++){
			if(i==0){
				resjosn="{\"citys\":[{\"id\":\""+list.get(i).getId()+"\",\"cname\":\""+list.get(0).getCname()+"\"}";
			}
			if(i>0){
				resjosn+=",{\"id\":\""+list.get(i).getId()+"\",\"cname\":\""+list.get(i).getCname()+"\"}";
			}
			if(i==list.size()-1){
				 	resjosn+="]}";
				}
	
		}

		return resjosn;
	}
	
	public String toJosnBank(List<Bank> list){
		String resjosn="";
		
		for(int i=0;i<list.size();i++){
			if(i==0){
				resjosn="{\"banks\":[{\"id\":\""+list.get(i).getId()+"\",\"cname\":\""+list.get(0).getCname()+"\"}";
			}
			if(i>0){
				resjosn+=",{\"id\":\""+list.get(i).getId()+"\",\"cname\":\""+list.get(i).getCname()+"\"}";
			}
			if(i==list.size()-1){
				 	resjosn+="]}";
				}
	
		}

		return resjosn;
	}

}
