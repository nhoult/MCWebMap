package mcwebmap.vaadin;

import java.awt.image.BufferedImage;
import java.io.IOException;

import mcwebmap.logic.MapImageGenerator;
import mcwebmap.logic.MapImageGeneratorCB;
import mcwebmap.logic.generators.MCMapGen;

import org.vaadin.artur.icepush.ICEPush;

import com.vaadin.ui.Panel;


public class ImageTabPanel 
extends Panel 
implements MapImageGeneratorCB
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private MCWebMapApplication application = null;
	private ImagePanel imagePanel = null;
	
	private ConsoleOutput consoleOut = null;
	private MapImageGenerator generator = null;
	
	
	public ImageTabPanel(MapImageGenerator generator)
	{
		this.generator = generator;
		application = MCWebMapApplication.getInstance();
		consoleOut = new ConsoleOutput(application, 20);
		this.imagePanel = new ImagePanel();
		
		// now do what we need to the web GUI
		synchronized(this){
			this.addComponent(consoleOut);
		}
		this.setCaption(generator.getTabTitle());
	}
	
	
	public void setImage(BufferedImage bi)
	{
		synchronized(this){
			this.removeComponent(consoleOut); // don't need the component anymore...
		}
		
		this.imagePanel.displayImage(bi, application, generator.getTabTitle()); // display the image

		this.setSizeUndefined(); // gives hor and ver scroll bars
		this.setHeight("100%"); // see the hor scroll bar the whole time instead of just at the bottom
		synchronized(this){
			this.addComponent(this.imagePanel); // lets add the image for the user to see.
		}
		// tell the client to refresh
		application.update();
		
		System.out.println("Sending image done");
	}

	public void message(String message) {
		consoleOut.message(message);
	}

	public void percentDone(int percent) {
		// TODO Auto-generated method stub
	}
}
