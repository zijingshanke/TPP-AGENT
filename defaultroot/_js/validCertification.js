 var ValidCerfication = Class.create();

    ValidCerfication.prototype = {

      initialize: function(email) {  
      	alert("valid.............");
        this.email=email;
      		
       },

      valid:function()
      { 
      	
      if(email.length>0 && email.indexOf('@')>0)
	  {

		var myAjax = new Ajax.Request("https://security/CertificateAction.do?thisAction=validAgent&email="+email,
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
	        }
	    }
	    );
	 }   	
     }
    };
    
  
  
 
