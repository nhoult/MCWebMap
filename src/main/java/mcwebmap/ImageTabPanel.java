package mcwebmap;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.vaadin.Application;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;

public class ImageTabPanel extends Panel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MCWebMapApplication application;
	private ImagePanel imagePanel;
	private int centerX, centerY,  width, height;
	
	public ImageTabPanel(int centerX, int centerY, int width, int height)
	throws IOException
	{
		// prime everything the thread needs
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		
		application = MCWebMapApplication.getInstance();
		
		this.imagePanel = new ImagePanel(centerX+"X_"+centerY+"Y_"+width+"W_"+height+"H.png");
		// start the thread
		new Thread(this).start();
		//run();
		
		// now do what we need to the web GUI
		this.addComponent(this.imagePanel);
		this.setCaption(centerX+"X_"+centerY+"Y_"+width+"W_"+height+"H.png");

		//this.setSizeUndefined(); // gives hor and ver scroll bars
		//this.setHeight("100%"); // see the hor scroll bar the whole time instead of just at the bot
	}
	
	public void run() {
		BufferedImage bi;
		try {			
			bi = application.getMapImageGen().genImage(this.centerX, this.centerY, this.width, this.height);
			this.imagePanel.displayImage(bi, application, this.centerX+"X_"+this.centerY+"Y_"+this.width+"W_"+this.height+"H.png");
			
			
			
			this.setSizeUndefined(); // gives hor and ver scroll bars
			this.setHeight("100%"); // see the hor scroll bar the whole time instead of just at the bot
			
			this.imagePanel.requestRepaint();
			application.repaintTab();
			this.requestRepaint();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
