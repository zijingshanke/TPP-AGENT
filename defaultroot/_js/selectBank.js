//path:_js/selectBank.js
//description:act on selectBank.jsp
//author:Yan Rui
//

//set bank and version value
function setBankVersionValue() {
	var bankNotice = document.getElementById("bankNotice");
	var divPersonal = document.getElementById("versionOfPersona1");
	var divEnterprise = document.getElementById("versionOfEnterprise");
	if (divEnterprise.style.display == "") {
		var bank1 = document.getElementsByName("bank1");//enterprise
		for (var i = 0; i < bank1.length; i++) {
			if (bank1[i].checked && i >= 4) {
				bankNotice.innerHTML = "\u4f60\u9009\u62e9\u7684\u94f6\u884c\u652f\u4ed8\u4e1a\u52a1\u5c1a\u672a\u5f00\u901a";
				return false;
			} else {
				if (bank1[i].checked && bank1[i].value != "") {
					document.forms[0].bank.value = bank1[i].value;
					document.forms[0].version.value = document.forms[0].bank.value + "B2B";
					return true;
				}
			}
		}
	} else {
		var bank0 = document.getElementsByName("bank0");//personal		
		for (var i = 0; i < bank0.length; i++) {
			if (bank0[i].checked && i > 20) {
				bankNotice.innerHTML = "\u4f60\u9009\u62e9\u7684\u94f6\u884c\u652f\u4ed8\u4e1a\u52a1\u5c1a\u672a\u5f00\u901a";
				return false;
			} else {
				if (bank0[i].checked && bank0[i].value != "") {
					var bank0Value = bank0[i].value;
					//alert(bank0Value);
					var indexFlag = bank0Value.lastIndexOf("ChinaPay");
					//alert(indexFlag);
					if (indexFlag > 0) {
					//if (bank0[i].value == "CMBCChinaPay") {
						document.forms[0].bank.value = "CMBC";
						document.forms[0].version.value = "CMBCChinaPay";
						//alert("bank:" + document.forms[0].bank.value);
						//alert("version:" + document.forms[0].version.value);
						return true;
					} else {
						document.forms[0].bank.value = bank0[i].value;
						document.forms[0].version.value = document.forms[0].bank.value + "B2C";
						return true;
					}
				}
			}
		}
	}
}


//set bank and version value
function setBankVersionValue2() {
	alert("setBankVersionValue2");
	var bankNotice = document.getElementById("bankNotice");
	var divPersonal = document.getElementById("versionOfPersona1");
	var divEnterprise = document.getElementById("versionOfEnterprise");
	if (divEnterprise.style.display == "") {
		var bank1 = document.getElementsByName("bank1");//enterprise
		for (var i = 0; i < bank1.length; i++) {
			if (bank1[i].checked && i >= 0) {
				bankNotice.innerHTML = "\u4f60\u9009\u62e9\u7684\u94f6\u884c\u652f\u4ed8\u4e1a\u52a1\u5c1a\u672a\u5f00\u901a";
				return false;
			} else {
				if (bank1[i].checked && bank1[i].value != "") {
					document.getElementById("version") = "B2B";
					return true;
				}
			}
		}
	} else {
		alert("--22222");
		var bank0 = document.getElementsByName("bank0");//personal		
		for (var i = 0; i < bank0.length; i++) {
			if (bank0[i].checked && i > 5) {
				bankNotice.innerHTML = "\u4f60\u9009\u62e9\u7684\u94f6\u884c\u652f\u4ed8\u4e1a\u52a1\u5c1a\u672a\u5f00\u901a";
				return false;
			} else {
				alert("--33333");
				if (bank0[i].checked && bank0[i].value != "") {
					document.getElementById("version") = "B2C";
					document.getElementById("bank") = bank0[i].value;
					//return true;
				}
			}
		}
	}
}

//select version of bank
function selectVersion(version) {
	var bankNotice = document.getElementById("bankNotice");
	var divPersonal = document.getElementById("versionOfPersona1");
	var divEnterprise = document.getElementById("versionOfEnterprise");
	bankNotice.innerHTML = "";
	if (version == 0) {
		document.getElementById("bank_CCB").style.display = "";
		divPersonal.style.display = "";
		divEnterprise.style.display = "none";
	} else {
		if (version == 1) {
			document.getElementById("bank_CCB").style.display = "none";
			document.getElementById("bank_ABC").style.display = "none";
			document.getElementById("bank_ICBC").style.display = "none";
			document.getElementById("bank_BOC").style.display = "none";
			document.getElementById("bank_CMBC").style.display = "none";
			document.getElementById("bank_CEB").style.display = "none";
			document.getElementById("bank_BCM").style.display = "none";
			document.getElementById("bank_SDB").style.display = "none";
			document.getElementById("bank_GDB").style.display = "none";
			document.getElementById("bank_SPDB").style.display = "none";
			document.getElementById("bank_CMB").style.display = "none";
			document.getElementById("bank_CITIC").style.display = "none";
			divPersonal.style.display = "none";
			divEnterprise.style.display = "";
		}
	}
}

