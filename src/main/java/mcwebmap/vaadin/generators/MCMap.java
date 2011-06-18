package mcwebmap.vaadin.generators;

import java.util.Properties;

import mcwebmap.logic.generators.MCMapGen;
import mcwebmap.vaadin.ImageTabPanel;
import mcwebmap.vaadin.MCWebMapApplication;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MCMap
extends VaadinGenerator
{
	private GridLayout optionsLayout = new GridLayout(2,14);
	
	private Label label_worldDir = new Label("Select World:");
	private Select worldDir = new Select();
	
	private Label label_xl = new Label("X Coordinate");
	private TextField textfield_xl = new TextField();
	
	private Label label_yl = new Label("Y Coordinate");
	private TextField textfield_yl = new TextField();
	
	private Label label_width = new Label("Width:");
	private TextField textfield_width = new TextField();
	
	private Label label_height = new Label("Height:");
	private TextField textfield_height = new TextField();
	
	private Label label_cave = new Label("Caves:");
	private CheckBox cb_cave = new CheckBox();
	//false, /*cave*/
	private Label label_blendCave = new Label("Blend Caves:");
	private CheckBox cb_blendCave = new CheckBox();
	//false, /*blendcave*/
	private Label label_night = new Label("Night:");
	private CheckBox cb_night = new CheckBox();
	//false, /*night*/
	private Label label_skylight = new Label("Skylight:");
	private CheckBox cb_skylight = new CheckBox();
	//false, /*skylight*/
	private Label label_minHeight = new Label("Min Levels:");
	private TextField textfield_minHeight = new TextField();
	//0, /*min*/
	private Label label_maxHeight = new Label("Max Levels:");
	private TextField textfield_maxHeight = new TextField();
	//127, /*max*/
	private Label label_direction = new Label("Direction:");
	private Select select_direction = new Select();
	//MCMapGen.DIR_NORTH  /*direction*/
	
	private Button button_show_map = new Button("Show Map");
	
	
	private int centerX = 0;
	private int centerY = 0;
	private int width = 0;
	private int height = 0;

	// not what you think it is, this is Z height
	private int minHeight = 0;
	private int maxHeight = 127;
	
	public class Direction{
		String direction;
		int value;
		
		public Direction(String direction, int value) {
			super();
			this.direction = direction;
			this.value = value;
		}
		
		public String getDirection() {
			return direction;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
		@Override
		public String toString(){
			return direction;
		}
	}
	
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
		
		worldDir.setContainerDataSource(container);
		// Set the caption mode to read the caption directly
	    // from the 'name' property of the bean
		worldDir.setItemCaptionMode(
	            Select.ITEM_CAPTION_MODE_PROPERTY);
		worldDir.setItemCaptionPropertyId("name");
		worldDir.select(container.getIdByIndex(0));
		worldDir.setNullSelectionAllowed(false);
		
		BeanItemContainer<Direction> dirContainer = new BeanItemContainer<Direction>(Direction.class);
		dirContainer.addItem(new Direction("NORTH", MCMapGen.DIR_NORTH));
		dirContainer.addItem(new Direction("EAST", MCMapGen.DIR_EAST));
		dirContainer.addItem(new Direction("SOUTH", MCMapGen.DIR_SOUTH));
		dirContainer.addItem(new Direction("WEST", MCMapGen.DIR_WEST));
		
		select_direction.setContainerDataSource(dirContainer);
		select_direction.setItemCaptionMode(
	            Select.ITEM_CAPTION_MODE_PROPERTY);
		select_direction.setItemCaptionPropertyId("direction");
		select_direction.select(dirContainer.getIdByIndex(1));
		
		//mapImageGenerator = new MCMapGen(
		//		properties.getProperty("mcmap.bin"), 
		//		properties.getProperty("mcmap.tmpdir"));
		
//		this.mcmapBin = mcmapBin;
//		this.tmpDir = tmpDir;
		
		textfield_minHeight.setValue("0");
		textfield_maxHeight.setValue("127");
		
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
		
		optionsLayout.addComponent(label_cave,      0, 5);
		optionsLayout.addComponent(cb_cave,         1, 5);
		
		optionsLayout.addComponent(label_blendCave, 0, 6);
		optionsLayout.addComponent(cb_blendCave,    1, 6);
		
		optionsLayout.addComponent(label_night,     0, 7);
		optionsLayout.addComponent(cb_night,        1, 7);
		
		optionsLayout.addComponent(label_skylight,  0, 8);
		optionsLayout.addComponent(cb_skylight,     1, 8);
		
		optionsLayout.addComponent(label_minHeight,    0, 9);
		optionsLayout.addComponent(textfield_minHeight,1, 9);
		
		optionsLayout.addComponent(label_maxHeight,    0, 10);
		optionsLayout.addComponent(textfield_maxHeight,1, 10);

		optionsLayout.addComponent(label_direction,    0, 11);
		optionsLayout.addComponent(select_direction,   1, 11);
		
		optionsLayout.addComponent(button_show_map,    0, 12);
		
		
		button_show_map.addListener(new ClickListener(){ 
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
				
				
				try {
					minHeight = Integer.parseInt(textfield_minHeight.getValue().toString().trim());
					textfield_height.setComponentError(null);
					if(minHeight >= 127){
						minHeight = 127;
					} else if (minHeight <= 0){
						minHeight = 0;
					}
				} catch (Exception e){
					problem = true;
					textfield_minHeight.setComponentError(new UserError("Bad number format"));
				}
				try {
					maxHeight = Integer.parseInt(textfield_maxHeight.getValue().toString().trim());
					textfield_height.setComponentError(null);
					if(maxHeight >= 127){
						maxHeight = 127;
					} else if (maxHeight <= 0){
						maxHeight = 0;
					}
				} catch (Exception e){
					problem = true;
					textfield_maxHeight.setComponentError(new UserError("Bad number format"));
				}
				
				if(!problem){	
					mapImageGenerator = new MCMapGen(
									properties.getProperty("mcmap.bin"), 
									properties.getProperty("mcmap.tmpdir"),
									((MapDir)worldDir.getValue()).getPath(),
									centerX,
									centerY,
									width,
									height,
									cb_cave.booleanValue(), /*cave*/
									cb_blendCave.booleanValue(), /*blendcave*/
									cb_night.booleanValue(), /*night*/
									cb_skylight.booleanValue(), /*skylight*/
									minHeight, /*min*/
									maxHeight, /*max*/
									((Direction)select_direction.getValue()).getValue()  /*direction*/);
					
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
