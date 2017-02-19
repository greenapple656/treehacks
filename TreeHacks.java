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
		Rectangle[][] linkAreas = {{new Rectangle(0,380,75,475), //image1 - left
								new Rectangle(1275,380,1333,475), //right
								new Rectangle(755,286,1016,592), //door
								new Rectangle(1036,351,1101,413)}, //keypad
								//image2: desk
								{new Rectangle(0,380,75,475), //left
								new Rectangle(1275,380,1333,475), //right
								new Rectangle(254,423,804,617)}, //desk
								//image3: man
								{new Rectangle(0,380,75,475), //left
								new Rectangle(1275,380,1333,475), //right
								new Rectangle(458,400,655,685)}, //man
								//image4: bookshelf
								{new Rectangle(0,380,75,475), //left
								new Rectangle(1275,380,1333,475), //right
								new Rectangle(536,277,778,605),}, //bookshelf
								//image5: doorCU
								{new Rectangle(951,311,1056,412), //keypad
								new Rectangle(418,125,906,700), //door
								new Rectangle(624,30,730,100)}, //back
								//image6: deskCU
								{new Rectangle(624,30,730,100)}, //back
								//image7: manCU
								{new Rectangle(747,388,804,432), //shackle
								new Rectangle(624,30,730,100)}, //back
								//image8: bookshelfCU
								{new Rectangle(624,30,730,100)}}; //back
		
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
									{new Rectangle(193,428,388,497), //book
										new Rectangle(777,517,972,587), //key
										new Rectangle(345,327,570,380)}, //papers
									{}, //image7
									{new Rectangle(636,371,705,414)}}; //image8
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
