package treehacks;

import java.awt.Rectangle;

public class Link {
	public Scene scene;
	public boolean available;
	public Rectangle hotspot;
	
	public Link(Scene scene, boolean available, Rectangle hotspot)
	{
		this.scene = scene;
		this.available = available;
		this.hotspot = hotspot;
	}
}
