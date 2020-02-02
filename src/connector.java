import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class connector {
	static int downloaded = 0;
	public static Path download(String sourceURL, String targetDirectory, String downloadTitle) throws IOException
	{
	    URL url = new URL(sourceURL);
	    String outputName = downloadTitle;
	    Path targetPath = new File(targetDirectory + File.separator + outputName).toPath();
	    Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	    downloaded++;
	    return targetPath;
	}
}
