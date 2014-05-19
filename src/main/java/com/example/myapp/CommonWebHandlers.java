package com.example.myapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

        StringBuilder stringBuilder = null;
        String[] parameters = paramList.toArray(new String[paramList.size()]);

        try {
            System.out.println("-----execute phantomjs-----file:"+jsFileName);
            Process process = Runtime.getRuntime().exec(parameters);
            
            int exitStatus = process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader (process.getInputStream()));

            String currentLine=null;
            stringBuilder = new StringBuilder(exitStatus==0 ? "SUCCESS" : "ERROR:");
            currentLine= bufferedReader.readLine();

            while(currentLine !=null)
            {
                stringBuilder.append(currentLine);
                currentLine = bufferedReader.readLine();
            }
            System.out.println(stringBuilder.toString());
            
        } catch (Exception e) {
            throw new IllegalStateException("Cannot execute script " + jsFileName, e);
        }
    	
    	return WebResponse.success(stringBuilder.toString());
    }

}
