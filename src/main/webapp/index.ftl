<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Phantomjs Snow</title>
    
    <link rel="stylesheet" type="text/css" href="${_r.contextPath}/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" type="text/css" href="${_r.contextPath}/bootstrap/css/bootstrap-responsive.css" />
    <link rel="stylesheet" type="text/css" href="${_r.contextPath}/css/imports-all.less.css">
    
    <script type="text/javascript">
        var contextPath = "${_r.contextPath}";
    </script>
    
    [@webBundle path="/js/" type="js" /]
    
    
    [#-- Global Initialization --] 
    <script type="text/javascript">
      // set the default to load the template
      brite.defaultComponentConfig.loadTmpl = true;
    </script>
    [#-- /Global Initialization --] 
    	
  </head>

  <body>
  	<div id="bodyPage">
  		<div id="content">
  			<label>Report Type: Batch</label>
  			<label>Section: Overview</label>
  			<label>View: Summary view</label>
  			<button id="exportChartBtn" class="btn btn-primary">Export Chart</button>
  		</div>
  	</div>
  	<script type="text/javascript">
		$(function(){
			$("#exportChartBtn").click(function(){
				console.log("---click----")
				var jsFileName = "resources/smr.js";
				app.ajaxRequest(app.host + "/exportChart", {jsFileName: jsFileName}, "POST").pipe(function(val){
					console.log("----------")
					return val.result;
				});
			});
		});
	</script>
  </body>
</html>