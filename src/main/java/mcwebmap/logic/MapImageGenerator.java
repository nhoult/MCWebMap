package mcwebmap.logic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public abstract class MapImageGenerator 
implements MapImageGeneratorCB 
{
	private Set<MapImageGeneratorCB> callbacks = new HashSet<MapImageGeneratorCB>();
	
	public abstract BufferedImage getImage() throws IOException;
	
	public abstract String getTabTitle();
	
	public abstract String getGeneratorLabel();
	
	public abstract int getXLoc();
	public abstract int getYLoc();
	public abstract int getWidth();
	public abstract int getHight();
	
	public void doWork()
	throws IOException 
	{
		BufferedImage bi = getImage();
		for(MapImageGeneratorCB itp: callbacks){
			itp.setImage(bi);
		}
	}
	
	public void addCallback(MapImageGeneratorCB callback){
		callbacks.add(callback);
	}
	
	public void addAllCallback(Collection<MapImageGeneratorCB> callbacks){
		callbacks.addAll(callbacks);
	}
	
	public Collection<MapImageGeneratorCB> getCallbacks(){
		return callbacks;
	}
	
	public void removeCallback(MapImageGeneratorCB callback){
		callbacks.remove(callback);
	}
	
	public void removeAllCallbacks(){
		callbacks.clear();
	}
	
	// for things like console out and such
	public void message(String message){
		for(MapImageGeneratorCB cb: callbacks){
			cb.message(message);
		}
	}
	// for a progress bar, 0-100
	public void percentDone(int percent){
		for(MapImageGeneratorCB cb: callbacks){
			cb.percentDone(percent);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((callbacks == null) ? 0 : callbacks.hashCode());
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
		return true;
	}
	
}
