package mcwebmap.logic.generators;

import java.awt.image.BufferedImage;
import java.io.IOException;

import mcwebmap.logic.MapImageGenerator;



import com.vaadin.ui.GridLayout;


public class BadMapGen extends MapImageGenerator {

	@Override
	public BufferedImage getImage() throws IOException {
		throw new IOException("Example bad map gen call");
		//return null;
	}

//	@Override
//	public GridLayout getOptionsGridLayout() {
//		return null;
//	}

	@Override
	public String getTabTitle() {
		return "Bad Result";
	}

	public void setImage(BufferedImage bi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getGeneratorLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getXLoc() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getYLoc() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
