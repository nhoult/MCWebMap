package mcwebmap.logic;

import java.io.IOException;
import java.util.List;


/*
 * Given an array of MapImageGenerator to monitor, try to grab
 * one and call doWork() on it.
 */

public class MapGenThread 
implements Runnable 
{

	private List<MapImageGenerator> processQueue = null;
	private Boolean running = true;
	private MapGenQueue mapGenQueue;
	private MapImageGenerator localMIG = null;
	
	public MapGenThread(List<MapImageGenerator> processQueue, MapGenQueue mapGenQueue){
		this.processQueue = processQueue;
		this.mapGenQueue = mapGenQueue;
		new Thread(this).start();
	}
	
	public void halt() {
		synchronized(this.running){
			this.running = false;
		}
		// TODO: should be a better way to do this...?
		synchronized(processQueue){
			processQueue.notifyAll();
		}
	}

	public MapImageGenerator getMapImageGenerator(){
		return localMIG;
	}
	
	public void run() {
		Boolean running;
		synchronized(this.running){
			running = this.running;
		}
		while(running){
			localMIG = null;
			synchronized(processQueue){
				if(!processQueue.isEmpty()){
					localMIG = processQueue.remove(0);
					mapGenQueue.setWaitingQueue(processQueue);
					mapGenQueue.updateProcessingQueue();
				} else {
					try {
						processQueue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
						running = false;
					}
				}
			}
			try {
				if(localMIG != null){
					localMIG.doWork();
				}
			} catch (IOException e) {
				e.printStackTrace();
				// print the error to the tab 'console'
				for(StackTraceElement s: e.getStackTrace()){
					localMIG.message(s.toString());
				}

			} finally {
				localMIG = null; // we are done with it
				mapGenQueue.updateProcessingQueue();
			}
			synchronized(this.running){
				running = this.running;
			}
		}
	}

	
}
