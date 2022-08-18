
Ext.define('Admin.view.Controller', {
	extend: 'Ext.app.ViewController',
	alias: 'controller.allController',

	doShort: function(btn, eOpts){

		var longUrl = this.lookupReference('longUrl').value;

		Ext.getBody().mask('Please wait...');

		this.doAjaxRequest(this, longUrl);

	},

	doAjaxRequest : function(cmp, longUrl) {

		var requestBody = {
        longUrl: longUrl
    };

    var requestJsonBody = JSON.stringify(requestBody, replacer);

    Ext.Ajax.request({
      url     : 'http://localhost:8080/url-shortener-server/create-short-url',
      method  : 'POST',
      params  : requestJsonBody,
      headers: {
				'Content-Type' : 'application/json' 
			},
      success : function(result, request){
      	Ext.getBody().unmask();
        cmp.lookupReference('shortUrl').setValue(result.responseText);
      },
      failure : function(result, request){
        Ext.getBody().unmask();  
        cmp.lookupReference('shortUrl').setValue(eval("(" + result.responseText + ")").reason);
      }
  	});
	}
});

function replacer(key, value) {

	if (typeof value === 'number' && !isFinite(value)) {
		return String(value);
	}

	return value;
}