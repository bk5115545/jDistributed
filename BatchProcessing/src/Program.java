
public class Program implements Runnable {
	
	@SuppressWarnings("rawtypes")
	Class toCall = null;
	
	@SuppressWarnings("rawtypes")
	public Program(Class c) {
		toCall = c;
	}

	@SuppressWarnings("unused")
	@Override
	public void run() {
		try {
			Object instance = toCall.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
