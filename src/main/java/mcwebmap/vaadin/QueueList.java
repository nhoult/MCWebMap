package mcwebmap.vaadin;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import mcwebmap.logic.MapGenQueueCB;
import mcwebmap.logic.MapImageGenerator;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;




// right side of main layout
public class QueueList 
extends Panel
implements MapGenQueueCB
{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private QueueTable processingQueue = new QueueTable();
	private QueueTable waitingQueue = new QueueTable();
	private int rowHeightPx = 28;
	private Label labelProcessing = new Label("Processing");
	private Label labelWaiting = new Label("Waiting");
	
	public QueueList(Properties properties){
		//processingQueue.setHeight("100px");
		processingQueue.setHeight(((Integer.parseInt(properties.getProperty("concurrent.mapgen"))+1)*rowHeightPx)+"px");
		
		this.addComponent(labelProcessing);
		this.addComponent(processingQueue);
		this.addComponent(labelWaiting);
		this.addComponent(waitingQueue);
	}

	public void setWaitingQueue(final List<MapImageGenerator> processQueue) {
		waitingQueue.setQueue(processQueue);
	}

	public void setProcessingQueue(final List<MapImageGenerator> processQueue) {
		processingQueue.setQueue(processQueue);
	}
	
}
