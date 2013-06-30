import java.io.Serializable;


public class Config implements Serializable {
	private static final long serialVersionUID = 7960862509499754919L;
	public static boolean isServer = true;
	public static boolean isProcessor = true;
	public static int serverPort = 10100;
	public static int processorPort = 10101;
}