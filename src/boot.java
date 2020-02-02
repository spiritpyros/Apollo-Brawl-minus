import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.io.*;
import java.net.URL;
import java.util.Scanner;
import javax.swing.WindowConstants;
import sun.audio.*;
import java.awt.Toolkit;
import java.awt.Window.Type;


public class boot extends JFrame  {
	
	/*Declarations*/
	static JLabel progress = new JLabel("Nothing here...");
	static JFrame frame = new JFrame();
	static public String host = new File("").getAbsolutePath();
	static File currentVersion = new File(host+"\\data.txt");
	static File newestVersion = new File(host+"\\current.txt");
	static File currentNews = new File(host+"\\news.txt");
	static File discord = new File(host+"\\discord.txt");
	static File twitter = new File(host+"\\twitter.txt");
	static public String menuMusic = new String(host+"\\src\\resources\\menu_music.wav");
	static String img = new String(host+"\\src\\resources\\favicon.png");
	static String discordImg = new String(host+"\\src\\resources\\discordCE.png");
	static String twitterImg = new String(host+"\\src\\resources\\twitter.png");
	static boolean fileFound;
	connector downloader = new connector();
	String mostrecentVersion = "https://www.dropbox.com/s/vyp0n0jh4nna2x7/current.txt?dl=1";
	String newsLink= "https://www.dropbox.com/s/ofza4896dmum1ww/news.txt?dl=1";
	static String discordLink = "https://www.dropbox.com/s/217jcjv6phhrsko/discord.txt?dl=1";
	static String twitterLink = "https://www.dropbox.com/s/275n9yvipfxftq5/twitter.txt?dl=1";
	String discordInvite = "";
	String twitterInvite = "";
	String top="";
    JLabel Jtop=new JLabel();
    JLabel Jtop2=new JLabel();
    static String firstLine = "";
    static String secondLine = "";
    static boolean pendingUpdate = false;
    static JButton updateButton = new JButton();
    static JButton publicDiscord = new JButton();
    static JButton smashCETwitter = new JButton();
    static Scanner versionPrinter;
    int textx = 1400;
	int textx2 = 2800;
	boolean internetError = false;
	///////////////
	
	public boot() {    
        initUI();
    }
	
	
	
