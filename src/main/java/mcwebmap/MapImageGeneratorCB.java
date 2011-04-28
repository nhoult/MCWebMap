package mcwebmap;

public interface MapImageGeneratorCB {
	// for things like console out and such
	public void message(String message);
	// for a progress bar, 0-100
	public void percentDone(int percent);
}
