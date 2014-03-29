package rong.net.yam.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rong.net.html.HtmlContentGetter;

public class PageDataYam {
	private String userName;
	private int pageNumber;
	
	public LinkedList<StringBuffer> getAlbumNumberForUserName(String webSite, String userName) {
		
		ArrayList<String> htmlContent = (checkIsNewUser(userName)) ? HtmlContentGetter.getHtmlContent(webSite + userName)
																	: HtmlContentGetter.getHtmlContent(webSite);

		LinkedList<StringBuffer> albumNumberAndPhotoCountList = new LinkedList<StringBuffer>();
		boolean isLocked = false;
   	   	for(String s : htmlContent) {
    	   		if(s.indexOf("/alt") > 0) {
    	   			albumNumberAndPhotoCountList.addLast(new StringBuffer(s.substring(s.indexOf("/alt") + 6, s.indexOf("\" titl")) + " "));
    	   			System.out.println(s.substring(s.indexOf("/alt") + 6, s.indexOf("\" titl")));
    	   		}
    	   		else if(s.indexOf("<!-- 相片 -->") > 0) {
    	   			albumNumberAndPhotoCountList.getLast().append(filterNumberForSubString(s.indexOf("相片") - 15, s.indexOf("相片"), s));
    	   			albumNumberAndPhotoCountList.getLast().append(" (" + filterNumberForSubString(52, 70, s) + ")");
    	   		}
    	   		else if(s.indexOf("<!-- 最後一頁 -->") > 0 && getPageNumber() == 0) {
    	   			StringBuffer string = new StringBuffer(filterNumberForSubString(s.indexOf("1/"), s.length(), s));
    	   			System.out.println(string.deleteCharAt(0));
    	   			setPageNumber(Integer.valueOf(string.toString()));
    	   		}
    	   	}
        return albumNumberAndPhotoCountList;
	}
	
	private boolean checkIsNewUser(String userName) {
		if(this.getUserName() != userName) {
			setPageNumber(0);
			setUserName(userName);
			return true;
		}
		else {
			return false;
		}
	}
	
	public String filterNumberForSubString(int start, int end, String string) {
		String regEx="[^0-9]";   
		Pattern pattern = Pattern.compile(regEx);   
		Matcher matcher = pattern.matcher(string.substring(start, end));
		return matcher.replaceAll("").trim();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public static void main(String[] args) {
		PageDataYam pageDataYam = new PageDataYam();
		System.out.println();
		System.out.println(pageDataYam.getAlbumNumberForUserName("http://album.blog.yam.com/", "cooperbmlee"));
		System.out.println(pageDataYam.getPageNumber());
	}
}
