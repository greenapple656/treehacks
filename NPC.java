package treehacks;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

public class NPC extends JButton //button within a scene that can "get" an item
{
	private ArrayList<Item> currItems;
	private ArrayList<Item> possItems;
	private Reception screwOOP;
	
	public NPC(Reception reception, final Inventory ivt, ArrayList<Item> possItems)
	{
		//Scene.showText2("hi");
		currItems = new ArrayList<Item>();
		
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		
		screwOOP = reception;
		this.possItems = possItems;
		
		//final Inventory fivt = ivt;
		addActionListener(new ActionListener() { 			
			  public void actionPerformed(ActionEvent e) {
				  receive(ivt.getActive());
				  
				  for(int i=0; i<ivt.getItems().length; i++)
				  {if(ivt.getItems()[i]!=null &&ivt.getItems()[i].isActive()){ivt.removeItem(i);}}
				  ivt.repaint();
			  } 
		});
	}
	
	public void receive(Item item)
	{
		screwOOP.react(this);
		if(!possItems.contains(item)||item==null)
			return;
		currItems.add(item);
		TreeHacks.redraw();
	}
	
	public ArrayList<Item> getCurrItems(){return currItems;}
	public Item getLastItem(){return currItems.get(currItems.size()-1);}
	public void removeLastItem(){currItems.remove(currItems.size()-1);}
	public void removeItem(Item item){currItems.remove(item);}
}

interface Reception
{
	public void react(NPC receptor);
}