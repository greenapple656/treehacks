package treehacks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private ArrayList<Item> currItems;
	private ArrayList<Item> possItems;
	private Image image;
	private ArrayList<Link> links;
	
	//"lazy" constructor
	public Scene(String name, Image image)
	{
		this.name = name;
		this.image = image;
	}
	
	//constructor
	public void addButtons(JFrame root, Inventory ivt, HashMap<String, Scene> ht, 
							Link[] links, 
							HashMap<Rectangle, Item> items,
							HashMap<Rectangle, Reception> npcs)
	{
		setLayout(null);
		this.links = new ArrayList<Link>();
		//currItems = (ArrayList<Item>) items.values();
		for(Link link:links)
		{
			add(link);
			this.links.add(link);
		}
			
		for(Rectangle rect:items.keySet())
		{
			//ImageIcon icon = new ImageIcon(items.get(rect).getImage());
			JButton button = new JButton();//icon);
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			
			final HashMap<Rectangle, Item> fitems = items;
			final JButton fbutton = button;
			final Inventory fivt = ivt;
			final Rectangle frect = rect;
			final JFrame froot = root;
			button.addActionListener(new ActionListener() { 			
				  public void actionPerformed(ActionEvent e) {
					fbutton.setVisible(false);
					fivt.addItem(fitems.get(frect));
					String name = fitems.get(frect).getName();
					TreeHacks.showText("You found "
							+ (name.substring(name.length()-1).equals("s")?"":"a ")
					+ fitems.get(frect).getName() + "\n\nTo check your inventory, roll your mouse to the bottom of the screen");
					froot.repaint();
				  } 
			});
			add(button);
			
			button.setSize(rect.width, rect.height);
			button.setLocation(rect.x, rect.y);
		}
		for(Rectangle rect:npcs.keySet())
		{
			Reception reception = npcs.get(rect);
			
			//hard-coding ArrayList here bc I'm desperate
			ArrayList<Item> possibleItems = new ArrayList<Item>();			
			for(Item item:TreeHacks.allItems)
			{if(item.getName().equals("key")){possibleItems.add(item);}}
			
			
			NPC npc = new NPC(reception, ivt, possibleItems);
			npc.setLocation(rect.x, rect.y);
			npc.setSize(rect.width, rect.height);
			add(npc);
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
	
	public void addLink(Link link)
	{
		add(link);
		links.add(link);
		repaint();
	}
	
	public void deleteLink(String name)
	{
		int removeIndex = 0;
		Link removeMe;
		for(int i=0; i<links.size(); i++)
		{
			if(links.get(i).getName()==null||links.get(i).getName().equals(name))
			{
				removeIndex = i;
				removeMe = links.get(i);
				remove(removeMe);
				break;
			}
		}
		links.remove(removeIndex);
		repaint();
	}
	
	public void printLinks()
	{
		for(Link link:links)
		{
			if(link!=null)
			System.out.println(link.getName());
		}
	}
	
	public void addNPC(Rectangle rect, NPC npc)
	{
		npc.setLocation(rect.x, rect.y);
		npc.setSize(rect.width, rect.height);
		add(npc);
	}

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
