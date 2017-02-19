package treehacks;

/*
 * File: TreeHacks.java
 * -------------------
 * 
 */ 

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class TreeHacks {
	
	private static JFrame rootFrame;
	private static JLabel label;
	private static ArrayList<Scene> scenes;
	private static HashMap<String, Scene> ht;
	private static Inventory ivt;
	
	//public variables suck...but come on, this is *way* more elegant than any other
	//solution you can think of
	public static ArrayList<Item> allItems;
	public final static int WIDTH=1366;
	public final static int HEIGHT=768;
	
	public static void main(String[] args)
	{
		rootFrame = new JFrame();
		label = new JLabel();
		ivt = new Inventory();
		allItems = new ArrayList<Item>();
		
		rootFrame.getContentPane().add(label);
		label.setSize(1250,30);
		label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 25));
		label.setForeground(Color.RED);
		label.setVisible(false);
		label.setLocation(40,30);
		rootFrame.getContentPane().add(ivt);
		ivt.setSize(1366,200);
		ivt.setLocation(Inventory.MIN_X,Inventory.MIN_Y);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		toolkit.addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent e) {
            	if (MouseEvent.MOUSE_CLICKED == e.getID()) {
                    MouseEvent event = (MouseEvent) e;
                    hideText();
                }
            }
           },
        AWTEvent.MOUSE_MOTION_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK + AWTEvent.KEY_EVENT_MASK);
		
		initScenes();
		rootFrame.setSize(WIDTH,HEIGHT);
		
		scenes.get(0).setSize(WIDTH,HEIGHT);
		scenes.get(0).setLocation(0,0);	
		rootFrame.getContentPane().add(scenes.get(0));
		
		rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		rootFrame.setVisible(true);	    
	    rootFrame.repaint();
	    
	    showText("Intro text here");
	}
	
	public static void showText(String text)
	{
		label.setText("\t"+text);
		label.setVisible(true);
	}
	public static void hideText()
	{
		label.setVisible(false);
	}
	
	private static void initScenes()
	{
		scenes = new ArrayList<Scene>();
		ht = new HashMap<String, Scene>();
		String[] filenames = {"img/1_ Door.jpg","img/2_ Desk.jpg","img/3_ Man.jpg","img/4_ Bookshelf.jpg",
				"img/5_ Door CU.jpg","img/6_ Desk CU.jpg","img/7_ Man CU.jpg","img/8_ Bookshelf CU.jpg"};
		
		//"lazily" construct the objects
		for(int i=0; i<filenames.length; i++)
		{
			Image img;
			try {
			    img = ImageIO.read(new File(filenames[i]));
			    String name = "image" + (i+1);
			    Scene newscene = new Scene(name, img);
			    scenes.add(newscene);
			    ht.put(name, newscene);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}			
		}
		 
		//make HashMap<Rectangle, String> for the link lists of the scenes
		Rectangle[][] linkAreas = {{new Rectangle(0,380,75,95), //image1 - left
								new Rectangle(1275,380,58,95), //right
								new Rectangle(755,286,261,306), //door
								new Rectangle(1036,351,65,62)}, //keypad
								//image2: desk
								{new Rectangle(0,380,75,95), //left
								new Rectangle(1275,380,58,95), //right
								new Rectangle(254,423,550,194)}, //desk
								//image3: man
								{new Rectangle(0,380,75,95), //left
								new Rectangle(1275,380,58,95), //right
								new Rectangle(458,400,197,285)}, //man
								//image4: bookshelf
								{new Rectangle(0,380,75,95), //left
								new Rectangle(1275,380,58,95), //right
								new Rectangle(536,277,242,328),}, //bookshelf
								//image5: doorCU
								{new Rectangle(951,311,105,101), //keypad
								new Rectangle(418,125,488,575), //door
								new Rectangle(624,30,106,70)}, //back
								//image6: deskCU
								{new Rectangle(624,30,106,70)}, //back
								//image7: manCU
								{new Rectangle(624,30,106,70)}, //back
								//image8: bookshelfCU
								{new Rectangle(624,30,106,70)}}; //back
		
		String[][] linkNames = {{"image2","image4","image5","image5"},
								{"image3","image1","image6"},
								{"image4","image2","image7"},
								{"image1","image3","image8"},
								{"image5","image5","image1"},//door and keypad shouldn't work
								{"image2"},
								{"image3"},
								{"image4"}};
		
		HashMap<Rectangle, String>[] linkMaps = (HashMap<Rectangle, String>[]) new HashMap[8];
		for(int i=0; i<linkAreas.length; i++)
		{
			HashMap<Rectangle, String> linkMap = new HashMap<Rectangle, String>();
			for(int j=0; j<linkAreas[i].length; j++)
			{
				linkMap.put(linkAreas[i][j], linkNames[i][j]);
			}
			linkMaps[i] = linkMap;
		}
		
		//make ArrayList<Item> for item lists of scenes
		Rectangle[][] itemAreas = {{},{},{},{},{}, //images 1-5
									//image6
									{new Rectangle(193,428,195,69), //papers
										new Rectangle(777,517,195,70)}, //key
									{}, //image7
									{new Rectangle(636,371,69,43)}}; //image8
		String[][] itemFilenames = {{},{},{},{},{},//images 1-5				
									{"img/papers.png","img/key.png"},
									{}, //image7
									{"img/books.png"}};
		
		HashMap<Rectangle, Item>[] items = (HashMap<Rectangle, Item>[]) new HashMap[itemAreas.length];
		for(int i=0; i<itemAreas.length; i++)
		{
			HashMap<Rectangle, Item> map = new HashMap<Rectangle, Item>();
			for(int j=0; j<itemAreas[i].length; j++)
			{
				Image img;
				try {
					String filename = itemFilenames[i][j];
				    img = ImageIO.read(new File(filename));
				    Item item = new Item(img,filename.substring(4,filename.length()-4));
				    map.put(itemAreas[i][j], item);
				    allItems.add(item);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}		
			}
			items[i] = map;
		}
		
		//make HashMap<Rectangle, String> for the link lists of the scenes
		Rectangle[][] npcAreas = {{},{},{},{},{},{},
									{new Rectangle(747,388,57,44)}, //back
									{}}; //back
		Reception[][] npcFunctions = {{},{},{},{},{},{},
				{new Reception()
					{
						public void react(NPC r)
						{
							if(ivt.getActive()==null)
							{TreeHacks.showText("fr(The key)");}
							else
							{
								if(ivt.getActive().getName().equals("key"))
								{TreeHacks.showText("Animation happens here.");
								//here, scene changes would occur
								//and the scene would get a new link, to outside
								}
								else
								{TreeHacks.showText("fr(no, I need the key)");}
							}
						}
					}}, //back
				{}}; //back
		HashMap<Rectangle, Reception>[] NPCs = (HashMap<Rectangle, Reception>[]) new HashMap[npcAreas.length];
		for(int i=0; i<npcAreas.length; i++)
		{
			HashMap<Rectangle, Reception> map = new HashMap<Rectangle, Reception>();
			for(int j=0; j<npcAreas[i].length; j++)
			{
				map.put(npcAreas[i][j], npcFunctions[i][j]);
			}
			NPCs[i] = map;
		}
		
		for(int i=0; i<scenes.size(); i++)
		{
			scenes.get(i).addButtons(rootFrame, ivt, ht, linkMaps[i], items[i], 
					NPCs[i]);
		}

	}
}
