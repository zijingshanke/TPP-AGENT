/*
版    本：V1.0
作    者：张威威
描    述:证书工具类

initActivex 函数 加载activex

*/


//initActivex 函数 加载activex
function initActivex(activexName){
	document.body.innerHTML+="<object id='"+activexName+"' name='"+activexName+"'  classid='clsid:2E93307E-777D-49E4-886A-D5B04470796A' codebase='qmCert.cab'  width='0px;' height='0px;' style='display: none'></object>";
}

//是否验证证书
function isValidCerts(certStatus){
	if(certStatus==0)
		return false;
	else
		return true;
}

/*
验证证书 validCerts
parm1 isValid 值为 true,flase 表示是否验证证书
parm2 serialNumber 验证证书的序列号
*/
function validCert(isValid,serialNumber,objActivex){

	if(!isValid){
		return true;
	}else{
		if(isValid=='null'||isValid=='undefined'){
			alert("是否需要验证证书函数有误,请联系相关技术负责人");
			return false;
		}else if(serialNumber=='null'||serialNumber=='undefined'){
			alert("有申请证书,但没带证书,请导入数字证书");
			return false;
		}else if(objActivex=='null'||objActivex=='undefined'){
			alert("证书控件有误,请联系相关技术负责人");
			return false;
		}else if(isValid){
			return 	objActivex.CheckLocalCert2QM(serialNumber);
		}
	}
}
function ImportPFXCert(objActivex,path,pwd){
	if(path==null||path=='undefined'){
		//alert("传入2参数有误");
		return false;
	}else if(pwd==null||pwd=='undefined'){
		//alert("传入3参数有误");
		return false;
	}else if(objActivex==null||objActivex=='undefined'){
		//alert("传入1参数有误");
		return false;
	}else if(objActivex.ImportPFXCert!='undefined'){
		return objActivex.ImportPFXCert(path,pwd);
	}else{
		return false;
	}
}


function ExcuteShellCommand(objActivex,vtCommand,vtParent){
	if(objActivex==null||objActivex=='undefined'){
		alert("传入1参数有误");
		return false;
	}else if(vtParent==null||vtParent=='undefined'){
		alert("传入2参数有误");
		return false;
	}else{	
		return objActivex.ExcuteShellCommand(vtCommand,vtParent);		
	}	
}	
