package mcwebmap.logic.generators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
//import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import mcwebmap.logic.MapImageGenerator;


public class MCMapGen 
extends MapImageGenerator
{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private int centerX = 0;
	private int centerY = 0;
	private int width = 0;
	private int height = 0;

	private String mcmapBin;
	private String tmpDir; 
	
	private String mapDir = null;
	
	
	public MCMapGen(
			String mcmapBin,
			String tmpDir,
			String mapDir,
			int centerX,
			int centerY,
			int width,
			int height)
	{
		this.mcmapBin = mcmapBin;
		this.tmpDir = tmpDir;
		this.mapDir = mapDir;
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public BufferedImage getImage() 
	throws IOException
	{
		String mapName = tmpDir+"/"+getTabTitle();
		
		String cmd = mcmapBin + 
		" -file " + mapName + 
		" -from "+(centerX - (width/2))+" "+ (centerY - (height/2)) +
		" -to "  +(centerX + (width/2))+" "+ (centerY + (height/2)) + 
		" " + mapDir;
		
		logger.info("Going to run ["+cmd+"]");
		// call the system mcmap + args
		Process p = Runtime.getRuntime().exec(cmd);
		
		// when it is done get the generated image
		Scanner sc = new Scanner(p.getInputStream());
		while (sc.hasNext()){
			String line = sc.nextLine();
			logger.debug(line);
			this.message(line);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// return the BufferedImage
		return ImageIO.read(new File(mapName));
	}

	@Override
	public String getTabTitle() {
		return centerX+"X_"+centerY+"Y_"+width+"W_"+height+"H.png";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + centerX;
		result = prime * result + centerY;
		result = prime * result + height;
		result = prime * result + ((mapDir == null) ? 0 : mapDir.hashCode());
		result = prime * result
				+ ((mcmapBin == null) ? 0 : mcmapBin.hashCode());
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MCMapGen other = (MCMapGen) obj;
		if (centerX != other.centerX)
			return false;
		if (centerY != other.centerY)
			return false;
		if (height != other.height)
			return false;
		if (mapDir == null) {
			if (other.mapDir != null)
				return false;
		} else if (!mapDir.equals(other.mapDir))
			return false;
		if (mcmapBin == null) {
			if (other.mcmapBin != null)
				return false;
		} else if (!mcmapBin.equals(other.mcmapBin))
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	public void setImage(BufferedImage bi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getGeneratorLabel() {
		return "mcmap";
	}

	@Override
	public int getXLoc() {
		return centerX;
	}

	@Override
	public int getYLoc() {
		return centerY;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHight() {
		return height;
	}
	
}
