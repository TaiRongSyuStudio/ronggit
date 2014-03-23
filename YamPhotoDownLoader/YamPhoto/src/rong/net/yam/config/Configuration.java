package rong.net.yam.config;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.thoughtworks.xstream.XStream;

public class Configuration {
	private String directory;
	private String wrongMessage;
	private String photoType;

	public String getDirectory() {
		return directory;
	}
	
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	
	public String getWrongMessage() {
		return wrongMessage;
	}
	
	public void setWrongMessage(String wrongMessage) {
		this.wrongMessage = wrongMessage;
	}
	
	public String getPhotoType() {
		return photoType;
	}
	
	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}
	
	public static void main(String[] args) throws IOException {
		FileReader fileReader = new FileReader("config.xml");
		XStream xstream = new XStream();  
		xstream.alias("config", Configuration.class);   
		Configuration configuration = (Configuration) xstream.fromXML(fileReader);
		System.out.println(configuration.getWrongMessage());
		
		Path path = Paths.get(System.getProperty("user.dir"));
		System.out.println(path);
	}

}
