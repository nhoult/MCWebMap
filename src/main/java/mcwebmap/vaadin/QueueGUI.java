package mcwebmap.vaadin;

import org.apache.log4j.Logger;

import mcwebmap.logic.MapImageGenerator;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Runo;

public class QueueGUI 
extends Panel
{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	private Label queue_label_type = null;
	private Label queue_label_x = null;
	private Label queue_label_y = null;
	private Label queue_label_height = null;
	private Label queue_label_width = null;
	
	public QueueGUI(MapImageGenerator mig){
		
		addStyleName(Runo.PANEL_LIGHT);
		
		queue_label_type = new Label("Type: "+mig.getGeneratorLabel());
		queue_label_x = new Label("X: "+mig.getXLoc());
		queue_label_y = new Label("Y: "+mig.getYLoc());
		queue_label_width = new Label("Width: "+mig.getWidth());
		queue_label_height = new Label("Height: "+mig.getHight());
		
		
		this.addComponent(queue_label_type);
		this.addComponent(queue_label_x);
		this.addComponent(queue_label_y);
		this.addComponent(queue_label_width);
		this.addComponent(queue_label_height);
		
	}
}
