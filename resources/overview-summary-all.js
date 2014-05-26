var page = require('webpage').create();
var system = require('system');
var address = system.args[1];
var reportType = system.args[2];
var imageName = reportType + "_Overview_Summary.png"

page.viewportSize = {width: 1300,height: 800};

page.open(address, function() {
	page.includeJs('js/jquery.min.js');

    window.setTimeout(function (){
    	page.includeJs('build/1.4.3/build.js',function() {
    		if(reportType == "BATCH"){
    			page.evaluate(function() {
					smr.showReport("#reports-container",smr.REPORT_TYPE.BATCH);
	    		});
    		}else if(reportType == "PROGRAM"){
    			page.evaluate(function() {
                	smr.showReport("#reports-container",smr.REPORT_TYPE.PROGRAM);
    			});
    		}else if(reportType == "TRANSACTIONAL"){
    			page.evaluate(function() {
                	smr.showReport("#reports-container",smr.REPORT_TYPE.TRANSACTIONAL);
    			});
    		}
            
        });
	}, 1000);
	
	window.setTimeout(function (){
		var clipRect = page.evaluate(function() {
			var target = $("#reports-container")[0].getBoundingClientRect();
			var rect = {
				top: target.top,
				left: target.left,
				width: target.width,
				height: target.height
			}
			return rect;
		});

		page.clipRect = clipRect;

	    page.render(imageName);
		phantom.exit();

    }, 3000);
});


