import java.io.*;
import javax.swing.*;
import java.awt.*;

public class render extends JPanel
{
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		flappybird.fb.repaint(g);
	}
	
	
}