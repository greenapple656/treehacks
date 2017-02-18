package treehacks;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Link extends JButton {	
	public Link(String name, HashMap<String, Scene> ht, JFrame root, Scene curr, Rectangle rect)
	{
		setSize(rect.width, rect.height);
		setLocation(rect.x, rect.y);
		//gross hack to prevent the ActionListener from erroring
		final String fname = name;
		final JFrame froot = root;
		final Scene fcurr = curr;
		final HashMap<String, Scene> fht = ht;
		
		addActionListener(new ActionListener() { 			
			  public void actionPerformed(ActionEvent e) { 
			    froot.remove(fcurr);
			    froot.add(fht.get(fname));
			  } 
		});
	}
}
