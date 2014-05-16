var page = require('webpage').create();
page.viewportSize = { width: 1300, height: 800 };
page.open('http://localhost:8081/', function() {
	window.setTimeout(function (){
        page.render('batch-overview-summary.png');
		phantom.exit();
    }, 3000);
 
});