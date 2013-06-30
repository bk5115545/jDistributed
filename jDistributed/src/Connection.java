import java.net.InetAddress;


public class Connection {

	public transient ServerThread serverThread = null;
	public transient ProcessorThread processorThread = null;
	public transient InetAddress me = null;
	public transient int localPort = 0;
	public boolean isServer = false;
	public boolean isProcessor = false;
	public int serverPort = 10100;
	public int processorPort = 10101;
	
	public Connection(InetAddress to, int port) {
		me = to;
		localPort = port;
	}
}
