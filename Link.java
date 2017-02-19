package treehacks;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Link extends JButton {	//button within a scene that effects a scene change
	private String prompt;
	private String name;
	private boolean toPrompt = true;
	
	public Link(String name, HashMap<String, Scene> ht, JFrame root, Scene curr,
												Rectangle rect)
	{
		this.name = name;
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);

		setSize(rect.width, rect.height);
		setLocation(rect.x, rect.y);
		//gross hack to prevent the ActionListener from erroring
		final String fname = name;
		final JFrame froot = root;
		final Scene fcurr = curr;
		final HashMap<String, Scene> fht = ht;
		
		addActionListener(new ActionListener() { 			
			  public void actionPerformed(ActionEvent e) {
				//JLayeredPane layeredPane = (JLayeredPane) froot.getContentPane();
			    froot.getContentPane().remove(fcurr);
			    froot.getContentPane().add(fht.get(fname));
			    froot.setSize(TreeHacks.WIDTH,TreeHacks.HEIGHT);
			    froot.revalidate();
			    froot.repaint();
			  } 
		});
	}
	
	public Link(String name, HashMap<String, Scene> ht, JFrame root, Scene curr,
			Rectangle rect, String prompt, boolean once)
	{
		setOpaque(false);
		setContentAreaFilled(false);
		this.prompt = prompt;
		//setBorderPainted(false);

		setSize(rect.width, rect.height);
		setLocation(rect.x, rect.y);
		//gross hack to prevent the ActionListener from erroring
		final String fname = name;
		final JFrame froot = root;
		final Scene fcurr = curr;
		final HashMap<String, Scene> fht = ht;
		final String fprompt = prompt;
		final boolean fonce = once;
		
		addActionListener(new ActionListener() { 			
			  public void actionPerformed(ActionEvent e) {
				TreeHacks.hideText();
				TreeHacks.redraw();
				//JLayeredPane layeredPane = (JLayeredPane) froot.getContentPane();
			    froot.getContentPane().remove(fcurr);
			    froot.getContentPane().add(fht.get(fname));
			    froot.setSize(TreeHacks.WIDTH,TreeHacks.HEIGHT);
			    if(toPrompt)
			    {
			    	TreeHacks.showText(fprompt);
			    	System.out.println(fprompt);
			    }
			    if(fonce)
			    	toPrompt = false;
			    froot.revalidate();
			    froot.repaint();
			  } 
		});
	}
	
	public String getName(){return name;}
}
