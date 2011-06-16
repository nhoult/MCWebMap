package mcwebmap.vaadin.generators;

import java.util.Properties;

import mcwebmap.logic.generators.MCMapGen;
import mcwebmap.vaadin.ImageTabPanel;
import mcwebmap.vaadin.MCWebMapApplication;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MCMap
extends VaadinGenerator
{
	//private final MapImageGenerator parent = this;
	private Label label_worldDir = new Label("Select World:");
	private Select worldDir = null;
	
	private GridLayout optionsLayout = new GridLayout(2,6);
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
	//private String mapDir = "/home/minecraft/Games/minecraft/first_world7";
	
	public class MapDir{
		String name;
		String path;

		public MapDir(String name, String path) {
			super();
			this.name = name;
			this.path = path;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
		
	}
	
	public MCMap(final Properties properties)
	{
		BeanItemContainer<MapDir> container = new BeanItemContainer<MapDir>(MapDir.class);
		// read properties up to 1024 worlds for the name and directory
		for(int i = 0; i < 1024; ++i){
			String dir = properties.getProperty("world"+i+".map.directory", null);
			String name = properties.getProperty("world"+i+".map.name", null);
			// if either value is null we are done
			if(dir == null || name == null){
				break;
			}
			container.addItem(new MapDir(name, dir));
		}
		
		worldDir = new Select("", container);
		// Set the caption mode to read the caption directly
	    // from the 'name' property of the bean
		worldDir.setItemCaptionMode(
	            Select.ITEM_CAPTION_MODE_PROPERTY);
		worldDir.setItemCaptionPropertyId("name");
		
		//mapImageGenerator = new MCMapGen(
		//		properties.getProperty("mcmap.bin"), 
		//		properties.getProperty("mcmap.tmpdir"));
		
//		this.mcmapBin = mcmapBin;
//		this.tmpDir = tmpDir;
		
		optionsLayout.addComponent(label_worldDir,  0, 0);
		optionsLayout.addComponent(worldDir,        1, 0);
		optionsLayout.addComponent(label_xl,        0, 1);
		optionsLayout.addComponent(textfield_xl,    1, 1);
		optionsLayout.addComponent(label_yl,        0, 2);
		optionsLayout.addComponent(textfield_yl,    1, 2);
		optionsLayout.addComponent(label_width,     0, 3);
		optionsLayout.addComponent(textfield_width, 1, 3);
		optionsLayout.addComponent(label_height,    0, 4);
		optionsLayout.addComponent(textfield_height,1, 4);
		optionsLayout.addComponent(button_show_map, 0, 5);
		
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
									((MapDir)worldDir.getValue()).getPath(),
									//"foo",
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
