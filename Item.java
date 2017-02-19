package treehacks;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.JButton;

public class Item
{
	private Image image;
	private boolean active = false;
	
	public Item(Image image){
		this.image = image;
	}
	
	public void setImage(Image image){this.image = image;}
	public Image getImage(){return image;}

	public boolean isActive() {return active;}
	public void setActive(boolean active) {this.active = active;}	
}