//click personal bank button
function clickPersonal(bankName) {
	initBankNotice();
	var bank = document.getElementsByName("bank0");
	for (var i = 0; i < bank.length; i++) {
		if (bank[i].value == bankName) {
			bank[i].checked = true;
		}
	}
	var shuoming = "bank_" + bankName;
	//alert("shuoming:" + shuoming);
	document.getElementById(shuoming).style.display = "";
}

//init BankNotice
function initBankNotice() {
	document.getElementById("bank_CCB").style.display = "none";
	document.getElementById("bank_ABC").style.display = "none";
	document.getElementById("bank_ICBC").style.display = "none";
	document.getElementById("bank_BOC").style.display = "none";
	document.getElementById("bank_CMBC").style.display = "none";
	document.getElementById("bank_CEB").style.display = "none";
	document.getElementById("bank_BCM").style.display = "none";
	document.getElementById("bank_SDB").style.display = "none";
	document.getElementById("bank_GDB").style.display = "none";
	document.getElementById("bank_SPDB").style.display = "none";
	document.getElementById("bank_CMB").style.display = "none";
	document.getElementById("bank_CITIC").style.display = "none";
	document.getElementById("bank_CIB").style.display = "none";
	document.getElementById("bank_PSBC").style.display = "none";
	document.getElementById("bank_HXB").style.display = "none";
	document.getElementById("bank_CBHB").style.display = "none";
	document.getElementById("bank_ZHNX").style.display = "none";
	document.getElementById("bank_JSNX").style.display = "none";
	document.getElementById("bank_YDXH").style.display = "none";
	document.getElementById("bank_JCCB").style.display = "none";
	document.getElementById("bank_WZCB").style.display = "none";
	document.getElementById("bank_NBCB").style.display = "none";
	document.getElementById("bank_HKB").style.display = "none";
	document.getElementById("bank_FDB").style.display = "none";
}

//click enterprise bank button
function clickEnterprise(bankName) {
	document.getElementById("bank_CCB").style.display = "none";
	document.getElementById("bank_ABC").style.display = "none";
	document.getElementById("bank_ICBC").style.display = "none";
	document.getElementById("bank_BOC").style.display = "none";
	document.getElementById("bank_CMBC").style.display = "none";
	document.getElementById("bank_CEB").style.display = "none";
	document.getElementById("bank_BCM").style.display = "none";
	document.getElementById("bank_SDB").style.display = "none";
	document.getElementById("bank_GDB").style.display = "none";
	document.getElementById("bank_SPDB").style.display = "none";
	document.getElementById("bank_CMB").style.display = "none";
	document.getElementById("bank_CITIC").style.display = "none";
	var bank = document.getElementsByName("bank1");//企业版
	for (var i = 0; i < bank.length; i++) {
		if (bank[i].value == bankName) {
			bank[i].checked = true;
		}
	}
	var shuoming = "bank_" + bankName;
	if (bankName == "CCB") {
		document.getElementById("showBank").innerHTML = "\u4e2d\u56fd\u5efa\u8bbe\u94f6\u884c\u4f01\u4e1a\u7f51\u94f6";
	}
	if (bankName == "ICBC") {
		document.getElementById("showBank").innerHTML = "\u4e2d\u56fd\u5de5\u5546\u94f6\u884c\u4f01\u4e1a\u7f51\u94f6";
	}
	if (bankName == "BCM") {
		document.getElementById("showBank").innerHTML = "\u4e2d\u56fd\u4ea4\u901a\u94f6\u884c\u4f01\u4e1a\u7f51\u94f6";
	}
	if (bankName == "ABC") {
		document.getElementById("showBank").innerHTML = "\u4e2d\u56fd\u519c\u4e1a\u94f6\u884c\u4f01\u4e1a\u7f51\u94f6";
	}
}
function displayAllBank() {
	var row1 = document.getElementById("otherRow1");
	var row2 = document.getElementById("otherRow2");
	var row3 = document.getElementById("otherRow3");
	row1.style.display = "";
	row2.style.display = "";
	row3.style.display = "";
}
function displayNoFiring() {
	var row11 = document.getElementById("otherRow11");
	var row12 = document.getElementById("otherRow12");
	row11.style.display = "";
	row12.style.display = "";
}



//if user selected personal 'ICBC'
//remark:cooperate/gateway.jsp
function isICBC() {
	var perICBC = document.getElementById("perICBC");
	if (perICBC.checked) {
		return true;
	} else {
		return false;
	}
}





//show notice Of Bank,this page need  import bank/noticeOfBank.jsp first
function showNoticeOfBank() {
}

