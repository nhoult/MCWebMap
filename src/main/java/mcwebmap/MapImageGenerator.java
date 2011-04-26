package mcwebmap;

import java.awt.image.BufferedImage;
import java.io.IOException;

// TODO: add validation calls for x, y, width, height?
// TODO: add callback for possible status updates to client

public abstract class MapImageGenerator {
	public abstract BufferedImage genImage(int centerX, int centerY, int width, int height) throws IOException;
}
