package mcwebmap.vaadin;

import java.util.ArrayList;
import java.util.List;

import mcwebmap.logic.MapImageGeneratorCB;

import org.vaadin.artur.icepush.ICEPush;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ConsoleOutput extends VerticalLayout
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Label> labels = new ArrayList<Label>();
	private int numLines = 0;
	private MCWebMapApplication application = null;
	
	public ConsoleOutput(MCWebMapApplication application){
		this(application, 20); 
	}
	
	public ConsoleOutput(MCWebMapApplication application, int lines){
		this.numLines = lines;
		this.application = application;
	}
	
	public void println(String line){
		Label newLabel = new Label(line);
		
		labels.add(newLabel); // add it to the list
		if(labels.size() > numLines){ // of we have more lines than we want...
			this.removeComponent(labels.remove(0)); // remove the oldest line
		}
		this.addComponent(newLabel); // add a new line

		application.update();// update GUI
	}

	public void message(String message) {
		println(message);
	}

}
