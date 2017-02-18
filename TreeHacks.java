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
	
	
	public void run() {

		try {
   			File file_image1 = new File("1_Door.jpg");
			File file_image2 = new File("2_Desk.jpg");
			File file_image3 = new File("3_Man.jpg");
			File file_image4 = new File("4_Bookshelf.jpg");
   			Image im_image1 = ImageIO.read(file_image1);
			Image im_image2 = ImageIO.read(file_image2);
			Image im_image3 = ImageIO.read(file_image3);
			Image im_image4 = ImageIO.read(file_image4);
		} catch (IOException ex) {
 			   ex.printStackTrace();
		}
		Image1 = new Scene("image1", im_image1);
		Image2 = new Scene("image2", im_image2);
		Image3 = new Scene("image3", im_image3);
		Image4 = new Scene("image4", im_image4);
		
		setUp();
		addActionListener();		
		
	}

	
	private void setUp() {
		
		makeFrame();
		makeWelcomeLabel();
		private HashMap<Rectangle, String> cords1;
		private HashMap<Rectangle, String> cords2;
		private HashMap<Rectangle, String> cords3;
		private HashMap<Rectangle, String> cords4;
		cords1.put(new Rectangle(0,380,75,475), "Image4"); 
		cords1.put(new Rectangle(1275,380,1333,475), "Image2"); 
		
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


