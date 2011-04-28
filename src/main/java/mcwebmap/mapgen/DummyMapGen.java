package mcwebmap.mapgen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import mcwebmap.MapImageGenerator;

public class DummyMapGen extends MapImageGenerator {

	private String fileName = null;
	
	public DummyMapGen(String fileName){
		this.fileName = fileName;
	}
	
	@Override
	public BufferedImage genImage(int centerX, int centerY, int width,
			int height) throws IOException {
		// "/home/nhoult/Downloads/247155957.png"
		return ImageIO.read(new File(fileName));
	}

}
