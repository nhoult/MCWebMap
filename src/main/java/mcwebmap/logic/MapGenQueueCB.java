package mcwebmap.logic;

import java.util.List;

public interface MapGenQueueCB {
	// if the list changes notify anyone listening of the new list
	public void setWaitingQueue(final List<MapImageGenerator> processQueue);
	
	// changes if a thread is working or stopped working on a request
	public void setProcessingQueue(final List<MapImageGenerator> processQueue);
}
