package mcwebmap.vaadin;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import mcwebmap.logic.MapImageGenerator;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.themes.Runo;

public class QueueList 
extends Panel
{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	// right side of main layout
	private GridLayout statLayout = new GridLayout(2,1);
	private Label labelQueue = new Label("Waiting in Queue: ");
	private Label labelQueueValue = new Label("0");
	
	public void QueueList(){
		//addStyleName(Runo.PANEL_LIGHT);
		
		this.addComponent(statLayout);
		statLayout.addComponent(labelQueue,      0, 0);
		statLayout.addComponent(labelQueueValue, 1, 0);
	}
	
	public void setQueue(final List<MapImageGenerator> processQueue){
		this.removeAllComponents();
		
		this.addComponent(statLayout);
		labelQueueValue.setValue(processQueue.size());
		
		for(MapImageGenerator mig: processQueue){
			this.addComponent(new QueueGUI(mig));
		}
	}
	
}
