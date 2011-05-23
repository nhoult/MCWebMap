package mcwebmap.vaadin.generators;

import org.apache.log4j.Logger;

import mcwebmap.logic.MapImageGenerator;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;

public abstract class VaadinGenerator {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	protected MapImageGenerator mapImageGenerator = null;
	
	public abstract GridLayout getOptionsGridLayout();
	
	//public abstract Panel getQueueInfo();
	
	public MapImageGenerator getMapImageGenerator(){
		if(mapImageGenerator == null){
			logger.error("VaadinGenerator: mapImageGenerator==null!");
		}
		return mapImageGenerator;
	}
}
