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

public class TreeHacks extends JFrame {
	
	private Scene Image1;	
	private Scene Image2;		
	private Scene Image3;
	private Scene Image4;	
	private Scene Image5;
	private Scene Image6;	
	private Scene Image7;	
	private Scene Image8;	
	
	public void run() {
		
		private HashMap<Rectangle, integer> cords1;
		private HashMap<Rectangle, integer> cords2;
		private HashMap<Rectangle, integer> cords3;
		private HashMap<Rectangle, integer> cords4;
		private HashMap<Rectangle, integer> cords5;
		private HashMap<Rectangle, integer> cords6;
		private HashMap<Rectangle, integer> cords7;
		private HashMap<Rectangle, integer> cords8;
		
		//1_Door image: 
		//arrows
		cords1.put(new Rectangle(0,380,75,475), 1); 
		cords1.put(new Rectangle(1275,380,1333,475), 1); 
		//Door 
		cords1.put(new Rectangle(755,286,1016,592), 1); 
		//Key Pad
		cords1.put(new Rectangle(1036,351,1101,413), 1); 
		
		//2_ Desk:
		//arrows
		cords2.put(new Rectangle(0,380,75,475), 2); 
		cords2.put(new Rectangle(1275,380,1333,475), 2); 
		//desk
		cords2.put(new Rectangle(254,423,804,617), 2); 
		
		//3_Man:
		//arrows
		cords3.put(new Rectangle(0,380,75,475), 3); 
		cords3.put(new Rectangle(1275,380,1333,475), 3); 
		//man
		cords3.put(new Rectangle(458,400,655,685), 3);
		
		//4_Bookshelf
		//arrows
		cords4.put(new Rectangle(0,380,75,475), 4); 
		cords4.put(new Rectangle(1275,380,1333,475), 4); 
		//bookshelf
		cords4.put(new Rectangle(536,277,778,605), 4); 
		
		//5_Door CU
		//Key pad
		cords5.put(new Rectangle(951,311,1056,412), 5); 
		//Door
		cords5.put(new Rectangle(418,125,906,700), 5);
		
		//6_Desk CU
		//Key pad
		cords6.put(new Rectangle(951,311,1056,412), 6); 
		//Drawers, top left
		cords6.put(new Rectangle(193,428,388,497), 6);
		//Drawers, middle right
		cords6.put(new Rectangle(777,517,972,587), 6);
		//papers
		cords6.put(new Rectangle(345,327,570,380), 6);
		
		//7_Man CU
		//Key pad
		cords7.put(new Rectangle(951,311,1056,412), 7); 
		//shackle
		cords7.put(new Rectangle(747,388,804,432), 7);
		
		//8_Bookshelf CU
		//Key pad
		cords8.put(new Rectangle(951,311,1056,412), 8); 
		//bookshelf
		cords8.put(new Rectangle(636,371,705,414), 8);
		
		
		
		
		try {
		
		Map<integer, Scene> ht = new HashMap<integer, Scene>();
		
		Image1 = new Scene(1,"1_Door.jpg");
		ht.put(1, Image1);
		Image2 = new Scene(2,"2_Desk.jpg");
		ht.put(2, Image2);
		Image3 = new Scene(3,"3_Man.jpg");
		ht.put(3, Image3);
		Image4 = new Scene(4,"4_Bookshelf.jpg");
		ht.put(4, Image4);
		Image4 = new Scene(5,"5_DoorCU.jpg");
		ht.put(5, Image5);		
		Image4 = new Scene(6,"6_DeskCU.jpg");
		ht.put(6, Image6);
		Image4 = new Scene(7,"7_ManCU.jpg");
		ht.put(7, Image7);
		Image4 = new Scene(8,"8_BookshelfCU.jpg");
		ht.put(8, Image8);		   
				   
		addActionListener();		
		setUp();
			
		} catch (IOException ex) {
 			   ex.printStackTrace();
		}	
	}

	
	private void setUp() {
		
		makeFrame();
		makeWelcomeLabel();
		
	}	
		
	private void makeFrame() {
		JFrame ourFrame = new JFrame("image.file");
		add(Image1);		
	}	
			
	private void makeButton () {
		
		panel.setLayout null; 
		Button b = new Button("");		
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setBorderPainter(false);
		panel.add(b);
	
	}
	
	 private void makeWelcomeLabel() {
		JLabel welcome = new JLabel("Good morning, your task in this game will be to figure out where you are! How? Consider exploring around this room...");
		welcome.setFont("Calibri-bold-30");
		welcome.setColor(Color.CYAN); 
		ourFrame.add(welcome, ourFrame.getWidth()/2, ourFrame.getHeight()/2);		
		
	}
	
	//The  to mouse being clicked
	public void actionPerformed (ActionEvent e) {
		
	}
}


