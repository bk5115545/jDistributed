import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Processor extends Thread {

	private boolean running = true;
	private boolean active = true;
	
	public int totalThreads = 0;
	private int usedThreads = 0;

	Set<Job> jobs = Collections.synchronizedSet(new HashSet<Job>());
	Job[] cache = null;
	ExecutorService executor = null;

	public Processor(int threadCount) {
		executor = Executors.newFixedThreadPool(threadCount);
		totalThreads = threadCount;
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
					cache = jobs.toArray(new Job[0]);
					for(Job j : cache) {
						if(!running || !active) break;
						executor.execute(j);
						jobs.remove(j);
						usedThreads++;
					}
				} else try {
					Thread.currentThread().sleep(1);
				} catch (InterruptedException e) {}
			}
		}
	}
	
	/**
	 * Queries the total job count minus the jobs currently executing
	 * @return If the returned number is negative then there are Math.abs number of jobs left in the queue.
	 */
	public int getAvailableThreadCount() {
		return totalThreads-usedThreads;
	}
	
	public void programFinished() {
		usedThreads--;
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
