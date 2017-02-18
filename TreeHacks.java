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
