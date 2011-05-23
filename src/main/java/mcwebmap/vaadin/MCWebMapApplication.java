package mcwebmap.vaadin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import org.vaadin.artur.icepush.ICEPush;


import mcwebmap.logic.MapGenQueue;
import mcwebmap.logic.MapImageGenerator;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class MCWebMapApplication 
extends Application 
implements TransactionListener
{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// I want to notify the clients if I want an update
	private ICEPush pusher = new ICEPush();
	//private static ThreadLocal<ICEPush> currentPusher = new ThreadLocal<ICEPush>();//new ICEPush();
	
	// thread global var
	private static ThreadLocal<MCWebMapApplication> currentApplication = new ThreadLocal<MCWebMapApplication> ();
	
	// should be shared between all users
	private static Properties properties = new Properties();
	private static String propFile = "MCWebMap.properties";
	private static final MapGenQueue mgf = new MapGenQueue();
	
	public MCWebMapApplication()
	throws FileNotFoundException, IOException, URISyntaxException, Exception
	{
		logger.setLevel(Level.DEBUG);
		logger.debug("MCWebMapApplication()");
		// read a configuration file
		//  TODO: delivering it as part of the war has the down side of being wiped out
		// on upgrade, perhaps putting it some palace that is cross web server
		// supported?
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(propFile);
		if (stream == null) {
			logger.debug("File "+propFile+" not found");
			throw new Exception("File "+propFile+" not found");
		} else {
			properties.load(stream);
		} 

		
		mgf.init(properties);
	}
	
	@Override
	public void init() {
		logger.debug("init()");
		//ICEPush pusher = new ICEPush();
		//currentPusher.set(pusher);
		// create our tab sheet:
		MainTabs.create(this);
		TabSheet tabsheet = MainTabs.getTabSheet(); // get our tab sheet to use later
		//tabsheet.removeAllComponents();
		
		getContext().addTransactionListener(this); // so I will be called on each transaction
		
		Window mainWindow = new Window("MCWebMap Application");
		mainWindow.setContent(tabsheet);
		
		tabsheet.setHeight("100%"); // I don't want the browser to ever scroll
		MainPanel mp = new MainPanel(mgf, properties, this);
		addTab(mp, false);

		setMainWindow(mainWindow);
		
		mp.addComponent(pusher);
		//mainWindow.addComponent(pusher);
		//pusher.setVisible(false);
		//pusher.setWidth("0px");
		
	}

	public void addTab(Component c, boolean closeable){
		TabSheet tabsheet = MainTabs.getTabSheet();
		tabsheet.addComponent(c);
		tabsheet.getTab(c).setCaption(c.getCaption());
		c.setCaption(null); // the caption is in the tab now.
		tabsheet.getTab(c).setClosable(closeable);
	}
	
	public void update(){
		logger.debug("Sending update request to client");
		// Push the changes
		//ICEPush pusher = currentPusher.get();
		synchronized(this){
			pusher.push();
		}
	}
	
	public void genMapImage(MapImageGenerator mapGen){
		mgf.genMapImage(mapGen);
	}
	
    public static MCWebMapApplication getInstance()
    {
        return currentApplication.get();
    }

    
	public void transactionStart(Application application, Object transactionData) {
		if ( application == MCWebMapApplication.this )
        {
			logger.debug("transactionStart()");
            currentApplication.set ( this );
        }
	}
    
	public void transactionEnd(Application application, Object transactionData) {
		if ( application == MCWebMapApplication.this )
        {
			logger.debug("transactionEnd()");
            currentApplication.set ( null );
            currentApplication.remove ();
        }
	}


}
