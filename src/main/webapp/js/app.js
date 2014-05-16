var app = app || {};
(function($) {
	
	var host = contextPath;
	app.host = host;
	
	app.render = function(templateName,data){
		data = data || {};
		var idPreFix = "#";
		var $e = $(Handlebars.compile($(idPreFix+templateName).html())(data));
		return $e;
	}
	
	app.ajaxRequest = function(url, params, method) {
		var dfd = $.Deferred();
		params = params || {};
		console.log(url, params, method)
		jQuery.ajax({
			  type : method ? method : "GET",
			  url : url,
			  async : true,
			  data : params,
			  dataType : "json"
		  }).success(function(data) {
			dfd.resolve(data);
		  }).fail(function(jxhr, arg2) {
			try {
				if (jxhr.responseText) {
					console.log(" WARNING: json not well formatted, falling back to JS eval");
					var data = eval("(" + jxhr.responseText + ")");
					dfd.resolve(data);
				} else {
					throw " EXCEPTION: Cannot get content for " + url;
				}
			} catch (ex) {
				console.log(" ERROR: " + ex + " Fail parsing JSON for url: " + url + "\nContent received:\n"
				  + jxhr.responseText);
			}
		});

		return dfd.promise();
	}
})(jQuery);