	@SuppressWarnings("serial") //fixes an error with the line setContentPane(new JPanel(new BorderLayout()) {
	private void initUI() {
		setPreferredSize(new Dimension(1280,720));
		setResizable(false); //makes window not-resizeable
	    pack(); //sets window height to be just bigger than all the jFrame subcomponents?
	    setTitle("Smash Bros. CE Boot Menu");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    getContentPane().setLayout(new GridLayout(1,3));
	    setIconImage(new ImageIcon(img).getImage());
	    if (currentVersion.exists()) fileFound = true;
	    else fileFound = false;
	    try {
		    final Image backgroundImage = javax.imageio.ImageIO.read(new File(host+"\\src\\resources\\CE Background.jpg"));
		    setContentPane(new JPanel(new BorderLayout()) {
		        @Override public void paintComponent(Graphics g) {
		            g.drawImage(backgroundImage, 0, 0, null);
		        }
		    });
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e, "Unable to find CE Background.jpg", JOptionPane.INFORMATION_MESSAGE);
		}
	    getContentPane().add(progress, BorderLayout.PAGE_END);
		progress.setForeground(Color.WHITE);
		try {
			connector.download(mostrecentVersion, host, "current.txt");
			connector.download(newsLink, host, "news.txt");
			connector.download(discordLink, host, "discord.txt");
			connector.download(twitterLink, host, "twitter.txt");
		} catch (IOException e) {
			updateButton.setText("Unable to connect to files needed to download. Check your internet connection.");
			updateButton.setEnabled(false);
			internetError = true;
		}
		try {
			Scanner newestversionPrinter = new Scanner(newestVersion);
			secondLine = newestversionPrinter.nextLine();
			newestversionPrinter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		newestVersion.delete();
		try {
			Scanner discordScanner = new Scanner(discord);
			discordInvite = discordScanner.nextLine();
			discordScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		discord.delete();
		try {
			Scanner twitterScanner = new Scanner(twitter);
			twitterInvite = twitterScanner.nextLine();
			twitterScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		twitter.delete();
		//add(Jtop2, BorderLayout.NORTH);
		getContentPane().add(Jtop, BorderLayout.PAGE_START);
        Jtop.setSize(getWidth(), 20);
        Jtop2.setSize(getWidth(), 20);
        try {
			Scanner newsPrinter = new Scanner(currentNews);
			while(newsPrinter.hasNextLine())
			top = newsPrinter.nextLine();
			newsPrinter.close();
		} catch (FileNotFoundException e) {
			top = "News unavailable. Please contact @SuperSmasherFan#0218 on Discord for help.";
		}
        currentNews.delete();
        Jtop.setText(top);
        Jtop.setForeground(Color.WHITE);
        Jtop.setFont(new Font("Impact", Font.PLAIN, 37));
        Jtop.setLocation(-1500, 15);
        Jtop2.setText("Jtop2");
        Jtop2.setForeground(Color.WHITE);
        Jtop2.setFont(new Font("Impact", Font.PLAIN, 37));
        Jtop2.setLocation(-3000, -90);
        t.start();
        scroll.start();
        sounds.playBGM(menuMusic);
        updateButton.setText("Update!");
        if (internetError == true) {
        	updateButton.setText("Couldn't obtain online files necessary for downloading.");
        	System.out.println("help");
        }
        updateButton.setBounds(360,670,570,20);
        publicDiscord.setBounds(1170,641,50,50);
        smashCETwitter.setBounds(1223, 641, 50, 50);
        add(updateButton, BorderLayout.SOUTH);
        add(publicDiscord, BorderLayout.SOUTH);
        add(smashCETwitter, BorderLayout.SOUTH);
        publicDiscord.setIcon(new ImageIcon(discordImg));
        smashCETwitter.setIcon(new ImageIcon(twitterImg));
        publicDiscord.setOpaque(false);
        publicDiscord.setContentAreaFilled(false);
        publicDiscord.setBorderPainted(false);
        smashCETwitter.setOpaque(false);
        smashCETwitter.setContentAreaFilled(false);
        smashCETwitter.setBorderPainted(false);
        //publicDiscord.setBorder(null);
        //smashCETwitter.setBorder(null);
	}
	
	public static void main(String[] args) {
		try {
			versionPrinter = new Scanner(currentVersion);
			firstLine = versionPrinter.nextLine();
			//versionPrinter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    boot ex = new boot();
	    ex.setVisible(true);
	}
	
Thread scroll=new Thread()
{
  public void run()
  {
      while(true)
      {
    	  double i=-1;
    	  try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		textx+=i;
    	    textx2+=i;
    	    Jtop.setLocation(textx,15);
    	    Jtop2.setLocation(textx2,15);
    	    if(Jtop.getX() < -1500) {
    	      Jtop.setLocation(1400,15);
    	      textx = 1400;
    	    }
    	    if(Jtop2.getX() < -1500) {
    	        Jtop2.setLocation(1400,-90);
    	        textx2 = 1400;
        }
    	    if (publicDiscord.getModel().isPressed()) {
    	    	try {
    	    		Thread.sleep(100);
    	            Desktop.getDesktop().browse(new URL(discordInvite).toURI());
    	        } catch (Exception e) {
    	        	JOptionPane.showMessageDialog(null, e, "Error opening link, check your internet.", JOptionPane.INFORMATION_MESSAGE);
    	        }
    	    }
    	    if (smashCETwitter.getModel().isPressed()) {
    	    	try {
    	    		Thread.sleep(100);
    	            Desktop.getDesktop().browse(new URL(twitterInvite).toURI());
    	        } catch (Exception e) {
    	        	JOptionPane.showMessageDialog(null, e, "Error opening link, check your internet.", JOptionPane.INFORMATION_MESSAGE);
    	        }
    	    }
      }
  };
};
static Thread progressUpdater=new Thread()
{
  public void run()
  {
      while(dataParser.updating == true)
      {
    	  boot.updateButton.setText("Files downloaded: "+(int)(dataParser.updateID/3.00)+" out of "+(int)dataParser.updates);
      }
  };
};
Thread t=new Thread()
{
  public void run()
  {
      boolean init = false;
      boolean loop = true;
      while(loop)
      {
    	  try {
			versionPrinter = new Scanner(currentVersion);
			firstLine = versionPrinter.nextLine();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, e1, "Error reading data.txt.", JOptionPane.INFORMATION_MESSAGE);
			firstLine = "Unknown?";
			loop = false;
		}
    	  if (!secondLine.equals(firstLine) && init == false && !firstLine.equals("Unknown?") && internetError == false) {
    		  progress.setText("Version: "+firstLine+"   Most current version: "+secondLine+"    You need to update!");
    		  pendingUpdate = true;
    		  updateButton.setEnabled(true);
    		  init = true;
    	  }
    	  else if (secondLine.equals(firstLine)) {
				progress.setText("Version: "+firstLine+"   Most current version: "+secondLine+"    You're all up to date!");
				pendingUpdate = false;
				updateButton.setEnabled(false);
			}
    	  else if (firstLine.equals("Unknown?")) {
    		  progress.setText("Version: "+firstLine+"   Most current version: "+secondLine);
    		  pendingUpdate = false;
    		  updateButton.setEnabled(false);
    	  }
    	  if (updateButton.getModel().isPressed()) {
    		  try {
				dataParser.update();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	  }
      try {
          Thread.sleep(50);
      } catch (Exception ex) {}
        }
      }
  };
}

