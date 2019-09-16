import java.io.*; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class flappybird implements ActionListener, MouseListener
{
	public static flappybird fb;
	public static render r = new render();
	public static JFrame jframe = new JFrame();
	
	
	public static Rectangle b = new Rectangle(500 - 10, 500 - 10, 20, 20);
	public static ArrayList<Rectangle> c = new ArrayList<Rectangle>();
	
	public Random rand = new Random();
	public Timer t = new Timer(50, this);
	
	public int ticks;
	public boolean gamestart;
	public boolean gameover;
	public int ymotion;
	public int score;
	
	public flappybird()
	{
		
		jframe.setTitle("Java FlappyBird");
		jframe.setVisible(true);
		jframe.setSize(1000,1000);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		
		jframe.addMouseListener(this); 
		jframe.add(r);
		
		addColumn(true);
		addColumn(true);
		
		t.start();
	}
	
	//-------------------------------------------------------------------------------------------------
	
	public void actionPerformed(ActionEvent e) 
	{
		
		
		int speed = 15;
		ticks++;
		
		if(gamestart)
		{
			//moving across the screen to see the painted pillars
			for(int i = 0; i < c.size(); i++)
			{
				Rectangle column = c.get(i);
				column.x -= speed;
			}
			
			//the bird natural movement by gravity
			if(ticks % 2 == 0 && ymotion < 20) 
			{
				ymotion += 5;
			}
			
			//iterates through the c array list and removes column 
			for(int i = 0; i < c.size(); i++)
			{
				Rectangle column = c.get(i);
				if(column.x + column.width < 0) 
				{
					c.remove(column);
					
					//if top column add another column making it infinite adding and removing off the screen
					if(column.y == 0)
					{
						addColumn(false);
					}
				}
			}
					
				
			b.y += ymotion;
			
			//collision detection if bird hits pipe and floor then game over
			for(Rectangle column: c)
			{
				//makes sure the birds position goes pass the y axis of the pillars to get a score
				if(column.y == 0 && b.x + b.width / 2 > column.x + column.width / 2 - 10 && b.x + b.width < column.x + column.width/ 2 + 10)
				{
					score++;
				}
				
				if(column.intersects(b))
				{
					gameover = true;
					
					//the pipe stops the bird from penetrating to the next pipe !!!!!doesnt work!!!!!!!
					b.x = column.x - b.width;
				}
			}
			
			//if bird hits ground then gameover
			if(b.y > 1000 -150 || b.y < 0)
			{
				gameover = true;
			}
			
			//makes the bird die on the floor and not exit screen
			if(b.y + ymotion >= 1000-150)
			{
				b.y = 1000 -150 -b.height;
				gameover = true;
			}
	}
		
		r.repaint();
			
	}
	
	//-------------------------------------------------------------------------------------------------
	
	public void addColumn(boolean s)
	{
		int space= 300;
		int width = 100;
		int height = 50 + rand.nextInt(300);
		
		//if starting columns proceed
		if(s) {
			//adding top half of the pillar -150 because above grass
			c.add(new Rectangle(1000 + width + c.size() * 300, 1000 - height - 150, width, height));
			//adding bottom half of the pillar 
			c.add(new Rectangle(1000 + width + (c.size() - 1) * 300, 0, width,1000 - height - space));
		}
		else
		{
			//appendent to the last column we had top half of pillar taking previous x value
			c.add(new Rectangle(c.get(c.size() - 1).x + 600, 1000 - height - 150, width, height));
			//appendent to the last column we had bottom half of pillar taking previous x value
			c.add(new Rectangle(c.get(c.size() - 1).x, 0, width, 1000 - height - space));
		}
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void paintColumn(Graphics g, Rectangle column)
	{
		//setcolour for the pillars
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
		
	}
	
	//--------------------------------------------------------------------------------------------------
	
	public void repaint(Graphics g)
	{
		//setting paint for the background
		g.setColor(Color.cyan);
		g.fillRect(0, 0, 1000, 1000);
		
		//Setting paint for the ground
		g.setColor(Color.orange);
		g.fillRect(0, 1000-150, 1000, 150);
		
		//Setting paint for the grass
		g.setColor(Color.green);
		g.fillRect(0, 1000-150, 1000, 20);
		
		//setting paint for the bird
		g.setColor(Color.yellow);
		g.fillRect(b.x, b.y, b.width, b.height);
	
		//painting the column
		for(Rectangle column: c)
		{
			paintColumn(g, column);
		}
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		
		//paint click to start
		if(!gamestart) 
		{
			
			g.drawString("Kanflappybird!", 150, 400 );
		}
		
		//paint the string gameover
		if(gameover) 
		{
			
			g.drawString("Game Over!", 300, 400 );
		}
		
		//draw score
		if(!gameover && gamestart)
		{
			g.drawString(String.valueOf(score), 10,100);
		}
	}
	
	//---------------------------------------------------------------------------------------------------
	
	public void jump()
	{
	
		if(gameover)
		{
			b = new Rectangle(500 - 10, 500 - 10, 20, 20);
			c.clear();
			ymotion = 0;
			score = 0;
			
			addColumn(true);
			addColumn(true);
			
			gameover = false;
		}
		
		if(!gamestart)
		{
			gamestart = true;
		}
		else if(!gameover)
		{
			if(ymotion > 0)
			{
				ymotion = 0;
			}
			ymotion -= 15;
			
		}
		
		
		
	}
	
	//---------------------------------------------------------------------------------------------------
	
	public void mouseClicked(MouseEvent e)
	{
		jump();
	}
	public void mousePressed(MouseEvent e)
	{
		
	}
	public void mouseReleased(MouseEvent e)
	{
		
	}
	public void mouseEntered(MouseEvent e)
	{
		
	}
	public void mouseExited(MouseEvent e)
	{
		
	}
	
	//---------------------------------------------------------------------------------------------------
	
	public static void main(String[] args)
	{
		fb = new flappybird();
	}
}