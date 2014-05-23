var page = require('webpage').create();
var system = require('system');
var address = system.args[1];

page.viewportSize = {width: 1300,height: 800};

page.open(address, function() {
	page.includeJs('js/jquery.min.js');

    window.setTimeout(function (){
    	page.includeJs('build/1.4.3/build.js',function() {
            page.evaluate(function() {
				smr.showReport("#reports-container",smr.REPORT_TYPE.BATCH);
    		});
        });
	}, 1000);
	
	window.setTimeout(function (){
		var clipRect = page.evaluate(function() {
			var target = document.querySelector(".report-content").getBoundingClientRect();
			var rect = {
				top: target.top,
				left: target.left,
				width: target.width,
				height: target.height
			}
			return rect;
		});

		page.clipRect = clipRect;

	    page.render('batch-overview-summary-all.png');
		phantom.exit();

    }, 3000);
});


