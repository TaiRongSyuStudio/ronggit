package rong.net.yam.controller;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import rong.net.http.DownLoadThread;
import rong.net.yam.view.YamPhotoWindow;
import rong.net.yam.config.Configuration;
import rong.net.yam.model.PageDataYam;

public class YamPhotoMonitor {
	
	private YamPhotoWindow yamPhotoWindow;
	private PageDataYam pageDataYam;
	private DownLoadThread downLoadThread;
	private boolean onDownLoad = false;
	
	public void viewAlbumNumberInList(String userName) {
		if(doValidationText(userName)) {
			Configuration configuration = yamPhotoWindow.getConfiguration();
			JOptionPane.showMessageDialog(null, configuration.getWrongMessage()); 
			return;
		} 
		setAlbumNumberOnList("http://album.blog.yam.com/", userName);
		
		if(pageDataYam.getPageNumber() < 1) {
			enabledTurnOverPageButton(false);
			yamPhotoWindow.getPageNumberLabel().setText("1/1");
		}
		else {
			enabledTurnOverPageButton(true);
			yamPhotoWindow.getPageNumberLabel().setText("1/" + pageDataYam.getPageNumber());
		}
	}
	
	public boolean doValidationText(String text) {
		return !text.matches("^[a-zA-Z0-9]*");
	}
	
	@SuppressWarnings("unchecked")
	public void setAlbumNumberOnList(String webSite, String userName) {
		yamPhotoWindow.getDownLoadButton().setEnabled(false);
		yamPhotoWindow.getAlbumNumberList().setListData(pageDataYam.getAlbumNumberForUserName(webSite, userName).toArray());
	}
	
	private void enabledTurnOverPageButton(boolean e) {
		yamPhotoWindow.getNextPageButton().setEnabled(e);
		yamPhotoWindow.getPreviousPageButton().setEnabled(e);
	}
	
	public void nextPage() {
		turnOverPage(1);
	}
	
	public void previousPage() {
		turnOverPage(-1);
	}
	
	public void turnOverPage(int pageCount) {
		String[] pageNumber = yamPhotoWindow.getPageNumberLabel().getText().split("/");
		int pageStart = Integer.parseInt(pageNumber[0]);
		pageStart += pageCount;
		pageStart = (pageStart > pageDataYam.getPageNumber()) ? 1 : pageStart;
		pageStart = (pageStart == 0) ? pageDataYam.getPageNumber() : pageStart;
		yamPhotoWindow.getPageNumberLabel().setText(pageStart + "/" + pageDataYam.getPageNumber());
		String webSite = String.format("http://album.blog.yam.com/album.php?userid=%s&page=%d", pageDataYam.getUserName(), pageStart);
		setAlbumNumberOnList(webSite, pageDataYam.getUserName());		
	}
	
	public void downLoadAlbum() {
		String listRowContent = yamPhotoWindow.getAlbumNumberList().getSelectedValue().toString();
		String[] albumNumberAndPhotoCount = listRowContent.split(" ");
		String albumNumber = albumNumberAndPhotoCount[albumNumberAndPhotoCount.length - 2];
		String photoCount = albumNumberAndPhotoCount[albumNumberAndPhotoCount.length - 1];
		downLoadThread = new DownLoadThread();
		downLoadThread.setAlbumNumber(pageDataYam.filterNumberForSubString(0, albumNumber.length(), albumNumber));
		downLoadThread.setPhotoCount(Integer.parseInt(pageDataYam.filterNumberForSubString(0, photoCount.length(), photoCount)));
		downLoadThread.setUserName(pageDataYam.getUserName());
		downLoadThread.setYamPhotoMonitor(this);
        Thread thread = new Thread(downLoadThread);
        setOnDownLoad(true);
        thread.start();
        reversionDownLoadAndStopButton(false);
	}
	
	public void stopDownLoad() {
		reversionDownLoadAndStopButton(true);
		downLoadThread.onStopDownload();
	}	
	
	public void reversionDownLoadAndStopButton(boolean e) {
        yamPhotoWindow.getDownLoadButton().setEnabled(e);
        yamPhotoWindow.getStopDownLoadButton().setEnabled(!e);
	}
	
	public void CheckOnDownLoadIng() {
		if(yamPhotoWindow.getAlbumNumberList().getSelectedIndex() != -1) 
		yamPhotoWindow.getDownLoadButton().setEnabled(!isOnDownLoad());
	} 

	public void setYamPhotoWindow(YamPhotoWindow yamPhotoWindow) {
		this.yamPhotoWindow = yamPhotoWindow;
	}

	public YamPhotoWindow getYamPhotoWindow() {
		return yamPhotoWindow;
	}

	public void setpageDataYam(PageDataYam pageDataYam) {
		this.pageDataYam = pageDataYam;
	}
	
	public JProgressBar getDownLoadProgressBar() {
		return this.yamPhotoWindow.getDownLoadProgressBar();
	}

	public boolean isOnDownLoad() {
		return onDownLoad;
	}

	public void setOnDownLoad(boolean onDownLoad) {
		this.onDownLoad = onDownLoad;
	}
}
