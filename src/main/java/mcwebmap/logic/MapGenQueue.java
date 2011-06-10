package mcwebmap.logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;


/*
 * Basically an array of factories that deals with the handing out of
 * generator classes as well as accepting them to be processed
 * 
 */

public class MapGenQueue 
//implements MapGenQueueCB
{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	// to anyone who wants to know what is going on in the queue
	private List<MapGenQueueCB> callbacks = new ArrayList<MapGenQueueCB>();
	// array of factories to read from
	//private List<MapImageGeneratorFactory> migf = new ArrayList<MapImageGeneratorFactory>();
	// queue of waiting to be processed generators
	private List<MapImageGenerator> processQueue = new ArrayList<MapImageGenerator>();
	// threads to do the work
	private List<MapGenThread> threads = new ArrayList<MapGenThread>();
	
	private Boolean inited = false;
	
	public MapGenQueue(){
	}
	
	public void init(Properties properties){
		synchronized(inited){
			if(!inited){
				// lets add some worker threads
				for(int i = 0; i < Integer.parseInt(properties.getProperty("concurrent.mapgen")); ++i){
					threads.add(new MapGenThread(processQueue,this));
				}
			}
		}
	}
	
	public void genMapImage(MapImageGenerator mapGen){
		synchronized(processQueue){
			// first see if there is an equal on the queue already
			for(MapImageGenerator local: processQueue){
				// are they equal?
				if(local.equals(mapGen)){
					// if they are lets just add another target for when it is rendered
					local.addAllCallback(mapGen.getCallbacks());
					// we are done for now, let a worker thread pick it up and do work
					return;
				}
			}
			// if it didn't find a match then we got here
			processQueue.add(mapGen);
			
			// tell anyone waiting the queue has been updated
			setWaitingQueue(processQueue);
			
			// tell someone we need to do some work!
			processQueue.notify();
		}
		
	}
	
	public void addCallback(MapGenQueueCB callback){
		callbacks.add(callback);
	}
	
	public void removeCallback(MapGenQueueCB callback){
		callbacks.remove(callback);
	}
	
	public void clearCallbacks(){
		callbacks.clear();
	}

	public void updateProcessingQueue(){
		// the assumption is if there is a call to setWaitingQueue() that the 
		// processing queue has changed
		List<MapImageGenerator> busyList = getProcessingQueue();
		
		for(MapGenQueueCB cb: this.callbacks){
			cb.setProcessingQueue(busyList);
		}
	}
	
	public final List<MapImageGenerator> getProcessingQueue(){
		List<MapImageGenerator> busyList = new ArrayList<MapImageGenerator>();
		for(MapGenThread thread: threads){
			MapImageGenerator tmpMIG = thread.getMapImageGenerator();
			if(tmpMIG != null){
				busyList.add(tmpMIG);
			}
		}
		return busyList;
	}
	
	// notify everyone the queue has changed
	public void setWaitingQueue(final List<MapImageGenerator> processQueue) {
		synchronized(processQueue){
			for(MapGenQueueCB cb: this.callbacks){
				cb.setWaitingQueue(processQueue);
			}
		}
	}
	
	public final List<MapImageGenerator> getWaitingQueue(){
		return processQueue;
	}
}
