package mcwebmap.vaadin;


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import mcwebmap.logic.MapGenQueue;
import mcwebmap.logic.MapGenQueueCB;
import mcwebmap.logic.MapImageGenerator;
import mcwebmap.vaadin.generators.MCMap;
import mcwebmap.vaadin.generators.VaadinGenerator;

import org.vaadin.artur.icepush.ICEPush;


import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;


public class MainPanel 
extends Panel 
implements MapGenQueueCB
{
	// I want to notify the clients if I want an update
	//private ICEPush pusher = new ICEPush();
	
	private GridLayout mainLayout = new GridLayout(2,2);
	
	private List<VaadinGenerator> mapGenerators = new ArrayList<VaadinGenerator>();
	private MCWebMapApplication application = null;
	
	private QueueList queueList = null;
	
	public MainPanel(MapGenQueue mgf, Properties properties, MCWebMapApplication mcWebMapApplication){
		this.setCaption("Main");
		this.addComponent(mainLayout);
		application = mcWebMapApplication;
		mapGenerators.add(new MCMap(properties));
		
		
		
		// TODO: this assumes all on one page, perhaps make this a tab or something?
		for(VaadinGenerator gen: mapGenerators){
			mainLayout.addComponent(gen.getOptionsGridLayout());
		}

		// now the stat area
		queueList = new QueueList();
		mainLayout.addComponent(queueList);//,      0, 1);
		
		//statLayout.setComponentAlignment(labelQueue, Alignment.TOP_RIGHT);
		//statLayout.setComponentAlignment(labelQueueValue, Alignment.TOP_RIGHT);
		//this.addComponent(pusher);
		
		// now add myself to listen for updates
		mgf.addCallback(this);
	}

	// callback of processing list
	public void setWaitingQueue(final List<MapImageGenerator> processQueue) {
		queueList.setWaitingQueue(processQueue);
		application.update();
		System.out.println("Maps waiting:"+processQueue.size());
	}

	public void setProcessingQueue(List<MapImageGenerator> processQueue) {
		queueList.setProcessingQueue(processQueue);
		application.update();
		System.out.println("Maps being processed:"+processQueue.size());
	}
}
