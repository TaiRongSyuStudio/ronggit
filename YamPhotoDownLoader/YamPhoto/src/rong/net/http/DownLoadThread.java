package rong.net.http;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import rong.net.html.HtmlContentGetter;
import rong.net.yam.config.Configuration;
import rong.net.yam.controller.YamPhotoMonitor;

public class DownLoadThread implements Runnable {
	private boolean isStop = true;
	private boolean isSuspend = false;
	private String folderPath;
	private String userName;
	private String albumNumber;
	private int photoCount;
	private YamPhotoMonitor yamPhotoMonitor;
	
    public void onStopDownload() { 
    		isStop = false; 
    		yamPhotoMonitor.setOnDownLoad(false);
    		yamPhotoMonitor.getDownLoadProgressBar().setValue(0);
    } 
    
    public void setSuspend() {
    		isSuspend = true;
    	}
    
    public synchronized void setResume() {
    		isSuspend = false;
    		notify();
    }

    public void run() { 
    		yamPhotoMonitor.getDownLoadProgressBar().setValue(0);
    		Configuration configuration = yamPhotoMonitor.getYamPhotoWindow().getConfiguration();
    		setFolderPath(configuration.getDirectory() + "/" + this.getUserName() + "_" + this.getAlbumNumber());
    		makeAlbumFolder();
    		int i = 0;
        while(i < photoCount && isStop != false) { 
	    		String photoWebSite = String.format("http://album.blog.yam.com/show.php?a=%s&f=%s&i=11111111&p=%d", userName, albumNumber, i);
	    		ArrayList<String> htmlContent = HtmlContentGetter.getHtmlContent(photoWebSite);
	    		String fileURL = null;
	    		for(String s : htmlContent) {
	    			if(s.indexOf("og:image") > 0) {
	    				fileURL = s.substring(35, s.length() - 2).replace("t_", "");
					System.out.println(fileURL);
   				}
   			} 
   			try {
				HttpDownloadUtility.downloadFile(fileURL, getFolderPath());
				
				synchronized (this) {
					while (isSuspend)
						wait();
				}
			} 
    			catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
   			i++;
   			yamPhotoMonitor.getDownLoadProgressBar().setValue((i * 100) / photoCount);
    		}
        yamPhotoMonitor.reversionDownLoadAndStopButton(true);
        onStopDownload();
    }
    
    public void makeAlbumFolder() {
		File folder = new File(getFolderPath());
		 if(!folder.exists()) {
			 folder.mkdir();
		}  	
    }
    
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPhotoCount(int photoCount) {
		this.photoCount = photoCount;
	}

	public void setAlbumNumber(String albumNumber) {
		this.albumNumber = albumNumber;
	}
	
	public String getAlbumNumber() {
		return albumNumber;
	}
	
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public void setYamPhotoMonitor(YamPhotoMonitor yamPhotoMonitor) {
		this.yamPhotoMonitor = yamPhotoMonitor;
	}
	
    public static void main(String args[]) throws  InterruptedException {
    		DownLoadThread downLoadThread = new DownLoadThread();
        Thread thread = new Thread(downLoadThread); 
        thread.start(); 
        downLoadThread.onStopDownload();
    }

}
