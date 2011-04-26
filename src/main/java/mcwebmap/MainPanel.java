package mcwebmap;

import java.io.IOException;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class MainPanel extends Panel{
	private Label label_xl = new Label("X Coordinate");
	private TextField textfield_xl = new TextField();
	private Label label_yl = new Label("Y Coordinate");
	private TextField textfield_yl = new TextField();
	private Label label_width = new Label("Width:");
	private TextField textfield_width = new TextField();
	private Label label_height = new Label("Height:");
	private TextField textfield_height = new TextField();
	private Button button_show_map = new Button("Show Map");
	
	private GridLayout layout = new GridLayout(2,5);
	
	public MainPanel(){
		this.setCaption("Main");
		this.addComponent(layout);

		layout.addComponent(label_xl, 0, 0);
		layout.addComponent(textfield_xl, 1, 0);
		layout.addComponent(label_yl, 0, 1);
		layout.addComponent(textfield_yl, 1, 1);
		layout.addComponent(label_width, 0, 2);
		layout.addComponent(textfield_width, 1, 2);
		layout.addComponent(label_height, 0, 3);
		layout.addComponent(textfield_height, 1, 3);
		layout.addComponent(button_show_map, 0, 4);
		
		button_show_map.addListener(new ClickListener(){ 
			public void buttonClick(ClickEvent event) {
				int centerX = 0;
				int centerY = 0;
				int width = 0;
				int height = 0;
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
					try {
						MCWebMapApplication.getInstance().addTab(new ImageTabPanel(centerX, centerY, width, height), true);
					} catch (IOException e) {
						e.printStackTrace();
						button_show_map.setComponentError(new UserError(e.getMessage()));
					}
				}
			}
		});
	}
}
