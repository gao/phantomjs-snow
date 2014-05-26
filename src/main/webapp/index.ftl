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
  			<div class="part">
	  			<label>Report Type: Batch -> Section: Overview -> View: Summary -> All page </label>
	  			<button class="btn btn-primary exportChartBtn" data-value="batch-overview-summary">Export Chart</button>
			</div>
			<div class="part">
	  			<label>Report Type: Batch -> Section: Overview -> View: Summary -> Only HighChart </label>
	  			<button class="btn btn-primary exportChartBtn" data-value="batch-overview-summary-chart">Export Chart</button>
  			</div>
  			<div class="part">
	  			<label>Report Type: Batch -> Section: Overview -> View: Summary -> All Content </label>
	  			<button class="btn btn-primary exportChartBtn" data-value="batch-overview-summary-all">Export Chart</button>
  			</div>
  			<div class="part">
  				<label>Report Type: Program -> Section: Overview -> View: Summary -> All Content </label>
	  			<button class="btn btn-primary exportChartBtn" data-value="program-overview-summary-all">Export Chart</button>
  			</div>
  			<div class="part">
  				<label>This select report type function only use one js file to export 3 different report image.</label>
	  			<select id="reportTypeSelect">
	  				<option value="BATCH" selected="true">Batch</option>
					<option value="TRANSACTIONAL">Transactional</option>
					<option value="PROGRAM">Program</option>
				</select>
				<button class="btn btn-primary exportChartBtn2" data-value="overview-summary-all">Export Chart</button>
			</div>
  			<div class="part">
	  			<label class="status success">The image of report generate success!</label>
	  			<label class="status failure">The image of report generate failure!</label>
  			</div>
  			<div class="report-data-loading">
	          	<div>
	          		<span class="loading-data-gif">&nbsp;</span><span>Loading data...</span>
			  	</div>
		   </div>
  		</div>
  	</div>
  	<script type="text/javascript">
		$(function(){
			$(".exportChartBtn").click(function(){
				var chartVal = $(this).attr("data-value");
				var jsFileName = "resources/" + chartVal + ".js";
				var url = "http://localhost:8081/";
				var $reportDataLoading = $(".report-data-loading");
				$reportDataLoading.show();
				app.ajaxRequest(app.host + "/exportChart", {jsFileName: jsFileName, url: url}, "POST").pipe(function(val){
					if(val.result == "SUCCESS"){
						$(".success").show();
						$(".failure").hide();
					}else{
						$(".success").hide();
						$(".failure").show();
					}
					$reportDataLoading.hide();
				});
			});
			
			$(".exportChartBtn2").click(function(){
				var chartVal = $(this).attr("data-value");
				var jsFileName = "resources/" + chartVal + ".js";
				var url = "http://localhost:8081/";
				var reportType = $("#reportTypeSelect").val();
				console.log("---reportType:"+reportType);
				var $reportDataLoading = $(".report-data-loading");
				$reportDataLoading.show();
				app.ajaxRequest(app.host + "/exportChart", {jsFileName: jsFileName, url: url, reportType: reportType}, "POST").pipe(function(val){
					if(val.result == "SUCCESS"){
						$(".success").show();
						$(".failure").hide();
					}else{
						$(".success").hide();
						$(".failure").show();
					}
					$reportDataLoading.hide();
				});
			});
		});
	</script>
  </body>
</html>