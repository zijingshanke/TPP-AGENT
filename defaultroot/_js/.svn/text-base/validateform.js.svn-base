/*
版    本：V1.0
作    者：winkee
电子邮件：weiwei@winkee.net
还有很多需要完善的地方，大家多多反馈。

验证规则

    notnull                         --------  不能为空

    equal:field                     --------  判断当前元素的值与field的值是否相等，通常用于密码确认

    length:[0]:[1]                  --------  字符串长度[0]到[1]之间
    zhlength:[0]:[1]                --------  中文字符串长度[0]到[1]之间

    number:[0]:[1]                  --------  可以为空的数字[0]到[1]之间
    numbernotnull:[0]:[1]           --------  不可为空的数字[0]到[1]之间

    double:[0]:[1]:[2]              --------  (修改过,可以为整数也可以为小数)为可以为空的浮点数[0]到[1]之间，[2]位小数，不填表示不限制位数，至少一位小数
    doublenotnull:[0]:[1]:[2]       --------  不可为空的浮点数[0]到[1]之间，[2]位小数，不填表示不限制位数，至少一位小数

    integer:[0]:[1]                 --------  可以为空的整数[0]到[1]之间
    integernotnull:[0]:[1]          --------  不可为空的整数[0]到[1]之间

    date:[0]:[1]                    --------  可以为空的日期格式[0]到[1]之间
    datenotnull:[0]:[1]             --------  不可为空的日期格式[0]到[1]之间

    charornum:[0]:[1]               --------  可以为空的数字，字母及下划线，长度在[0]到[1]之间
    charornumnotnull:[0]:[1]        --------  不可为空的数字，字母及下划线，长度在[0]到[1]之间

    email                           --------  可以为空的email格式
    emailnotnull                    --------  不可以为空的email格式

    zipcode                         --------  可以为空的邮政编码格式
    zipcodenotnull                  --------  不可以为空的邮政编码格式

    ip                              --------  可以为空的IP地址格式
    ipnotnull                       --------  不可以为空的IP地址格式

    idcard:15/18                    --------  可以为空的身份证号码格式15位或18位，没有参数表示15位或18位都可以
    idcardnotnull:15/18             --------  不可以为空的身份证号码格式15位或18位，没有参数表示15位或18位都可以
*/


// 验证元素的标题和提示信息
var title = "";
var message = "";

function validate()
{
	var showmessage = element.getAttribute("showmessage");

}
function validateForm(theForm)
{
    // 得到当前表单
    var frmElements = theForm.elements;
	
	
    for(var i = 0; i < frmElements.length; i++)
    {
        // 获取提示标题
        title = frmElements[i].title;

        if (title == null || title == "")
        {
            title = "此";
        }

        // 根据规则验证
        if ( !checkElement( frmElements[i] ) )
        {
            message += "，请重新输入！";

            if( frmElements[i].type == "text"
             || frmElements[i].type == "password"
             || frmElements[i].type == "textarea"
             || frmElements[i].type == "select"  )
            {
                frmElements[i].focus();
            }
            return false;
        }
    }

    return true;
}

function checkElement(element)
{
    // 获取其检验规
    var rule = element.getAttribute("rule");
    // 如果该属性不存在，忽略当前元素
    if ( rule == null )
    {
        return true;
    }

    rule = rule.toLowerCase();

    // 获取该元素的值
    var value = element.value;

    var array = rule.split(":");

    var caseStr = array[0];

    // 如含有notnull，取出notnull
    var index = array[0].indexOf("notnull");

    if ( index != -1 && index != 0 )
    {
        caseStr = array[0].substring(0,index);
    }

    // 具体判断
    var rtn = false;

    switch (caseStr)
    {
        // 判断非空
        case "notnull":
            rtn = !checkEmpty(value);
            break;

        // 判断简单字符串长度
        case "length":
            rtn = checkLength(value,array);
            break;

        // 判断中英文混和字符串长度
        case "zhlength":
            rtn = checkZHLength(value,array);
            break;

        // 判断数字
        case "number":
            rtn = checkNumber(value,array);
            break;

        // 判断浮点数
        case "double":
            rtn = checkDouble(value,array);
            break;

        // 判断电子邮件
        case "email":
            rtn = checkEmail(value,array);
            break;

        // 判断日期
        case "date":
            rtn = checkDate(value,array);
            break;

        // 判断身份证号码
        case "idcard":
            rtn = checkIDCard(value,array);
            break;

        // 判断邮政编码
        case "zipcode":
            rtn = checkZipCode(value,array);
            break;

        // 判断IP地址
        case "ip":
            rtn = checkIP(value,array);
            break;

        // 判断整数
        case "integer":
            rtn = checkInteger(value,array);
            break;

        // 判断数字，字母及下划线
        case "charornum":
            rtn = checkCharOrNum(value,array);
            break;

        // 判断数字，字母及下划线
        case "equal":
            rtn = checkEqual(value,array);
            break;
		//判断手机,以13,15,18开头的
		case "mobile":
			rtn = mobile(value);
			break;
        default:
            rtn = true;
            break;
    }

    return rtn;
}

