 var ValidCerfication = Class.create();

    ValidCerfication.prototype = {

      initialize: function(email) {  
      	
        this.email=email;
      		
       },

      validSSL:function()
      { 
      	alert("valid............."+this.email);
      if(this.email.length>0 && this.email.indexOf('@')>0)
	  {

		var myAjax = new Ajax.Request("https://qm.qmpay.com/security/certificate.do?thisAction=validAgent&email="+this.email,
		{ 
			thisAction:"post", 
			onComplete:function (originalRequest)
			{
			  var result = originalRequest.responseText;
			  if(result==1)
			  {
				return true;
		      }
			  else
			  {
				return false;	
		      }
		    }, 
		    onException:function(originalRequest, ex)
	        {
	          alert("Exception:" + ex.message);
	          return  ex.message;	
	        }
	    }
	    );
	 }   	
     }
    };
    
  
  
 
