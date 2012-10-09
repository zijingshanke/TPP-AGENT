	function WeekDate() {

    /**
     * 基准时间，所有计算以此为基础
     */
    var _calcDate = new Date();

    /**
     * 一天的豪秒数
     */
    var _day = 1000 * 60 * 60 * 24;

    this.getThisWeekDate = getThisWeekDate;
    this.getPrevWeekDate = getPrevWeekDate;
    this.getNextWeekDate = getNextWeekDate;
    this.wrapDate = wrapDate;

    this.getDayMillisecond = getDayMillisecond;

    /**
     * 取上周开始至上周结束日期
     *
     * @return Array [0]上周第一天 [1]上周最后一天
     */
    function getPrevWeekDate() {
        // 取上周结束日期
        var lastDay = new Date(_calcDate - (_calcDate.getDay()) * _day);
        // 取上周开始日期
        var firstDay = new Date((lastDay * 1) - 6 * _day);
        // 更新基准时间
        _calcDate = firstDay;

        return [wrapDate(firstDay), wrapDate(lastDay)];
    }

    /**
     * 取下周开始至下周结束日期
     *
     * @return Array [0]上周第一天 [1]上周最后一天
     */
    function getNextWeekDate() {
        // 取下周开始日期
        var firstDay = new Date((_calcDate * 1) + (6 - _calcDate.getDay() + 2) * _day);
        // 取下周结束日期
        var lastDay = new Date((firstDay * 1) + 6 * _day);
        // 更新基准时间        
        _calcDate = firstDay;

        return [wrapDate(firstDay), wrapDate(lastDay)];
    }

    /**
     * 取本周开始至本周结束日期
     *
     * @return Array [0]本周第一天 [1]本周最后一天
     */
    function getThisWeekDate() {
        _calcDate = new Date();
        // 第一天日期
        var firstDay = new Date(_calcDate - (_calcDate.getDay() - 1) * _day);
        // 最后一天日期
        var lastDay = new Date((firstDay * 1) + 6 * _day);

        return [wrapDate(firstDay), wrapDate(lastDay)];
    }

    function wrapDate($date) {
        var m = $date.getMonth() + 1;
        m = m < 10 ? "0" + m : m;

        var d = $date.getDate();
        d = d < 10 ? "0" + d : d;

        return $date.getFullYear() + "-" + m + "-" + d;            
    }

    function getDayMillisecond() {
        return _day;
    }
}
	
	 function gaibian(z){
        var weekDate = new WeekDate();
  		var txt=document.getElementById("dateType");
  		var cda=document.getElementById("selectDealDate");
 		document.getElementById("dateDiv").style.display ="none";
  		var cda=z;
  	   
  		var today   =   new   Date()
  		var YY = today.getYear();//获取年份
	    var MM = today.getMonth()+1;//获取月份
	    var DD = today.getDate();//获取日
	    var HH = today.getHours();//获得时
	    var NN = today.getMinutes();//获得分
	    var SS = today.getSeconds();//获得秒
	    
	    YY = (YY<1900?(1900+YY):YY);//处理火狐浏览器年份乱码问题
	    if(MM<10){
	    	MM="0"+MM;
	    }
	    if(DD<10){
	    	DD="0"+DD;
	    }
	    if(HH<10){
	    	HH="0"+HH;
	    }
	    if(NN<10){
	    	NN="0"+NN;
	    }
	    if(SS<10){
	    	SS="0"+SS;
	    }
	    if(cda=="1"){
	       
	       txt.value="当日";
	    	document.getElementById("beginDate").value=YY+"-"+MM+"-"+DD+" 00:00:00";
	    	document.getElementById("endDate").value=YY+"-"+MM+"-"+DD+" 23:59:59";
	    }
	    if(cda=="2"){
	        
	        txt.value="本月";
	    	document.getElementById("beginDate").value=YY+"-"+MM+"-01 00:00:00";
	    	document.getElementById("endDate").value=YY+"-"+MM+"-"+DD+" 23:59:59";
	    }
	    if(cda=="3"){
	        txt.value="上月";
	   	var d = new Date();
            d.setMonth(d.getMonth() - 1); 
	    	NM=(d.getMonth()+1);
	    	if(NM<10){
	    		NM="0"+NM;
	    	}
	    	document.getElementById("beginDate").value=(d.getFullYear())+"-"+NM+"-01 00:00:00";
	    	document.getElementById("endDate").value=YY+"-"+MM+"-01 00:00:00";
	    }
	    
	    if(cda=="4"){
	        txt.value="全部";
	    	document.getElementById("beginDate").value="";
	    	document.getElementById("endDate").value="";
	    }
	      if(cda=="5"){
	        var range = weekDate.getPrevWeekDate();
	        txt.value="上周";
	    	document.getElementById("beginDate").value=range[0]+" 00:00:00";
	    	document.getElementById("endDate").value=range[1]+" 23:59:59";
	    }
	    
	        if(cda=="6"){
	         var range = weekDate.getThisWeekDate();
	        txt.value="本周";
	    	document.getElementById("beginDate").value=range[0]+" 00:00:00";
	    	document.getElementById("endDate").value=YY+"-"+MM+"-"+DD+" 23:59:59";
	    }
	    
	 
  }


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	