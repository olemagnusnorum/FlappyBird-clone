package flappy;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Flappy implements ActionListener, MouseListener{
	
	public final int WHITH = 600, HEIGHT = 600;

	public static Flappy flappybird;
	
	public Renderer renderer;
	
	public Rectangle bird;
	
	public ArrayList<Rectangle> columns;
	
	public Random rand;
	
	public int ticks,yMotion,score;
	
	public boolean gameOver, started;
	
	public Flappy() {
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20,this);
		renderer = new Renderer();
		rand = new Random();
		
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(WHITH, HEIGHT);
		jframe.setResizable(false);
		jframe.addMouseListener(this);
		jframe.setTitle("Flappybird");
		jframe.setVisible(true);
		
		bird = new Rectangle(WHITH/2-10,HEIGHT/2-10,20,20);
		columns = new ArrayList<Rectangle>();
		
		addColumn(true);
		addColumn(true);
		addColumn(true);
		
		timer.start();
	}
	
	public void addColumn(boolean start) {
		int space = 240;
		int with = 100;
		int height = 50 + rand.nextInt(300);
		if(start) {
			columns.add(new Rectangle(WHITH + with+columns.size()*200, HEIGHT - height-120, with, height));
			columns.add(new Rectangle(WHITH + with+(columns.size()-1)*200, 0, with,HEIGHT - height-space));
		}else {
			columns.add(new Rectangle(columns.get(columns.size()-1).x+500, HEIGHT-height-120,with,height));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0,with,HEIGHT-height-space));
		}
	}
	
	public void jump() {
		if(gameOver) {
			bird = new Rectangle(WHITH/2-10,HEIGHT/2-10,20,20);
			columns.clear();
			yMotion = 0;
			score = 0;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			
			gameOver = false;
		}
		if(!started) {
			started = true;
		}else if (!gameOver){
			if(yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= 10;
			
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int speed = 5;
		ticks++;
		
		if (started) {
		
			for(int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}
			
			if(ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2;
				
			}
			
			for(int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				if(column.x + column.width < 0) {
					columns.remove(column);
					if(column.y == 0) {
						addColumn(false);
					}
				}
				
			}
			
			bird.y += yMotion;
			
			for(Rectangle column: columns) {
				if(column.intersects(bird)) {
					gameOver = true;
					
					bird.x = column.x-bird.width;
				}
			}
			
			if(bird.y > HEIGHT-120 || bird.y < 0) {
				gameOver = true;
			}
			if(gameOver) {
				bird.y = HEIGHT-120-bird.height;
			}
		}
			renderer.repaint();
		
	}
	
	public void repaint(Graphics g) {
	
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WHITH, HEIGHT);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-120, WHITH, 150);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-120, WHITH, 20);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		for(Rectangle column: columns) {
			paintColumn(g, column);
		}
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",1,50));
		
		if(!started) {
			g.drawString("Click To Start", 140, HEIGHT/2);
		}
		
		if(gameOver) {
			g.drawString("Game Over", 140, HEIGHT/2);
		}
	}



	public void paintColumn(Graphics g, Rectangle column) {
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		
		flappybird = new Flappy();
		
	}


	
}

