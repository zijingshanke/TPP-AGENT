function check(ba)
  {
  
	document.getElementById("bank_CCB").style.display="none";
  	document.getElementById("bank_ABC").style.display="none";
  	document.getElementById("bank_ICBC").style.display="none";
  	document.getElementById("bank_BOC").style.display="none";
  	document.getElementById("bank_CMBC").style.display="none";
  	document.getElementById("bank_CEB").style.display="none";
  	document.getElementById("bank_BCM").style.display="none";
  	document.getElementById("bank_SDB").style.display="none";
  	document.getElementById("bank_GDB").style.display="none";
  	document.getElementById("bank_SPDB").style.display="none";
  	document.getElementById("bank_CMB").style.display="none";
  	document.getElementById("bank_CITIC").style.display="none";
	var bank=document.getElementsByName("bank0");
	for(var i=0;i<bank.length;i++) 
	{  
        if(bank[i].value==ba)
        bank[i].checked=true;  
    } 
    var shuoming="bank_"+ba;
    document.getElementById(shuoming).style.display="";
  }
  
  function checkOfPersona(ba)
  {
	document.getElementById("bank_CCB").style.display="none";
  	document.getElementById("bank_ABC").style.display="none";
  	document.getElementById("bank_ICBC").style.display="none";
  	document.getElementById("bank_BOC").style.display="none";
  	document.getElementById("bank_CMBC").style.display="none";
  	document.getElementById("bank_CEB").style.display="none";
  	document.getElementById("bank_BCM").style.display="none";
  	document.getElementById("bank_SDB").style.display="none";
  	document.getElementById("bank_GDB").style.display="none";
  	document.getElementById("bank_SPDB").style.display="none";
  	document.getElementById("bank_CMB").style.display="none";
  	document.getElementById("bank_CITIC").style.display="none";
	var bank=document.getElementsByName("bank1");
	for(var i=0;i<bank.length;i++) 
	{  
        if(bank[i].value==ba)
        bank[i].checked=true;  
    }
    var shuoming="bank_"+ba;
     if(ba=='CCB'){
    	document.getElementById("showBank").innerHTML="中国建设银行企业网银";
    }
    if(ba=='ICBC'){
    	document.getElementById("showBank").innerHTML="中国工商银行企业网银";
    }
    if(ba=='BCM'){
    	document.getElementById("showBank").innerHTML="中国交通银行企业网银";
    }
    if(ba=='ABC'){
    	document.getElementById("showBank").innerHTML="中国农业银行企业网银";
    }
    
  }

var companyName = "<c:out value="${agent.companyName}"/>";
var pr=document.getElementsByName("banben");

 	for(var i=0;i<pr.length;i++){
 	if(companyName!=""){
        pr[1].checked=true;
        sleVersion(2);
        }
    else{
    	pr[0].checked=true;
    	sleVersion(1);
    	}     
}