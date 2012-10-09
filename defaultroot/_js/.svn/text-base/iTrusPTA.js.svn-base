var miniPTAVersion=0x0204;
var cabPath="/download/1007/aliedit.cab";

var iTrusPTA;
try{
//make cat path
var script=document.scripts("iTrusPTAScript");
if(script==null)
        alert("debug: iTrusPTA.js should be use with a id 'iTrusPTAScript'.\n Example: <script id=iTrusPTAScript src=...>");
var m=script.src.match(/^http:\/\/[^\/]*/i);
if(m) cabPath=m+cabPath;

//create pta
iTrusPTA=new ActiveXObject("PTA.iTrusPTA");
}catch(e){
}

if(iTrusPTA==null || iTrusPTA.Version<miniPTAVersion){
        var strVersion=(miniPTAVersion>>8)+","+(miniPTAVersion & 0xff)+",0,0";
        document.write('<OBJECT id="iTrusPTA1" codebase="'+cabPath+'#version='+strVersion+'" classid="clsid:1E0DFFCF-27FF-4574-849B-55007349FEDA" onreadystatechange="javascript:onPTAReady();"></OBJECT>');
}

function onPTAReady(){
	if(iTrusPTA1.readyState!=4)
		return;
	var	pta=iTrusPTA1.object;
	if(pta==null){
		alert("支付宝控件安装失败！");
		return;
	}
	if(pta.Version<miniPTAVersion){
		alert("支付宝控件版本过低!");
		return;
	}
	iTrusPTA=pta;
}

function getSingleCert(issuer,serial){
	var filter=iTrusPTA.Filter;
	filter.Clear();
	if(issuer.length>0)
		filter.Issuer=issuer;
	if(serial.length>0)
		filter.SerialNumber=serial;
	if(iTrusPTA.MyCertificates.Count==0){
		return null;
	}
	return iTrusPTA.MyCertificates(0);
}
