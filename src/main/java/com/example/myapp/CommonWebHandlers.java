package com.example.myapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    /* hide it for now
    @Inject
    @Named("myApp.phantomjsPath")
    private String phantomjsPath; 
    */
    
    private static final String PREFIX = "phantomjs_";
    private static final File EXECUTABLE_TMP_PATH = initializeExecutable();
    private static boolean executableWritten = false;

    InputStream getResourceStreamByArchitecture() {
        String architecture = System.getProperty("os.arch");
        String resourceName = PREFIX + architecture;
        return this.getClass().getResourceAsStream(resourceName);
    }
    
    static File initializeExecutable() {
        try {
            File executable = File.createTempFile("phantomjs", "");
            executable.deleteOnExit();
            return executable;
        } catch (IOException e) {
            throw new IllegalStateException("Can't initialize PhantomJS executable", e);
        }
    }

    File getExecutableFile() {
        if (!executableWritten) {
            synchronized (EXECUTABLE_TMP_PATH) {
                writeStreamToFile(getResourceStreamByArchitecture(), EXECUTABLE_TMP_PATH);
                EXECUTABLE_TMP_PATH.setReadable(true);
                EXECUTABLE_TMP_PATH.setExecutable(true);
                if (!EXECUTABLE_TMP_PATH.canExecute()) {
                    throw new IllegalStateException("Cannot set phantomjs file as executable");
                }
                executableWritten = true;
            }
        }
        return EXECUTABLE_TMP_PATH;
    }

    void writeStreamToFile(InputStream inputStream, File file) {
        try {
            byte[] buffer = new byte[8192];
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int r;
            while ((r = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, r);
            }
            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new IllegalStateException("Can't write stream to file", e);
        }

    }

    @WebPost("/exportChart")
    public WebResponse exportChart(@WebParam("jsFileName") String jsFileName, @WebParam("url") String url, @WebParam("reportType") String reportType) {
        File executable = getExecutableFile();
        String path =  executable.getAbsolutePath();
        
        List<String> paramList = new LinkedList<String>();
        //paramList.add(phantomjsPath);
        paramList.add(path);
        paramList.add(jsFileName);
        paramList.add(url);
        paramList.add(reportType);

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
