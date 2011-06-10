package mcwebmap.vaadin.generators;

import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;

public class Dummy 
extends VaadinGenerator
{
	
	private GridLayout optionsLayout = new GridLayout(2,5);
	private Label l = new Label("This is just a dummy generator");
	private Button b = new Button("Do nothing");
	
	public Dummy(){
		optionsLayout.addComponent(l, 0, 0);
		optionsLayout.addComponent(b, 0, 1);
	}
	
	
	@Override
	public GridLayout getOptionsGridLayout() {
		return optionsLayout;
	}

	@Override
	public String getTabTitle() {
		return "dummy";
	}

}
