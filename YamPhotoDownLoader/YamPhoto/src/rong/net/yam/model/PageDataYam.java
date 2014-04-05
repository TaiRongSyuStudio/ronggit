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

		ArrayList<StringBuffer> albumName = new ArrayList<StringBuffer>();
		ArrayList<StringBuffer> albumNumberAndPhotoCount = new ArrayList<StringBuffer>();
		
   	   	for(String s : htmlContent) {
    	   		if(s.indexOf("/" + getUserName() +"&folder") > 0) {
    	   			if(s.indexOf("alt=") > 0) { 
    	   				albumName.add(new StringBuffer(s.substring(s.indexOf("alt=") + 5, s.indexOf("\" titl")) + " "));
    	   				System.out.println(s.substring(s.indexOf("alt") + 5, s.indexOf("\" titl")));
    	   			}
    	   			else if(s.indexOf("<!-- 相片 -->") > 0) {
    	   				albumNumberAndPhotoCount.add(new StringBuffer(filterNumberForSubString(s.indexOf("相片") - 10, s.indexOf("相片"), s)));
    	   				albumNumberAndPhotoCount.get(albumNumberAndPhotoCount.size() - 1).append(" (" + filterNumberForSubString(52, 70, s) + ")");
        	   		}
    	   		}
    	   		else if(s.indexOf("<!-- 最後一頁 -->") > 0 && getPageNumber() == 0) {
    	   			StringBuffer string = new StringBuffer(filterNumberForSubString(s.indexOf("1/"), s.length(), s));
    	   			System.out.println(string.deleteCharAt(0));
    	   			setPageNumber(Integer.valueOf(string.toString()));
    	   		}
    	   	}
   	   	LinkedList<StringBuffer> albumNumberAndPhotoCountList = new LinkedList<StringBuffer>();
   	   	for(int i = 0; i <  albumName.size(); i++) {
   	   		albumNumberAndPhotoCountList.add(new StringBuffer(albumName.get(i)));
   	   		albumNumberAndPhotoCountList.getLast().append(albumNumberAndPhotoCount.get(i));
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
		System.out.println(pageDataYam.getAlbumNumberForUserName("http://album.blog.yam.com/", "mihokaneko"));
		System.out.println(pageDataYam.getPageNumber());
	}
}
