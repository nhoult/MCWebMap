package mcwebmap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Properties;

import mcmap.mapgen.MCMapGen;
import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.ui.*;


public class MCWebMapApplication extends Application implements ApplicationContext.TransactionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// thread global var
	private static ThreadLocal<MCWebMapApplication> currentApplication =
        new ThreadLocal<MCWebMapApplication> ();
	
	private TabSheet tabsheet = new TabSheet();
	private MapImageGenerator mig = null;
	
	private Properties properties = new Properties();
	
	private static String propFile = "MCWebMap.properties";
	Window mainWindow;
	
	public MCWebMapApplication()
	throws FileNotFoundException, IOException, URISyntaxException, Exception
	{
		System.out.println("MCWebMapApplication()");
		// read a configuration file
		//  TODO: delivering it as part of the war has the down side of being wiped out
		// on upgrade, perhaps putting it some palace that is cross web server
		// supported?
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream(propFile);
		if (stream == null) {
			//System.out.println("File "+propFile+" not found");
			throw new Exception("File "+propFile+" not found");
		} else {
			properties.load(stream);
		} 

		mig = new MCMapGen(properties.getProperty("mcmap.bin"), properties.getProperty("mcmap.tmpdir"), properties.getProperty("world.dir"));
		//mig = new DummyMapGen("/tmp/image.png");
		//mig = new BadMapGen();
	}
	
	@Override
	public void init() {
		System.out.println("init()");
		getContext().addTransactionListener(this);
		
		mainWindow = new Window("MCWebMap Application");
		mainWindow.setContent(tabsheet);
		
		tabsheet.setHeight("100%"); // I don't want the browser to ever scroll
		addTab(new MainPanel(), false);

		setMainWindow(mainWindow);
	}

	public void addTab(Component c, boolean closeable){
		tabsheet.addComponent(c);
		tabsheet.getTab(c).setCaption(c.getCaption());
		tabsheet.getTab(c).setClosable(closeable);
	}
	
	public void repaintTab(){
		//tabsheet.requestRepaint();
		
		mainWindow.requestRepaintAll();
	}
	
	public MapImageGenerator getMapImageGen(){
		return mig;
	}
	
    public static MCWebMapApplication getInstance()
    {
        return currentApplication.get();
    }

    
	public void transactionStart(Application application, Object transactionData) {
		if ( application == MCWebMapApplication.this )
        {
			System.out.println("transactionStart()");
            currentApplication.set ( this );
        }
	}
    
	public void transactionEnd(Application application, Object transactionData) {
		if ( application == MCWebMapApplication.this )
        {
			System.out.println("transactionEnd()");
            currentApplication.set ( null );
            currentApplication.remove ();
        }
	}


}
