package treehacks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import acm.graphics.*;

public class Scene extends JPanel{
	
	//set coords before instantiating
	private String name;
	private HashMap<Rectangle, String> coords;
	private ArrayList<Item> currItems;
	private ArrayList<Item> possItems;
	private Image image;
	
	//"lazy" constructor
	public Scene(String name, Image image)
	{
		this.name = name;
		this.image = image;
	}
	
	//constructor
	public void addButtons(JFrame root, HashMap<String, Scene> ht, HashMap<Rectangle, String> links, HashMap<Rectangle, Item> items)
	{
		setLayout(null);
		coords = links;
		//currItems = (ArrayList<Item>) items.values();
		for(Rectangle rect:coords.keySet())
		{
			add(new Link(links.get(rect), ht, root, this, rect));
		}
		for(Rectangle rect:items.keySet())
		{
			ImageIcon icon = new ImageIcon(items.get(rect).getImage());
			JButton button = new JButton(icon);
			add(button);
			button.setSize(rect.width, rect.height);
			button.setLocation(rect.x, rect.y);
		}
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(image, 0, 0, null);
	}
	
	public String getName(){return name;}
	public void setName(String name){this.name = name;}
	
	public void setImage(Image image)
	{
		this.image = image;
		repaint();
	}
	
	//keeping these as ArrayLists, and including the addLink method because
	//not sure how we're going to deal with the data yet - if we'll add
	//it all at once or not
	public HashMap<Rectangle,String> getCoords(){return coords;}
	public void setCoords(HashMap<Rectangle,String> coords){this.coords = coords;}
	public void addCoord(Rectangle rect, String name){coords.put(rect, name);}

	public ArrayList<Item> getCurrItems() {return currItems;}
	public void setCurrItems(ArrayList<Item> currItems) 
	{this.currItems = currItems;}
	//returns true if you can add the item, false if you can't
	public boolean addCurrItem(Item item)
	{
		if(possItems.contains(item))
		{
			currItems.add(item); //the zero is just a placeholder right now
			return true;
		}
		return false;
	}
	public void removeCurrItem(Item item)
	{
		currItems.remove(item);
	}

	public ArrayList<Item> getPossItems() {return possItems;}
	public void setPossItems(ArrayList<Item> possItems) {this.possItems = possItems;}
	public void addPossItem(Item item) {possItems.add(item);}
}
