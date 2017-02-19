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
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TreeHacks {
	
	private static JFrame rootFrame;
	private static ArrayList<Scene> scenes;
	private static HashMap<String, Scene> ht;
	
	private final static int WIDTH=1366;
	private final static int HEIGHT=768;
	
	public static void main(String[] args)
	{
		rootFrame = new JFrame();
		initScenes();
		rootFrame.setSize(WIDTH,HEIGHT);
		rootFrame.add(scenes.get(0));
		
		rootFrame.setVisible(true);
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
								{new Rectangle(747,388,57,44), //shackle
								new Rectangle(624,30,106,70)}, //back
								//image8: bookshelfCU
								{new Rectangle(624,30,106,70)}}; //back
		
		String[][] linkNames = {{"image2","image4","image5","image5"},
								{"image3","image1","image6"},
								{"image4","image2","image7"},
								{"image1","image3","image8"},
								{"image5","image5","image1"},//door and keypad shouldn't work
								{"image2"},
								{"image7","image3"},
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
									{new Rectangle(193,428,195,69), //book
										new Rectangle(777,517,195,70), //key
										new Rectangle(345,327,225,53)}, //papers
									{}, //image7
									{new Rectangle(636,371,69,43)}}; //image8
		String[][] itemFilenames = {{},{},{},{},{},//images 1-5				
									{"img/books.png","img/key.png","img/papers.png"},
									{}, //image7
									{"img/books.png"}};
		
		ArrayList<Item> items = new ArrayList<Item>();
		for(int i=0; i<itemAreas.length; i++)
		{
			for(int j=0; j<itemAreas[i].length; j++)
			{
				Image img;
				try {
				    img = ImageIO.read(new File(itemFilenames[i][j]));
				    items.add(new Item(itemAreas[i][j],img));
				} 
				catch (IOException e) {
					e.printStackTrace();
				}		
			}
		}
		
		for(int i=0; i<scenes.size(); i++)
		{
			scenes.get(i).addButtons(rootFrame, ht, linkMaps[i], items);
		}
	}
}
