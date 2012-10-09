<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<script type="text/javascript">
var $ = function (id) {
    return "string" == typeof id ? document.getElementById(id) : id;
};
var Class = {
  create: function() {
    return function() {
      this.initialize.apply(this, arguments);
    }
  }
}
Object.extend = function(destination, source) {
    for (var property in source) {
        destination[property] = source[property];
    }
    return destination;
}
function addEventHandler(oTarget, sEventType, fnHandler) {
    if (oTarget.addEventListener) {
        oTarget.addEventListener(sEventType, fnHandler, false);
    } else if (oTarget.attachEvent) {
        oTarget.attachEvent("on" + sEventType, fnHandler);
    } else {
        oTarget["on" + sEventType] = fnHandler;
    }
};

var Scroller = Class.create();
Scroller.prototype = {
  initialize: function(idScroller, idScrollMid, options) {
    var oScroll = this, oScroller = $(idScroller), oScrollMid = $(idScrollMid);
    
    this.heightScroller = oScroller.offsetHeight;
    this.heightList = oScrollMid.offsetHeight;
    
    if(this.heightList <= this.heightScroller) return;
    
    oScroller.style.overflow = "hidden";
    oScrollMid.appendChild(oScrollMid.cloneNode(true));
    
    this.oScroller = oScroller;    
    this.timer = null;
    
    this.SetOptions(options);
    
    this.side = 1;//1是上 -1是下
    switch (this.options.Side) {
        case "down" :
            this.side = -1;
            break;
        case "up" :
        default :
            this.side = 1;
    }
    
    addEventHandler(oScrollMid , "mouseover", function() { oScroll.Stop(); });
    addEventHandler(oScrollMid , "mouseout", function() { oScroll.Start(); });
    
    if(this.options.PauseStep <= 0 || this.options.PauseHeight <= 0) this.options.PauseStep = this.options.PauseHeight = 0;
    this.Pause = 0;
    
    this.Start();
  },
  //设置默认属性
  SetOptions: function(options) {
    this.options = {//默认值
      Step:            1,//每次变化的px量
      Time:            20,//速度(越大越慢)
      Side:            "up",//滚动方向:"up"是上，"down"是下
      PauseHeight:    0,//隔多高停一次
      PauseStep:    1000//停顿时间(PauseHeight大于0该参数才有效)
    };
    Object.extend(this.options, options || {});
  },
  //滚动
  Scroll: function() {
    var iScroll = this.oScroller.scrollTop, iHeight = this.heightList, time = this.options.Time, oScroll = this, iStep = this.options.Step * this.side;
    
    if(this.side > 0){
        if(iScroll >= (iHeight * 2 - this.heightScroller)){ iScroll -= iHeight; }
    } else {
        if(iScroll <= 0){ iScroll += iHeight; }
    }
    
    if(this.options.PauseHeight > 0){
        if(this.Pause >= this.options.PauseHeight){
            time = this.options.PauseStep;
            this.Pause = 0;
        } else {
            this.Pause += Math.abs(iStep);
            this.oScroller.scrollTop = iScroll + iStep;
        }
    } else { this.oScroller.scrollTop = iScroll + iStep; }
    
    this.timer = window.setTimeout(function(){ oScroll.Scroll(); }, time);
  },
  //开始
  Start: function() {
    this.Scroll();
  },
  //停止
  Stop: function() {
    clearTimeout(this.timer);
  }
};
window.onload = function(){
    new Scroller("idScroller", "idScrollMid",{ PauseHeight:25 });
}
</script>
<style>
#idScroller *{margin:0px; padding:0px;font-size: 12px;color:#005B9B;}
#idScroller{line-height:25px;width:150px; height:80px; overflow:hidden; }
#idScroller ul{width:100%}
#idScroller li{width:100%;  float:left; overflow:hidden; list-style:none;}
.leftbox_bl {font-size: 12px;color:#005B9B;border-right:1px solid #cccccc;}
</style>
  <div class="leftbox_bl">
            钱门动态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/information/newslist.do?thisAction=listNews" style="color:#FF8040;" target="_blank">更多>>></a> <br/>
           <A style="color:#FF8040;" target="_blank" href="<%=request.getContextPath()%>/information/newslist.do?thisAction=showNews&id=<c:out value='${Latest.id}'/>"><c:out value="${Latest.tmpeTitle}"></c:out></A><img src="<%=request.getContextPath()%>/_img/zuixin.gif" />	
<div id="idScroller">
  <div id="idScrollMid">
    <c:forEach var="news" items="${nlf.list}">
    <ul>
      <li><a style="color:#FF8040;" target="_blank" href="<%=request.getContextPath()%>/information/newslist.do?thisAction=showNews&id=<c:out value='${news.id}'/>"><c:out value="${news.tmpeTitle}"/></a></li>
    </ul>
     </c:forEach> 
    <div style="clear:both;"></div>
  </div>
</div>
 </div>           
           
 