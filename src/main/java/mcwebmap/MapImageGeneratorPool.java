package mcwebmap;

//import java.lang.ref.ReferenceQueue;
//import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/*
 * Yeah so first time using WeakReference and ReferenceQueue. not sure how well it will work or if I am doing it right
 * but if I am not learning I am not interested...
 */
public class MapImageGeneratorPool 
{
	private List<MapImageGenerator> freeGenerators = new ArrayList<MapImageGenerator>();
	//private ReferenceQueue<MapImageGenerator> deadGenerators = new ReferenceQueue<MapImageGenerator>();
	//private List<WeakReference<MapImageGenerator>> trackingGenerators = new ArrayList<WeakReference<MapImageGenerator>>();
	
	
//	private boolean running = true;
	
	public MapImageGeneratorPool(){
//		this.start();
	}
	
	public MapImageGenerator getMapImageGenerator(){
		MapImageGenerator retMap = null;
		while(retMap == null){
			synchronized(freeGenerators){
				if(freeGenerators.size() > 0){
					retMap = freeGenerators.remove(0);
					freeGenerators.notify();
				} else {
					try {
						System.out.println("Waiting on a MapGenerator to free up");
						freeGenerators.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
//		synchronized(trackingGenerators){
//			trackingGenerators.add(new WeakReference<MapImageGenerator>(retMap, deadGenerators));
//		}
		
		return retMap;
	}
	
	
	public void returnMapImageGenerator(MapImageGenerator mig){
//		synchronized(trackingGenerators){
//			for(WeakReference<MapImageGenerator> wrmig: trackingGenerators){
//				if(wrmig.get().equals(mig)){
//					System.out.println("Found in trackingGenerators, removing");
//					trackingGenerators.remove(wrmig);
//				}
//			}
//		}
		synchronized(freeGenerators){
			freeGenerators.add(mig); // add it to the array
			freeGenerators.notify(); // tell others one is ready
		}
	}
/*	
	@Override
	public void run(){
		while(running){
			try {
				MapImageGenerator reclaimedMapGen = null;
				synchronized(deadGenerators){
					reclaimedMapGen = deadGenerators.remove().get(); // only wake up if there is one in it
				}
				synchronized(trackingGenerators){
					trackingGenerators.remove(reclaimedMapGen); // remove it from the monitored
				}
				
				synchronized(freeGenerators){
					freeGenerators.add(reclaimedMapGen); // add it to be used again
					freeGenerators.notify(); // we got a new one, tell someone
				}
				System.out.println("Got a map generator that was not properly returned!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
}
