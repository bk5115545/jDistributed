import java.io.Serializable;
import java.lang.reflect.Constructor;


public class Program implements Serializable {
	private static final long serialVersionUID = -9039745098171533512L;

	private Object[] arguements = null;
	
	@SuppressWarnings("rawtypes")
	Class toCall = null;
	
	@SuppressWarnings("rawtypes")
	public Program(Class c) {
		toCall = c;
	}
	
	@SuppressWarnings("rawtypes")
	public Program(Class c, Object[] arguements) {
		toCall = c;
		this.arguements = arguements;
	}
	
	

	@SuppressWarnings({ "rawtypes" })
	public void run() {
		try {
		if(arguements==null) { toCall.newInstance(); }
		else {
			Constructor[] constructors = toCall.getConstructors();
			for(int i=0; i<constructors.length; i++) {
				//only doing a length check not a type check yet.
				//should change this to use the generic types of the required parameters, possibly allowing for programmatic declaration of parameter types
				if(constructors[i].isAccessible() && constructors[i].getParameterTypes().length == arguements.length) {
					try {
						constructors[i].newInstance(arguements);
						break;
					} catch (Exception e) {
						System.out.println("Improper constructor called.");
						continue;
					}
					//i really hope we actually ran this only once
				}
			}
		}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		Main.processor.programFinished();
	}
	
}
