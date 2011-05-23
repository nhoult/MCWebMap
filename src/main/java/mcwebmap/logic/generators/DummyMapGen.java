package mcwebmap.logic.generators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mcwebmap.logic.MapImageGenerator;



import com.vaadin.ui.GridLayout;


public class DummyMapGen extends MapImageGenerator {

	private String fileName = null;
	
	public DummyMapGen(String fileName){
		this.fileName = fileName;
	}
	
	@Override
	public BufferedImage getImage() throws IOException {
		// "/home/nhoult/Downloads/247155957.png"
		return ImageIO.read(new File(fileName));
	}

//	@Override
//	public GridLayout getOptionsGridLayout() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public String getTabTitle() {
		// TODO Auto-generated method stub
		return null;
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
