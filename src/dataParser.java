import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class dataParser {
	
	static String[] sourceURL;
	static String[] targetPath;
	static String[] fileName;
	static public String root = new File("").getAbsolutePath();
	static File updateNum = new File(root+"\\updateNum.txt");
	static File updateQueue = new File(root+"\\updateQueue.txt");
	static String updateNumLink = "https://www.dropbox.com/s/cq8vxz7djzah0vf/updateNum.txt?dl=1";
	static String updateQueueLink = "https://www.dropbox.com/s/9ii981uv7ye52hb/updateQueue.txt?dl=1";
	static double updates = 0;
	static double updateID = -1;
	static String line = " ";
	connector downloader = new connector();
	static boolean updating = false;
	static int errors = 0;

	public static void update() throws InterruptedException {
		boot.updateButton.setEnabled(false);
		boot.updateButton.setText("Updating... please give the program a moment...");
		updating = true;
			try {
				if (boot.pendingUpdate) connector.download(updateNumLink, root, "updateNum.txt");
				if (boot.pendingUpdate) connector.download(updateQueueLink, root, "updateQueue.txt");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				Scanner updateNumFinder = new Scanner(updateNum);
				String updateString = updateNumFinder.nextLine();
				updates = Integer.parseInt(updateString);
				System.out.println("Files: "+updates);
				updateNumFinder.close();
				} catch (FileNotFoundException e) {}
			String[] sourceURL = new String[(int) (updates*3)];
			String[] targetPath = new String[(int) (updates*3)];
			String[] fileName = new String[(int) (updates*3)];
			boot.progressUpdater.start();
			try {
				Scanner updateReader = new Scanner(updateQueue);
				line = updateReader.nextLine();
				String[] words = line.split(" "); 
				for (String word : words) {
					updateID++;
					System.out.println(word+"   "+updateID);
					if (updateID % 3 == 0) {
						sourceURL[(int) updateID] = word;
					}
					if ((updateID-1) % 3 == 0) {
						targetPath[(int) updateID] = root+word;
					}
					if ((updateID-2) % 3 == 0) {
						fileName[(int) updateID] = word;
					}
				}
				updateReader.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			updateNum.delete();
			updateQueue.delete();
			updateID = 0;
			while (updateID < (updates*3)) {
				try {
					connector.download(sourceURL[(int) updateID], targetPath[(int) updateID+1], fileName[(int) (updateID+2)]);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e, "Unspecified download error. Please report to @SuperSmasherFan#0218 on Discord", JOptionPane.INFORMATION_MESSAGE);
					errors++;
				}
				updateID+=3;
			}
			updating = false;
			if (errors == 0) {
				try {
					PrintWriter versionCorrector = new PrintWriter(boot.currentVersion);
					versionCorrector.flush();
					versionCorrector.print("v1.1");
					versionCorrector.close();
					boot.updateButton.setText("Done downloading!");
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e, "Error editing data.txt", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else if (errors > 0) {
				boot.updateButton.setText("Download finished with "+errors+" errors, download failed.");
			}
		} 
}
