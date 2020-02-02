import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class sounds {
	public static void main(String[] args) {
		playBGM(boot.menuMusic);
	}
	public static void playBGM(String directory) {
		try {
		File musicPath = new File(directory);
		AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
		Clip clip = AudioSystem.getClip();
		clip.open(audioInput);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception ex) {
			//JOptionPane.showMessageDialog(null, ex, "Menu music file not found.", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
