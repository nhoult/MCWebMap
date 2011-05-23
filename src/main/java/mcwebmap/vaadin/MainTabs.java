package mcwebmap.vaadin;

import java.io.Serializable;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext.TransactionListener;
import com.vaadin.ui.TabSheet;

/*
 * Give credit where credit is due: https://vaadin.com/book/-/page/advanced.global.html
 * 
 */

public class MainTabs 
extends TabSheet
implements TransactionListener, Serializable
{
	private static ThreadLocal<TabSheet> instance = new ThreadLocal<TabSheet> ();
	
	public MainTabs() {
		super();
	}

	public static void create(Application app) {
        // Create the session-specific instance here
		MainTabs sessionData = new MainTabs();

        // It's usable globally from now on in this request
        instance.set(sessionData);

        // Register to get transaction events
        app.getContext().addTransactionListener(sessionData);
	}
	
	public void transactionStart(Application application, Object transactionData) {
		// TODO Auto-generated method stub
		instance.set(this);
	}

	public void transactionEnd(Application application, Object transactionData) {
		// TODO Auto-generated method stub
		instance.set(null);
	}
	
	public static TabSheet getTabSheet(){
		return instance.get();
	}
}
