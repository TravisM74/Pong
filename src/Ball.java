import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle {
	
	Random random;
	int xVelocity;
	int yVelocity;
	int initialSpeed = 2 ;
	int hit;
	boolean hitnow = false;
	Image image = new ImageIcon("Untitled.png").getImage();
	
	
	
	Ball(int x,int y,int width, int height, int hits){
		super(x,y,width,height);
		random = new Random();
		int randomXDirection = random.nextInt(2);
		if (randomXDirection == 0) {
			randomXDirection--;
		}
		setXDirection(randomXDirection*initialSpeed);
		int randomYDirection = random.nextInt(2);
		if (randomYDirection == 0) {
			randomYDirection--;
		}
		setYDirection(randomYDirection*initialSpeed);
	}
	public void hit() {
		hit = hit+1 ;
		System.out.println(" ball hit :"+hit);
		hitnow=true;
		//Ball.drawhit(g);

	}
	
	public void setXDirection(int randomXDirection) {
		xVelocity = randomXDirection;
		
		
	}
	public void setYDirection(int randomYDirection) {
		yVelocity = randomYDirection;
		
	}
	public void move() {
		x += xVelocity;
		y += yVelocity;
		
		
	}
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval(x, y, height, width);
		
	}
	public void drawhit(Graphics g) {
		g.setColor(Color.YELLOW);
		g.setFont(new Font("Consolas",Font.BOLD,20));
		g.drawString("Wack",x,y);
		g.drawImage(image,x,y, null);
		hitnow=false;
	}
	                            
	
}
