var page = require('webpage').create();

page.viewportSize = {width: 1300,height: 800};

page.open('http://localhost:8081/', function() {
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


