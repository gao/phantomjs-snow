package com.example.myapp;

import java.util.LinkedList;
import java.util.List;

import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.rest.annotation.WebPost;
import com.google.inject.Inject;
import com.google.inject.Singleton;


@Singleton
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommonWebHandlers {
	

    @Inject
    public CommonWebHandlers() {
    }

    @WebPost("/exportChart")
    public WebResponse exportChart(@WebParam("jsFileName") String jsFileName) {
        List<String> paramList = new LinkedList<String>();
        paramList.add("/usr/local/bin/phantomjs");
        paramList.add(jsFileName);

        String[] parameters = paramList.toArray(new String[paramList.size()]);

        try {
            System.out.println("-----execute phantomjs-----file:"+jsFileName);
            Runtime.getRuntime().exec(parameters);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot execute script " + jsFileName, e);
        }
    	
    	return WebResponse.success(jsFileName);
    }

}
