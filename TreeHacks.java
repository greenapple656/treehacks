package treehacks;

/*
 * File: TreeHacks.java
 * -------------------
 * 
 */ 

import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class TreeHacks {
	
	private static JFrame rootFrame;
	private static JTextArea textArea;
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
		textArea = new JTextArea();
		ivt = new Inventory();
		allItems = new ArrayList<Item>();
		
		rootFrame.getContentPane().add(textArea);
		textArea.setSize(1250,30);
		textArea.setFont(new Font(textArea.getFont().getName(), Font.PLAIN, 18));
		textArea.setBackground(new Color(255,214,214));
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createCompoundBorder(
		        textArea.getBorder(), 
		        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		textArea.setOpaque(true);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setVisible(false);
		textArea.setLocation(40,30);
		
		rootFrame.getContentPane().add(ivt);
		ivt.setSize(1366,200);
		ivt.setLocation(Inventory.MIN_X,Inventory.MIN_Y);
		
		/*
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
		*/
		
		textArea.addMouseListener(new labelClickListener());
		
		initScenes();
		rootFrame.setSize(WIDTH,HEIGHT);
		
		scenes.get(0).setSize(WIDTH,HEIGHT);
		scenes.get(0).setLocation(0,0);	
		rootFrame.getContentPane().add(scenes.get(0));
		
		rootFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		rootFrame.setVisible(true);	    
	    rootFrame.repaint();
	    
	    showText("You wake up in a strange room without any memory of how you got there." +
	    		" In fact, you have no memory of anything at all. You don’t even remember who " +
	    		"you are. All you know is that you’re in this room. And you should probably " +
	    		"figure a way out…" + "\n\n" + "Click on objects to explore your environment, click on text boxes to close them");
	}
	
	public static void showText(String text)
	{
		textArea.setText(text);
		if(text.length()<40)
			textArea.setSize(text.length()*18,50);
		else
			textArea.setSize(500,(int)Math.ceil(text.length()/33.0*20)+18*text.split("\n").length);
		textArea.setVisible(true);
		rootFrame.repaint();
	}
	public static void hideText()
	{
		textArea.setVisible(false);
	}
	
	public static void redraw()
	{
		rootFrame.revalidate();
		rootFrame.repaint();
	}
	
	private static void initScenes()
	{
		scenes = new ArrayList<Scene>();
		ht = new HashMap<String, Scene>();
		String[] filenames = {"img/1_Door.jpg","img/2_Desk.jpg","img/3_Man.jpg","img/4_Bookshelf.jpg",
				"img/5_Door CU.jpg","img/6_Desk CU.jpg","img/7_Man CU.jpg","img/8_Bookshelf CU.jpg"};
		
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
		
		Rectangle left = new Rectangle(22,324,69,69);
		Rectangle right = new Rectangle(1271,324,69,69);
		Rectangle back = new Rectangle(650,21,69,69);
		 
		//make HashMap<Rectangle, String> for the link lists of the scenes
		Rectangle[][] linkAreas = {{left, right, //image1
								new Rectangle(757,289,258,430), //door
								new Rectangle(1038,410,47,51)}, //keypad
								//image2: desk
								{left, right,
								new Rectangle(342,567,452,173)}, //desk
								//image3: man
								{left, right,
								new Rectangle(600,419,184,342)}, //man
								//image4: bookshelf
								{left, right,
								new Rectangle(658,477,212,249),}, //bookshelf
								//image5: doorCU
								{new Rectangle(1166,292,68,74), //keypad
								new Rectangle(757,116,375,626), //door
								back},
								//image6: deskCU
								{back},
								//image7: manCU
								{back},
								//image8: bookshelfCU
								{back}};
		
		String[][] linkNames = {{"image2","image4","image5","image5"},
								{"image3","image1","image6"},
								{"image4","image2","image7"},
								{"image1","image3","image8"},
								{"image5","image5","image1"},//door and keypad shouldn't work
								{"image2"},
								{"image3"},
								{"image4"}};
		String[][] linkPrompts = {{"","","",""},//left, right, door, keypad
								{"You're shocked to see a man chained to the wall! He clearly needs help.\n\nHe can barely speak, but utters one word…","",""},//left, right, desk
								{"","","To give the man an item in your inventory, click the item, then click the man."},//left, right, man
								{"","",""},//left, right, bookshelf
								{"Nothing happens","The door won’t open",""},//keypad, door, back
								{""},{""},{""}};//back
		boolean[][] linkBools = {{false,false,false,false},//left, right, door, keypad
								{true,false,false},//left, right, desk
								{false,false,true},//left, right, man
								{false,true,false},//left, right, bookshelf
								{false,false,false},//keypad, door, back
								{false},{false},{false}};//back
		
		ArrayList<Link[]> links = new ArrayList<Link[]>(8);
		for(int i=0; i<linkAreas.length; i++)
		{
			Link[] linkList = new Link[linkAreas[i].length];
			for(int j=0; j<linkAreas[i].length; j++)
			{
				if(linkPrompts[i][j].equals("")){
					linkList[j] = new Link(linkNames[i][j], ht, rootFrame, 
							scenes.get(i), linkAreas[i][j]);
				}
				else
				{
					linkList[j] = new Link(linkNames[i][j], ht, rootFrame, scenes.get(i), 
							linkAreas[i][j], linkPrompts[i][j],linkBools[i][j]);
				}
			}
			links.add(linkList);
		}
		
		//make ArrayList<Item> for item lists of scenes
		Rectangle[][] itemAreas = {{},{},{},{},{}, //images 1-5
									//image6
									{new Rectangle(197,586,221,55), //papers
										new Rectangle(860,586,221,55)}, //key
									{}, //image7
									{new Rectangle(358,411,47,64)}}; //image8
		String[][] itemFilenames = {{},{},{},{},{},//images 1-5				
									{"img/Papers.png","img/Key.png"},
									{}, //image7
									{"img/Book.png"}};
		
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
				    Item item = new Item(img,filename.substring(4,filename.length()-4).toLowerCase());
				    map.put(itemAreas[i][j], item);
				    allItems.add(item);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}		
			}
			items[i] = map;
		}
		
		HashMap<String, String> sceneDict = new HashMap<String, String>();
		sceneDict.put("book", "un livre");
		sceneDict.put("papers", "des papiers");
		final HashMap<String, String> fsceneDict = sceneDict;
		
		final Reception finalReception = new Reception(){
			public void react(NPC receptor) {
				Image img;
				try {
				    img = ImageIO.read(new File("img/9_DoorCode.jpg"));
				    Scene lastScene = new Scene("image9", img);
				    lastScene.addButtons(rootFrame, ivt, ht, new Link[0],
				    		new HashMap<Rectangle,Item>(), new HashMap<Rectangle,Reception>());
				    showText("You help the man to the door, and he types in the code.\n\n" +
				    		"The door swings open, and you stare in disbelief when you realize where you are…");
				    rootFrame.remove(scenes.get(4));
				    rootFrame.add(lastScene);
				    redraw();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}		
		}};
		
		Rectangle[][] npcAreas = {{},{},{},{},{},{},
									{new Rectangle(631,162,398,607)}, //back
									{}}; //back
		Reception[][] npcFunctions = {{},{},{},{},{},{},
				{new Reception()
					{
						public void react(NPC r)
						{
							if(ivt.getActive()==null)
							{TreeHacks.showText("“La clé…la clé!”");}
							else
							{
								String currItem = ivt.getActive().getName();
								if(currItem.equals("key"))
								{
									rootFrame.remove(scenes.get(6));
									rootFrame.add(scenes.get(2));
									redraw();
									showText("The man cries out, “Oui, la clé ! La clé!”\n\nYou unlock the shackles and help him to his feet.\n“La porte! La porte!” he says.");
									scenes.get(2).deleteLink("image7");
									
									scenes.get(4).printLinks();
									scenes.get(4).deleteLink("image5");
									scenes.get(4).deleteLink("image5");
									
									NPC npc = new NPC(finalReception, ivt, new ArrayList<Item>());
									scenes.get(4).addNPC(new Rectangle(757,116,375,626), npc);
									redraw();
								}
								else
								{TreeHacks.showText("“Non! pas " + fsceneDict.get(currItem) + 
										". La clé ! La clé !”");}
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
			scenes.get(i).addButtons(rootFrame, ivt, ht, links.get(i), items[i], 
					NPCs[i]);
		}

	}
	static class labelClickListener implements MouseListener
	{
		public void mouseClicked(MouseEvent arg0) {hideText();}		
		//unnecessary methods
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}
	}
}