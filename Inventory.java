package treehacks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Inventory extends JPanel implements MouseListener
{
	public final static int MIN_X = 0;
	public final static int MIN_Y = 670;
	
	private Item[] items = new Item[8];
	private int numItems = 0;
	private boolean hidden = true;
	
	public Inventory()
	{
		setSize(1366, 200);
		addMouseListener(this);
		Rectangle[] boxCoordinates = {new Rectangle(8,50,140,140)};, //Image of key
								new Rectangle(180,50,140,140),//Image of paper
								new Rectangle (352,50,140,140), // Image of books
		
	}
	
	public void hide()
	{
		setLocation(MIN_X,MIN_Y);
	}
	public void show()
	{
		setSize(1368,200);
		setLocation(0,568);
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    Image img;
	    try {
		    img = ImageIO.read(new File("img/inventory.jpg"));
		    g.drawImage(img, 0, 0, null);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	    
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("Clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		System.out.println("entered");
		show();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		System.out.println("exited");
		hide();
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
