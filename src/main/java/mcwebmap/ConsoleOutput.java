package mcwebmap;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.artur.icepush.ICEPush;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ConsoleOutput extends VerticalLayout implements MapImageGeneratorCB
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private ICEPush pusher = new ICEPush();
	private List<Label> labels = new ArrayList<Label>();
	private int numLines = 0;
	
	
	public ConsoleOutput(){
		this(20); 
	}
	
	public ConsoleOutput(int lines){
		this.numLines = lines;
		this.addComponent(pusher);
	}
	
	public void println(String line){
		Label newLabel = new Label(line);
		
		labels.add(newLabel); // add it to the list
		if(labels.size() > numLines){ // of we have more lines than we want...
			this.removeComponent(labels.remove(0)); // remove the oldest line
		}
		this.addComponent(newLabel); // add a new line
		
		pusher.push(); // update GUI
	}

	public void message(String message) {
		println(message);
	}

	public void percentDone(int percent) {
		// don't care
	}
}
