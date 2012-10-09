//验证两者相等
function checkEquals(target,present,objid,message,wrongMessage)
{
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
	var target = document.getElementById(target);
	
	var present = document.getElementById(present);
	var objid = document.getElementById(objid);
	
		
		if(target.value==present.value){
			objid.className="sucess";
			if(IE_FIREFOX>-1)
				objid.innerText="";
			else
				objid.textContent="";
		return true;
		}
		else{
			objid.className="errors";
			if(IE_FIREFOX>-1)
				objid.innerText=wrongMessage;
			else
				objid.textContent=wrongMessage;
			return false;
			
		}
	
}

//验证身份证
function checkCard(certNum,objid,message)
{
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
	var certNum = document.getElementById(certNum).value;
	var objid = document.getElementById(objid);
	if (verifyIdentity(certNum))
	{    
		objid.className="sucess";
		if(IE_FIREFOX>-1)
			objid.innerText="";
		else
			objid.textContent="";
		return  true;
	}else{
		objid.className="errors";
		if(IE_FIREFOX>-1)
			objid.innerText=message;
		else
			objid.textContent=message;
		return  false;
	}
	return false;
}  

function verifyIdentity(_strIdNum)
{
	var isCard=/^((1[1-5])|(2[1-3])|(3[1-7])|(4[1-6])|(5[0-4])|(6[1-5])|71|(8[12])|91)\d{4}((19\d{2}(0[13-9]|1[012])(0[1-9]|[12]\d|30))|(19\d{2}(0[13578]|1[02])31)|(19\d{2}02(0[1-9]|1\d|2[0-8]))|(19([13579][26]|[2468][048]|0[48])0229))\d{3}(\d|X|x)?$/;
	
	if (isCard.test(_strIdNum)||(/(\d)(?=(\d\d\d)+(?!\d))/g.test(_strIdNum)&&_strIdNum.length==15))
	{    
		return  true;
	}
	return false;
}  

function checkNotEquals(target,present,objid,message,wrongMessage)
{
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
	var target = document.getElementById(target);
	
	var present = document.getElementById(present);
	var objid = document.getElementById(objid);
	
		
		if(target.value!=present.value){
			objid.className="sucess";
			if(IE_FIREFOX>-1)
				objid.innerText="";
			else
				objid.textContent="";
		return true;
		}
		else{
			objid.className="errors";
			if(IE_FIREFOX>-1)
				objid.innerText=wrongMessage;
			else
				objid.textContent=wrongMessage;
			return false;
			
		}
	return false;
}

function trim(s)  
{   
	if(s!="")
  		return  s.replace(/(^[\s]*)|([\s]*$)/g, "");  
   else 
   		return "";
}  


//6-20位数字  objid为要检查的text文本id
function lengthBetween(objid,tipid,begin,end,wrongMessage)
{
	var obj = trim(document.getElementById(objid).value);
	var tipid = document.getElementById(tipid);
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
	if(obj.length>=begin && obj.length<=end)
	{
		tipid.className="sucess";
		if(IE_FIREFOX>-1)
				tipid.innerText="";
			else
				tipid.textContent="";
				return true;
	}else{
		tipid.className="errors";
		if(IE_FIREFOX>-1)
				tipid.innerText=wrongMessage;
			else
				tipid.textContent=wrongMessage;
			return false;
	}
	return false;
}
//set text
function setText(objid,message)
{
	var objid = document.getElementById(objid);
	
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
	if(message=='')
		objid.className="sucess";
	else
		objid.className="information";
	if(IE_FIREFOX>-1)
				objid.innerText=message;
			else
				objid.textContent=message;

}
//手机验证
function chkmobile(mobile,objid,message)
{
	var objid = document.getElementById(objid);
	var mobile = document.getElementById(mobile);
	var chkstring = mobile.value.substring(0,2);
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
		if((/^13\d{9}$/g.test(mobile.value))||(/^15\d{9}$/g.test(mobile.value)) ||(/^18\d{9}$/g.test(mobile.value)))
		{
			objid.className="sucess";
			if(IE_FIREFOX>-1){
				objid.innerText="";
				return;
			}
			else{
				objid.textContent="";
				return;
			}
		}
	if(IE_FIREFOX>-1)
		objid.innerText=message;
	else
		objid.textContent=message;
	objid.className="errors";
}
//电话验证
function chktelephone(telephone,objid,message)
{
	var objid = document.getElementById(objid);
	var telephone = document.getElementById(telephone);
	var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
	var regm=/^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/;
		if( regm.test(telephone.value))
		{
			objid.className="sucess";
			if(IE_FIREFOX>-1){
				objid.innerText="";
				return;
			}
			else{
				objid.textContent="";
				return;
			}
		}
	if(IE_FIREFOX>-1)
		objid.innerText=message;
	else
		objid.textContent=message;
	objid.className="errors";
}
//email
function chkemail(email,objid,message)     
{ 
	if(email.value!=""){
		var IE_FIREFOX = navigator.appName.indexOf(" Explorer" );
		var email = document.getElementById(email);
		var objid = document.getElementById(objid);   
		var regm = /^([a-z0-9A-Z]+(-|_)*[\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-|_)*([a-z0-9A-Z]+)?\.)+[a-zA-Z]{2,}$/
		if (email.value.match(regm)){     
			objid.className="sucess";
			if(IE_FIREFOX>-1)
				objid.innerText=message;
			else
				objid.textContent=message;
			return true;  
		}      
		
	}
	objid.className="errors";
	if(IE_FIREFOX>-1)
		objid.innerText="Email地址格式不正确,请填写正确的Email地址。";
	else
		objid.textContent="Email地址格式不正确,请填写正确的Email地址。";
	return false;   
     
} 

