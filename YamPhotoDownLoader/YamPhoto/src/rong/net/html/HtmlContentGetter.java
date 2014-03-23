package rong.net.html;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class HtmlContentGetter {
	public static ArrayList<String> getHtmlContent(String webSite) {
		ArrayList<String> htmlContent = new ArrayList<String>();
        try {
        	   	URL url = new URL(webSite);
        	   	InputStream inputStream = url.openStream();
        	   	InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf8");
        	   	BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        	   	String tempString;
        	   	
        	   	while ((tempString = bufferedReader.readLine()) != null) {
        	   		htmlContent.add(tempString);
        	   	}
            url = null;
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } 
        catch (Exception e) {
           e.getStackTrace();
           System.out.println(e.getMessage());
        } 
        return htmlContent;
	}
}
