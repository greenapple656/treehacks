package treehacks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Inventory extends JPanel implements MouseListener
{
	public final static int MIN_X = 0;
	public final static int MIN_Y = 690;
	private final static int SIZE = 6;
	private static Rectangle[] RECTANGLES = new Rectangle[SIZE];
	
	private Item[] items = new Item[SIZE];
	private int lowestAvailable = 0;
	private JButton[] buttons = new JButton[SIZE];
	private JLabel[] glowLabels = new JLabel[SIZE];	
	
	private Image image;
	
	public Inventory()
	{
		for(int i=0; i<SIZE; i++)
		{
			RECTANGLES[i] = new Rectangle(332+172*i, 28, 140,140);
		}
		setSize(1366, 200);
		addMouseListener(this);
		setLayout(null);
		
		for(int i=0; i<RECTANGLES.length; i++)
		{
			Image glow;
			try {
				image = ImageIO.read(Inventory.class.getResource("/inventory.jpg"));
				
				glow = ImageIO.read(Inventory.class.getResource("/Glow.png"));
				glowLabels[i]= new JLabel(new ImageIcon(glow));
				//labels[i].setSize(RECTANGLES[i].width, RECTANGLES[i].height);
				glowLabels[i].setSize(154,154);
				glowLabels[i].setLocation(RECTANGLES[i].x, RECTANGLES[i].y-3);
				glowLabels[i].setVisible(false);
				add(glowLabels[i]);
			} 
			catch (IOException e) {e.printStackTrace();}			
			
			buttons[i]= new JButton();
			buttons[i].setSize(RECTANGLES[i].width, RECTANGLES[i].height);
			buttons[i].setLocation(RECTANGLES[i].x, RECTANGLES[i].y);
			buttons[i].setVisible(false);
			
			final int fi = i;
			buttons[i].addActionListener(new ActionListener() { 			
				  public void actionPerformed(ActionEvent e) {
					if(items[fi]!=null)
					{
						items[fi].setActive(!items[fi].isActive());
						for(int j=0; j<items.length; j++)
						{
							if(items[j]!=null&&j!=fi)
								items[j].setActive(false);
						}
					}
					refreshActive();
				  } 
			});
			buttons[i].setOpaque(false);
			buttons[i].setContentAreaFilled(false);
			buttons[i].setBorderPainted(false);
			add(buttons[i]);
		}
		
		refreshActive();
	}
	
	public void addItem(Item item)
	{
		items[lowestAvailable] = item;
		refreshLowest();	
	}
	//badly done manual implementation of a LinkedList :[
	public Item removeItem(int i)
	{
		Item returnme = items[i];
		items[i] = null;
		refreshLowest();
		refreshActive();
		return returnme;
	}
	
	public void refreshLowest()
	{
		for(int i=0; i<items.length; i++)
		{
			if(items[i]==null)
			{
				lowestAvailable = i;
				break;
			}
		}
		refreshActive();
	}
	
	public void refreshActive()
	{
		for(int i=0; i<items.length; i++)
		{
			if(items[i]!=null)
			{
				buttons[i].setIcon(new ImageIcon(items[i].getImage()));
				System.out.println(items[i].isActive());
				buttons[i].setVisible(true);
				glowLabels[i].setVisible(false);
				if(items[i].isActive()) {glowLabels[i].setVisible(true);}
			}
			else
			{
				buttons[i].setVisible(false);
				glowLabels[i].setVisible(false);
			}
		}
		repaint();
	}
	
	public Item[] getItems()
	{
		return items;
	}
	
	public Item getActive()
	{
		for(int i=0; i<items.length; i++)
		{
			if(items[i]!=null&&items[i].isActive())
				return items[i];
		}
		return null;
	}
	
	public void hide()
	{
		setLocation(MIN_X,MIN_Y);
	}
	public void show()
	{
		setSize(1368,200);
		setLocation(0,540);
		for(int i=0; i<RECTANGLES.length; i++)
		{
			buttons[i].setSize(RECTANGLES[i].width, RECTANGLES[i].height);
			buttons[i].setLocation(RECTANGLES[i].x, RECTANGLES[i].y);
		}
	}
	
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

	public void mouseEntered(MouseEvent arg0) {show();}
	public void mouseExited(MouseEvent arg0) {if(arg0.getY()<0){hide();}}
	//unnecessary methods
	public void mouseClicked(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
