package rong.net.http;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

	public class HttpImageDownloadUtility {
	 
	    public static void downloadFile(String fileURL, String saveDir)
	            throws IOException 
	    {
	        URL url = new URL(fileURL);
	        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
	        httpConn.addRequestProperty("referer", "http://album.blog.yam.com/");
	        int responseCode = httpConn.getResponseCode();
	        
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            String fileName = "";
	            String disposition = httpConn.getHeaderField("Content-Disposition");
	            String contentType = httpConn.getContentType();
	            int contentLength = httpConn.getContentLength();
	 
	            if (disposition != null) {

	                int index = disposition.indexOf("filename=");
	                if (index > 0) {
	                    fileName = disposition.substring(index + 10,
	                            disposition.length() - 1);
	                }
	            } 
	            else {
	                
	                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
	                        fileURL.length());
	            }
	 
	            System.out.println("Content-Type = " + contentType);
	            System.out.println("Content-Disposition = " + disposition);
	            System.out.println("Content-Length = " + contentLength);
	            System.out.println("fileName = " + fileName);

	            String saveFilePath = saveDir + File.separator + fileName;

	        		BufferedImage image = null;

	            image = ImageIO.read(httpConn.getInputStream());
	            
	            ImageIO.write(image, "jpg", new File(saveFilePath));
	                
	            System.out.println("File downloaded");
	        } 
	        else {
	            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
	        }
	        httpConn.disconnect();
	    }
	    public static void main(String[] args) {
	        String fileURL = "http://pics9.yamedia.tw/11/userfile/p/pilipala/album/1487b67ad1cc30.jpg";
	        String saveDir = "/Users/rong/Downloads/photo";
	        try {
	            HttpImageDownloadUtility.downloadFile(fileURL, saveDir);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	}