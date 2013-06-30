import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Processor extends Thread {

	private boolean running = true;
	private boolean active = true;

	Set<Program> jobs = Collections.synchronizedSet(new HashSet<Program>());
	Program[] cache = null;
	ExecutorService executor = null;

	public Processor(int threadCount) {
		executor = Executors.newFixedThreadPool(threadCount);
	}

	@SuppressWarnings("static-access")
	public void run() {
		while(running) {
			if(!active) {
				try {
					Thread.currentThread().sleep(10);
					continue;
				} catch (InterruptedException e) {continue;}
			} else {
				if(jobs.size()>0) {
					cache = jobs.toArray(new Program[0]);
					for(Program p : cache) {
						if(!running || !active) break;
						executor.execute(p);
						jobs.remove(p); //need something better to not double process jobs (this will probably concurrentAccessModificiation error often) (cache completed jobs maybe)
					}
				} else try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {}
			}
		}
	}

	public void kill() {
		running = false;
	}

	public void pauseProcessing() {
		active = false;
	}

	public void continueProcessing() {
		active = true;
	}

	public boolean isPaused() {
		return !active;
	}

	public boolean isRunning() {
		return running;
	}

}
