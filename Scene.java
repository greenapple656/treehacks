package treehacks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import acm.graphics.*;

public class Scene extends JPanel{
	
	//set coords before instantiating
	private String name;
	private HashMap<Rectangle, String> coords;
	private HashMap<Item, Integer> currItems; //not sure exactly how we'll use the
		//integer, but we'll have to somehow connect adding a specific item with a 
		//change of state, and this is a possible way
	private ArrayList<Item> possItems;
	private Image image;
	
	//"lazy" constructor
	public Scene(String name, Image image)
	{
		this.name = name;
		this.image = image;
	}
	
	//constructor
	public Scene(String name, Image image, JFrame root, HashMap<String, Scene> ht)
	{
		this.name = name;
		this.image = image;
		setLayout(null);
		
		for(Rectangle rect:coords.keySet())
		{
			add(new Link(name, ht, root, this, rect));
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

	public HashMap<Item, Integer> getCurrItems() {return currItems;}
	public void setCurrItems(HashMap<Item, Integer> currItems) 
	{this.currItems = currItems;}
	//returns true if you can add the item, false if you can't
	public boolean addCurrItem(Item item)
	{
		if(possItems.contains(item))
		{
			currItems.put(item, 0); //the zero is just a placeholder right now
			return true;
		}
		return false;
	}

	public ArrayList<Item> getPossItems() {return possItems;}
	public void setPossItems(ArrayList<Item> possItems) {this.possItems = possItems;}
	public void addPossItem(Item item) {possItems.add(item);}
}
