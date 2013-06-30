import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Scanner;


public class ConfigLoader {

	private static String filename = "Config.conf";
	
	@SuppressWarnings("unused")
	/**
	 * Creates the configuration file if it doesn't exist and loads the contents if it does.
	 * @throws IOException
	 */
	public ConfigLoader() throws IOException {
		File f = new File(filename);
		if(f==null || !f.exists()) {
			if(f==null) {
				throw new NullPointerException("f is null for filename: " + filename); 
			}
			else if(f.canWrite()) {
				f.createNewFile();
				writeDefaults(f);
			} else throw new AccessDeniedException("You do not have premission to create file: \"" + filename + "\" at " + f.getAbsolutePath());
			
			load(f);
		} else load(f);
	}

	/**
	 * Writes the default configuration settings to the specified file.
	 * @param f
	 * @throws IOException 
	 */
	public void writeDefaults(File f) throws IOException {
		FileWriter fout = new FileWriter(f);
		
		fout.write("isServer=true\n");
		fout.write("isProcessor=true\n");
		fout.write("serverPort=10100\n");
		fout.write("processorPort=10101\n");
		
		fout.close();
	}
	
	/**
	 * Loads the configurations found in the supplied file object.
	 * @param f
	 * @throws FileNotFoundException
	 */
	public void load(File f) throws FileNotFoundException {
		Scanner fin = new Scanner(f);
		
		ArrayList<String> params = new ArrayList<String>();
		while(fin.hasNextLine()) params.add(fin.nextLine());
		
		for(String i : params) {
			if(i.startsWith("isServer")) {
				if(i.endsWith("true")) Config.isServer = true; else Config.isServer = false;
			} else if(i.startsWith("isProcessor")) {
				if(i.endsWith("true")) Config.isProcessor = true; else Config.isProcessor = false;
			} else if(i.startsWith("serverPort")) {
				Config.serverPort = Integer.parseInt(i.substring(i.indexOf("=")+1));
			} else if(i.startsWith("processorPort")) {
				Config.processorPort = Integer.parseInt(i.substring(i.indexOf("=")+1));
			} else System.err.println("Error loading param:\t" + i);
		}
	}
}