// 调用正则表达式
function check( reg, str )
{
    if( reg.test( str ) )
    {
        return true;
    }

    return false;
}
//手机号码
function mobile(str)
{
	message = "手机号码不正确!";
	if((/^13\d{9}$/g.test(str))||(/^15\d{9}$/g.test(str)) ||(/^18\d{9}$/g.test(str))){
		return true;
	}
	else
		return false;
}
// 空字符串
function checkEmpty( str )
{
    message = title + "项不能为空";
    return ( str == "" );
}

// 判断两个元素值是否相等
function checkEqual(value,array)
{
    message = title + "不相等";

    if (array[1] != null && array[1] != "")
    {
        var e = document.all(array[1]).value;
        return ( value == e );
    }

    return true;
}

// 数字
function checkNumber( str, array )
{
    // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    message = title + "项只能是数字";

    var reg = /^-?[0-9]*\.?[0-9]*$/;

    if ( check( reg, str ) )
    {
        // 最小数和最大数判断
        return judgeMin(str, array[1]) && judgeMax(str, array[2]);
    }

    return false;
}

// 带小数位数的Double
function checkDouble( str, array )
{
    // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    if ( str == "" )
    {
        return true;
    }

    message = title + "项不符合格式";

 //   var exp = "^-?[0-9]\\.?[0-9]$";
	
      var exp = "^(-?\\d+)(\\.\\d+)?$" ;
//	alert(exp);

    // 判断小数点长度
//    if ( array[3] != null && array[3] != "" )
//    {
//        exp = "^-?[0-9]*\\.[0-9]{" + array[3] + "}$";
//   }

    var reg = new RegExp(exp);

    if ( check( reg, str ) )
    {
        // 最小数和最大数判断
        return judgeMin(str, array[1]) && judgeMax(str, array[2]);
    }

    return false;
}

// 整数
function checkInteger( str, array )
{
    // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    if (str == "")
    {
        return true;
    }

    message = title + "项只能是整数";

    var reg = /^-?\d+$/;

    if ( check( reg, str ) )
    {
        // 最小数和最大数判断
        return judgeMin(str, array[1]) && judgeMax(str, array[2]);
    }

    return false;
}

// 身份证号码
function checkIDCard( str, array )
{
    // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    if (str == "")
    {
        return true;
    }

    message = title + "输入不正确";

    // 有要去身份证号码位数
    if (array[1] != null && array[1] != "")
    {
        // 15位身份证号码
        if (array[1] == 15)
        {
            return checkNum15(str);
        }
        else if (array[1] == 18)
        {
            return checkNum18(str);
        }
    }

    return checkNum18(str) || checkNum15(str);
}

// 由数字、26个英文字母或者下划线
function checkCharOrNum( str,array )
{
    // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    if ( str == "" )
    {
        return true;
    }

    message = title + "项不符合格式";

    var reg = /^\w+$/;

    if ( check( reg, str ) )
    {
        // 判断最小长度和最大长度
        return judgeMinLength( str.length, array[1] ) && judgeMaxLength( str.length, array[2] );
    }

    return false;
}

// 手机号/小灵通号
function checkMobile( str,array )
{
    var regMobile = /^\d{11,12}$/;

    return check( regMobile, str );
}

// 邮编
function checkZipCode( str, array )
{
    // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    if (str == "")
    {
        return true;
    }

    message = title + "项邮政编码不符合格式";

    var reg = /^\d{6}$/;

    return check( reg, str );
}

// IP地址
function checkIP( str, array )
{
    // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    if (str == "")
    {
        return true;
    }

    message = title + "项IP地址不符合格式";

    var ip = "(25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)";
    var ipDot = ip + "\\.";
    ip = "^" + ipDot + ipDot + ipDot + ip + "$";
    var reg = new RegExp(ip);

    return check( reg, str );
}

