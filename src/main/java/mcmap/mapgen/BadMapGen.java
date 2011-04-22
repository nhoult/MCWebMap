package mcmap.mapgen;

import java.awt.image.BufferedImage;
import java.io.IOException;

import mcmap.MapImageGenerator;

public class BadMapGen extends MapImageGenerator {

	@Override
	public BufferedImage genImage(int centerX, int centerY, int width,
			int height) throws IOException {
		throw new IOException("Example bad map gen call");
		//return null;
	}

}
