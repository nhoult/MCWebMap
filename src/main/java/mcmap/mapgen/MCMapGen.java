package mcmap.mapgen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import mcmap.MapImageGenerator;

public class MCMapGen extends MapImageGenerator {

	private String mapDir;
	private String tmpDir;
	private String mcmapBin;
	public MCMapGen(String mcmapBin, String tmpDir, String mapDir){
		this.mcmapBin = mcmapBin;
		this.tmpDir = tmpDir;
		this.mapDir = mapDir;
	}
	
	@Override
	public BufferedImage genImage(int centerX, int centerY, int width,
			int height) throws IOException {
		String mapName = tmpDir+"/"+centerX+"X_"+centerY+"Y_"+width+"W_"+height+"H.png";
		
		String cmd = mcmapBin + 
		" -file " + mapName + 
		" -from "+(centerX - (width/2))+" "+ (centerY - (height/2)) +
		" -to "  +(centerX + (width/2))+" "+ (centerY + (height/2)) + 
		" " + mapDir;
		
		System.out.println("Going to run ["+cmd+"]");
		// call the system mcmap + args
		Process p = Runtime.getRuntime().exec(cmd);
		
		// when it is done get the generated image
		Scanner sc = new Scanner(p.getInputStream());
		while (sc.hasNext()) System.out.println(sc.nextLine());
		
/*		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	*/	
		// return the BufferedImage
		return ImageIO.read(new File(mapName));
	}

}