// Email
function checkEmail( str, array )
{
    // 有非空要求
    if (!judgeEmpty(str, array[0]) )
    {
        return false;
    }

    message = title + "项不符合邮件地址格式";

    var reg = /^([a-zA-Z0-9]+[-|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;

    if ( str != "")
    { 
         if( str.match(reg) )
	    {
	        return true;
	    }	
	    return false;
    }

    return false;
}

// 检查是不是日期格式
function checkDate( date, array )
{
    // 有非空要求
    if ( !judgeEmpty(date, array[0]) )
    {
        return false;
    }

    // 可以为空
    if ( date == "" )
    {
        return true;
    }

    message = title + "项不符合日期格式";

    var reg = /^(\d{4})([\/,-])(\d{1,2})\2(\d{1,2})$/;
    var r = date.match(reg);

    if ( r == null )
        return false;

    var d = new Date(r[1], r[3]-1,r[4]);
    var newDate = d.getFullYear() + r[2] + ( d.getMonth() +1 ) + r[2] + d.getDate();

    date = r[1] + r[2] + (( r[3] - 1 ) + 1) + r[2] + ( ( r[4] - 1 ) + 1 );

    // 符合日期格式，判断是否在最大和最小日期之间
    if ( newDate == date )
    {
        // 判断最小日期
        if ( array[1] != null && array[1] != "" )
        {
            var min = array[1].match(reg);

            if ( min != null)
            {
                var minDate = new Date(min[1], min[3]-1,min[4]);
                if ( d.getTime() < minDate.getTime() )
                {
                    message = title + "不能小于" + array[1];
                    return false;
                }
            }
        }

        // 判断最大日期
        if ( array[2] != null && array[2] != "" )
        {
            var max = array[2].match(reg);

            if ( max != null)
            {
                var maxDate = new Date(max[1], max[3]-1,max[4]);
                if ( d.getTime() > maxDate.getTime() )
                {
                    message = title + "不能大于" + array[2];
                    return false;
                }
            }
        }

        return true;
    }

    return false;
}

// 判断简单字符串长度
function checkLength(  str, array )
{
     // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    // 判断最小长度和最大长度
    return judgeMinLength( str.length, array[1] ) && judgeMaxLength( str.length, array[2] );
}

// 判断中英文混和字符串长度
function checkZHLength(  str, array )
{
     // 有非空要求
    if ( !judgeEmpty(str, array[0]) )
    {
        return false;
    }

    // 判断最小长度和最大长度
    return judgeMinLength( str.getZHLength(), array[1] ) && judgeMaxLength( str.getZHLength(), array[2] );
}

// 判断最小长度
function judgeMinLength( length1, length2 )
{
    if (length2 != null && length2 != "")
    {
        message = title + "项长度最少为" + length2 + "字符";
        if (length2 == 1)
        {
             message = title + "项不能为空";
        }

        // 输入字符长度小于要求长度
        if ( compareNum(length1,length2) == -1 )
        {
            return false;
        }
    }
    return true;
}

// 判断最大长度
function judgeMaxLength( length1, length2 )
{
    if (length2 != null && length2 != "")
    {
        message = title + "项长度最多为" + length2 + "字符";
        // 要求长度小于输入字符长度
        if ( compareNum( length2, length1 ) == -1 )
        {
            return false;
        }
    }
    return true;
}

// 数值逻辑大小
// num1 > num2 返回1
// num1 = num2 返回0
// num1 < num2 返回-1
function compareNum( num1, num2 )
{
    num1 = parseFloat(num1);
    num2 = parseFloat(num2);

    if( num1 > num2 )
    {
        return 1;
    }

    if( num1 == num2 )
    {
        return 0;
    }

    return -1;
}

// 最小数判断
function judgeMin( num1, num2)
{
    // 最小数
    if (num1 != "" && num2 != "" && num1 != null && num2 != null)
    {
        if ( compareNum(num1,num2) == -1 )
        {
            message = title + "项最小为" + num2;
            return false;
        }
    }

    return true;
}

// 最大数判断
function judgeMax( num1, num2)
{
    // 最大数
    if (num1 != "" && num2 != "" && num1 != null && num2 != null)
    {
        if ( compareNum(num2, num1) == -1 )
        {
            message = title + "项最大为" + num2;
            return false;
        }
    }

    return true;
}

// 非空要求判断
function judgeEmpty( value, rule)
{
    // 有非空要求 且 value 为空
    if ( rule.indexOf("notnull") != -1 && checkEmpty(value) )
    {
        return false;
    }
    return true;
}

// 15位身份证号
function checkNum15( str )
{
    var reg = /^\d{15}$/;

    return check( reg, str );
}

// 18位身份证号
function checkNum18( str )
{
    var reg = /^\d{17}(?:\d|x)$/i;

    return check( reg, str );
}

// 利用prototype为string对象加一个getZHLength()方法，
// 取得中英文混和字符串长度。
String.prototype.getZHLength = function()
{
    var cArr = this.match(/[^\x00-\xff]/ig);
    return this.length + (cArr == null ? 0 : cArr.length);
}