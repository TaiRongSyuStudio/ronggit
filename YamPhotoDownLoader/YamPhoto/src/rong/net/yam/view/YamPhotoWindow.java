package rong.net.yam.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;

import rong.net.system.OSValidator;
import rong.net.yam.config.Configuration;
import rong.net.yam.controller.YamPhotoMonitor;
import rong.net.yam.model.PageDataYam;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JProgressBar;

import com.thoughtworks.xstream.XStream;

public class YamPhotoWindow {
	private Configuration configuration;
	private JFrame frmYamdownloaderVersionFor;
	private JTextField userNameField;
	private JList albumNumberList;
	private JButton albumListGetterButton;
	private JButton downLoadButton;
	private JButton stopDownLoadButton;
	private JButton previousPageButton;
	private JButton nextPageButton;
	private JLabel pageNumberLabel;
	private JProgressBar downLoadProgressBar;
	private YamPhotoMonitor yamPhotoMonitor;
	private PageDataYam pageDataYam;
	private XStream xstream;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YamPhotoWindow window = new YamPhotoWindow();
					PageDataYam pageDataYam = new PageDataYam();
					YamPhotoMonitor yamPhotoMonitor = new YamPhotoMonitor();
					
					yamPhotoMonitor.setYamPhotoWindow(window);
					yamPhotoMonitor.setpageDataYam(pageDataYam);
					
					window.setYamPhotoMonitor(yamPhotoMonitor);
					window.setPageDataYam(pageDataYam);
					
					window.frmYamdownloaderVersionFor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public YamPhotoWindow() {
		initialize();
	}

	private void initialize() {
		setConfiguration();
		
		frmYamdownloaderVersionFor = new JFrame();
		frmYamdownloaderVersionFor.setTitle("YamAlbumDownLoader for java v1.2");
		frmYamdownloaderVersionFor.setBounds(100, 100, 492, 357);
		frmYamdownloaderVersionFor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmYamdownloaderVersionFor.getContentPane().setLayout(null);
		
		JLabel userNameLabel = new JLabel("\u4F7F\u7528\u8005\u540D\u7A31:");
		userNameLabel.setBounds(10, 33, 78, 15);
		frmYamdownloaderVersionFor.getContentPane().add(userNameLabel);
		
		userNameField = new JTextField();
		userNameField.setBounds(91, 30, 213, 21);
		frmYamdownloaderVersionFor.getContentPane().add(userNameField);
		userNameField.setColumns(10);
		
		JLabel albumNumberLabel = new JLabel("\u76F8\u7C3F\u7DE8\u865F:");
		albumNumberLabel.setBounds(10, 66, 63, 15);
		frmYamdownloaderVersionFor.getContentPane().add(albumNumberLabel);
		
		albumNumberList = new JList();
		albumNumberList.setBounds(90, 65, 214, 215);
		frmYamdownloaderVersionFor.getContentPane().add(albumNumberList);
		
		albumListGetterButton = new JButton("\u53D6\u5F97\u76F8\u7C3F\u5217\u8868");
		albumListGetterButton.setBounds(333, 28, 116, 23);
		frmYamdownloaderVersionFor.getContentPane().add(albumListGetterButton);
		
		downLoadButton = new JButton("\u4E0B\u8F09");
		downLoadButton.setEnabled(false);
		downLoadButton.setBounds(333, 79, 116, 23);
		frmYamdownloaderVersionFor.getContentPane().add(downLoadButton);
		
		stopDownLoadButton = new JButton("\u505C\u6B62");
		stopDownLoadButton.setEnabled(false);
		stopDownLoadButton.setBounds(333, 130, 116, 23);
		frmYamdownloaderVersionFor.getContentPane().add(stopDownLoadButton);
		
		nextPageButton = new JButton("\u4E0B\u4E00\u9801");
		nextPageButton.setEnabled(false);
		nextPageButton.setBounds(235, 292, 86, 29);
		frmYamdownloaderVersionFor.getContentPane().add(nextPageButton);
		
		previousPageButton = new JButton("\u4E0A\u4E00\u9801");
		previousPageButton.setEnabled(false);
		previousPageButton.setBounds(76, 292, 86, 29);
		frmYamdownloaderVersionFor.getContentPane().add(previousPageButton);
		
		pageNumberLabel = new JLabel("");
		pageNumberLabel.setBounds(174, 298, 49, 16);
		frmYamdownloaderVersionFor.getContentPane().add(pageNumberLabel);
		
		downLoadProgressBar = new JProgressBar();
		downLoadProgressBar.setBounds(333, 294, 146, 20);
		frmYamdownloaderVersionFor.getContentPane().add(downLoadProgressBar);
		
		albumNumberList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				yamPhotoMonitor.CheckOnDownLoadIng();
			}
		});
		
		albumListGetterButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(userNameField.getText());
				yamPhotoMonitor.viewAlbumNumberInList(userNameField.getText());
			}
		});
		
		nextPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				yamPhotoMonitor.nextPage();
			}
		});
		
		previousPageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				yamPhotoMonitor.previousPage();
			}
		});
		
		downLoadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				yamPhotoMonitor.downLoadAlbum();
			}
		});
		
		stopDownLoadButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				yamPhotoMonitor.stopDownLoad();
			}
		});
		
	}
	
	public JTextField getUserNameField() {
		return userNameField;
	}

	public JList getAlbumNumberList() {
		return albumNumberList;
	}

	public JButton getAlbumListGetterButton() {
		return albumListGetterButton;
	}

	public JButton getDownLoadButton() {
		return downLoadButton;
	}

	public JButton getStopDownLoadButton() {
		return stopDownLoadButton;
	}

	public void setYamPhotoMonitor(YamPhotoMonitor yamPhotoMonitor) {
		this.yamPhotoMonitor = yamPhotoMonitor;
	}

	public void setPageDataYam(PageDataYam pageDataYam) {
		this.pageDataYam = pageDataYam;
	}
	
	public PageDataYam getPageDataYam() {
		return pageDataYam;
	}

	public JButton getPreviousPageButton() {
		return previousPageButton;
	}

	public void setPreviousPageButton(JButton previousPageButton) {
		this.previousPageButton = previousPageButton;
	}

	public JButton getNextPageButton() {
		return nextPageButton;
	}

	public void setNextPageButton(JButton nextPageButton) {
		this.nextPageButton = nextPageButton;
	}

	public JLabel getPageNumberLabel() {
		return pageNumberLabel;
	}

	public void setPageNumberLabel(JLabel pageNumberLabel) {
		this.pageNumberLabel = pageNumberLabel;
	}

	public JProgressBar getDownLoadProgressBar() {
		return downLoadProgressBar;
	}

	public void setDownLoadProgressBar(JProgressBar downLoadProgressBar) {
		this.downLoadProgressBar = downLoadProgressBar;
	}
	
	public void setConfiguration() {
		FileInputStream fis = null;
		String codeSource = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		Path path = null;
		
        if (OSValidator.isWindows()) {
            System.out.println("This is Windows");
            path = Paths.get(codeSource.substring(1, codeSource.length()));
        } 
        else if (OSValidator.isMac()) {
            System.out.println("This is Mac");
            path = Paths.get(codeSource);
        }
        
		System.out.println(path.getParent());
		try {
			
			fis = new FileInputStream(path.getParent() + "/config.xml");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xstream = new XStream();  
		xstream.alias("config", Configuration.class);   
		configuration = (Configuration) xstream.fromXML(fis);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
}
