import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class MeshNetwork implements Serializable {
	private static final long serialVersionUID = 8487145816502035366L;
	
	private static Set<Connection> s = Collections.synchronizedSet(new HashSet<Connection>());
	
	public static Set<Connection> getServers() {
		Set<Connection> c = Collections.synchronizedSet(new HashSet<Connection>());
		for(Connection con : s) if(con.isServer) c.add(con);
		return c;
	}
	
	public static Set<Connection> getProcessors() {
		Set<Connection> c = Collections.synchronizedSet(new HashSet<Connection>());
		for(Connection con : s) if(con.isProcessor) c.add(con);
		return c;
	}
	
	public static Set<Connection> getConnections() {
		return s;
	}

}
