package mcwebmap.vaadin;

import java.util.List;

import org.apache.log4j.Logger;

import mcwebmap.logic.MapGenQueueCB;
import mcwebmap.logic.MapImageGenerator;

import com.vaadin.ui.Panel;




// right side of main layout
public class QueueList 
extends Panel
implements MapGenQueueCB
{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private QueueTable processingQueue = new QueueTable();
	private QueueTable waitingQueue = new QueueTable();
	
	public QueueList(){
		processingQueue.setHeight("100px");
		
		this.addComponent(processingQueue);
		this.addComponent(waitingQueue);
	}

	public void setWaitingQueue(final List<MapImageGenerator> processQueue) {
		waitingQueue.setQueue(processQueue);
	}

	public void setProcessingQueue(final List<MapImageGenerator> processQueue) {
		processingQueue.setQueue(processQueue);
	}
	
}
