/*
 * File: TreeHacks.java
 * -------------------
 * 
 */ 

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.*;

import com.sun.medialib.mlib.Image;

public class TreeHacks extends GraphicsProgram {
	private void run() {
		Scene();
		addImage1();
		addMouseListeners();		
		setUp();
		getObjects();
		openDoor();
	}

	
	private void addImage1() {
		JImage image1 = new JImage("//image to be imported");
		image1.setSize(getWidth(); getHeight());
		image1.sendToBack();
		add(image1);		
		
	}
	
	//The  to mouse being clicked
	private void MouseClicked(MouseEvent e) {
		
	}
}


public class Scene {
	
	private ArrayList<Link> links;
	private HashMap<Item, Integer> currItems; //not sure exactly how we'll use the
		//integer, but we'll have to somehow connect adding a specific item with a 
		//change of state, and this is a possible way
	private ArrayList<Item> possItems; 
	private GImage image;
	
	
	//constructor that sets only the image
	public Scene(GImage newimage) {
		image = newimage;
	}
	
	
	public GImage getImage(){return image;}
	public void setImage(GImage image){this.image = image;}
	
	//keeping these as ArrayLists, and including the addLink method because
	//not sure how we're going to deal with the data yet - if we'll add
	//it all at once or not
	public ArrayList<Link> getLinks(){return links;}
	public void setLinks(ArrayList<Link> links){this.links = links;}
	public void addLink(Link link){links.add(link);}
	public void addLink(Scene scene, boolean bool, Rectangle hotspot)
	{links.add(new Link(scene,bool,hotspot));}

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

    Contact GitHub API Training Shop Blog About 

