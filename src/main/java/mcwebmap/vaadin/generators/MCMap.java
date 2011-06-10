package mcwebmap.vaadin.generators;

import java.util.Properties;

import mcwebmap.logic.generators.MCMapGen;
import mcwebmap.vaadin.ImageTabPanel;
import mcwebmap.vaadin.MCWebMapApplication;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MCMap
extends VaadinGenerator
{
	//private final MapImageGenerator parent = this;
	
	private GridLayout optionsLayout = new GridLayout(2,5);
	private Label label_xl = new Label("X Coordinate");
	private TextField textfield_xl = new TextField();
	private Label label_yl = new Label("Y Coordinate");
	private TextField textfield_yl = new TextField();
	private Label label_width = new Label("Width:");
	private TextField textfield_width = new TextField();
	private Label label_height = new Label("Height:");
	private TextField textfield_height = new TextField();
	private Button button_show_map = new Button("Show Map");
	
	private int centerX = 0;
	private int centerY = 0;
	private int width = 0;
	private int height = 0;

	private String mcmapBin;
	private String tmpDir; 
	// TODO make it a drop down box
	private String mapDir = "/home/minecraft/Games/minecraft/first_world7";
	
	
	public MCMap(final Properties properties)
	{
		//mapImageGenerator = new MCMapGen(
		//		properties.getProperty("mcmap.bin"), 
		//		properties.getProperty("mcmap.tmpdir"));
		
//		this.mcmapBin = mcmapBin;
//		this.tmpDir = tmpDir;
		
		
		optionsLayout.addComponent(label_xl,        0, 0);
		optionsLayout.addComponent(textfield_xl,    1, 0);
		optionsLayout.addComponent(label_yl,        0, 1);
		optionsLayout.addComponent(textfield_yl,    1, 1);
		optionsLayout.addComponent(label_width,     0, 2);
		optionsLayout.addComponent(textfield_width, 1, 2);
		optionsLayout.addComponent(label_height,    0, 3);
		optionsLayout.addComponent(textfield_height,1, 3);
		optionsLayout.addComponent(button_show_map, 0, 4);
		
		button_show_map.addListener(new ClickListener(){ 
			public void buttonClick(ClickEvent event) {
				boolean problem = false;
				
				try {
					centerX = Integer.parseInt(textfield_xl.getValue().toString().trim());
					textfield_xl.setComponentError(null);
				} catch (Exception e){
					problem = true;
					textfield_xl.setComponentError(new UserError("Bad number format"));
				}
				
				try {
					centerY = Integer.parseInt(textfield_yl.getValue().toString().trim());
					textfield_yl.setComponentError(null);
				} catch (Exception e){
					problem = true;
					textfield_yl.setComponentError(new UserError("Bad number format"));
				}
				
				try {
					width = Integer.parseInt(textfield_width.getValue().toString().trim());
					textfield_width.setComponentError(null);
					if(width > 64){
						width = 64;
					}
				} catch (Exception e){
					problem = true;
					textfield_width.setComponentError(new UserError("Bad number format"));
				}
				try {
					height = Integer.parseInt(textfield_height.getValue().toString().trim());
					textfield_height.setComponentError(null);
					if(height > 64){
						height = 64;
					}
				} catch (Exception e){
					problem = true;
					textfield_height.setComponentError(new UserError("Bad number format"));
				}
				
				if(!problem){	
					mapImageGenerator = new MCMapGen(
									properties.getProperty("mcmap.bin"), 
									properties.getProperty("mcmap.tmpdir"),
									mapDir,
									centerX,
									centerY,
									width,
									height);
					// ok we need a new tab
					ImageTabPanel itp = new ImageTabPanel(mapImageGenerator);
					// attach the tab as a target of our map generator
					mapImageGenerator.addCallback(itp);
					// add the tab to the browser
					MCWebMapApplication.getInstance().addTab(itp, true);
					// add myself to the queue of processors
					MCWebMapApplication.getInstance().genMapImage(mapImageGenerator);
				}
			}
		});
	}
	
	
	@Override
	public GridLayout getOptionsGridLayout() {
		return optionsLayout;
	}


	@Override
	public String getTabTitle() {
		return "mcmap";
	}
}
