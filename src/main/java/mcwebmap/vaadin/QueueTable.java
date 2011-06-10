package mcwebmap.vaadin;

import java.util.List;

import mcwebmap.logic.MapImageGenerator;

import org.apache.log4j.Logger;

import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;

public class QueueTable 
extends Table
{
		private Logger logger = Logger.getLogger(this.getClass().getName());

		//private static String[] cols   = new String[] { "Type", "X", "Y", "Width", "Height", "Gen" };
		
		private IndexedContainer queueData = new IndexedContainer();
		
		public QueueTable(){
			//this.setVisibleColumns(cols);
			//for (String p : cols) {
			//	queueData.addContainerProperty(p, String.class, "");
	        //}
			queueData.addContainerProperty("Type",   String.class, "");
			queueData.addContainerProperty("X",      String.class, "");
			queueData.addContainerProperty("Y",      String.class, "");
			queueData.addContainerProperty("Width",  String.class, "");
			queueData.addContainerProperty("Height", String.class, "");
			queueData.addContainerProperty("Gen",    Button.class, "");
			
			this.setContainerDataSource(queueData);
			this.setImmediate(true);
			this.setSortDisabled(true);
		}
		
		public void setQueue(final List<MapImageGenerator> processQueue){
			synchronized(queueData){
				queueData.removeAllItems();

				for (MapImageGenerator mig: processQueue) {
					Button b = new Button("Gen");
					
					Object id = queueData.addItem();
					queueData.getContainerProperty(id, "Type").setValue(mig.getGeneratorLabel());
					queueData.getContainerProperty(id, "X").setValue(mig.getXLoc());
					queueData.getContainerProperty(id, "Y").setValue(mig.getYLoc());
					queueData.getContainerProperty(id, "Height").setValue(mig.getHight());
					queueData.getContainerProperty(id, "Width").setValue(mig.getWidth());
					queueData.getContainerProperty(id, "Gen").setValue(b);
				}
			}
		}
		
}
