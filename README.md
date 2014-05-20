phantomjs-snow
==============

phantomjs java server with snow

Steps:
1. install the phantomjs
2. in phantomjs-snow, change the path of phantomjs in snow.properties
3. in SMR project run:  node server
4. in phantomjs-snow run:  mvn jetty:run
5. in http://localhost:8080/, click the Export Chart button, it will generate the image and saved in the phantomjs-snow folder