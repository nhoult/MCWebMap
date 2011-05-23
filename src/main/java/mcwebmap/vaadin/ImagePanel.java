package mcwebmap.vaadin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


import com.vaadin.Application;
import com.vaadin.terminal.StreamResource;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Window;

public class ImagePanel extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private class ImageSource implements StreamResource.StreamSource {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage bi;
		public ImageSource(BufferedImage bi){
			this.bi = bi;
		}
		
		public InputStream getStream() {
			try {
	            /* Write the image to a buffer. */
				ByteArrayOutputStream imagebuffer = new ByteArrayOutputStream();
	            ImageIO.write(bi, "png", imagebuffer);
	            
	            /* Return a stream from the buffer. */
	            return new ByteArrayInputStream( imagebuffer.toByteArray());
	        } catch (IOException e) {
	            return null;
	        }
		}
		
	}
	
	public ImagePanel(){
		super();
	}
	
	public ImagePanel(String caption){
		super(caption);
	}
	
	public ImagePanel(String caption, ComponentContainer content){
		super(caption,content);
	}
	
	
	public void displayImage(BufferedImage bi, Application  application, String fileName){
		ImagePanel.ImageSource source = new ImagePanel.ImageSource(bi);
		
        StreamResource imageresource = new StreamResource(source, fileName, application);
     // Instruct browser not to cache the image.
        imageresource.setCacheTime(0);
        
		this.addComponent(new Embedded(null, imageresource));
	}
}
