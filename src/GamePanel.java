import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
	
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT =(int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle1a;
	Paddle paddle1b;
	Paddle paddle1c;
	Paddle paddle2;
	Ball ball;
	Score score;
	Random raondom;
	
	
	
	GamePanel() {
		newPaddles();
		newBall();
		score = new Score(GAME_WIDTH,GAME_HEIGHT);
		this.setFocusable(true); //this will register the keystrokes
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void newBall() {
		//Random ball start point
		random = new Random();
		ball= new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER)-(BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER,0);
		
		//ball= new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),(GAME_HEIGHT/2)-(BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
		
	}
	
	public void newPaddles() {
		/*
		paddle1a = new Paddle(0,((GAME_HEIGHT/2)-(PADDLE_HEIGHT/3))-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT/3,1);
		paddle1b = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT/3,1);
		paddle1c = new Paddle(0,((GAME_HEIGHT/2)+(PADDLE_HEIGHT/3))-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT/3,1);
		*/
		paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);   //top left corner and this panel to be drawn.
		
	}
	
	public void draw(Graphics g) {
		//paddle1.draw(g);
		/*
		paddle1a.draw(g);
		paddle1b.draw(g);
		paddle1c.draw(g);
		*/
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
		if(ball.hitnow) {
			ball.drawhit(g);
		}
	}

	public void move() {
		/*
		paddle1a.move();
		paddle1b.move();
		paddle1c.move();
		*/
		paddle1.move();
		paddle2.move();
		ball.move();
		
	}
	public void CheckCollision(){
		//Stopss paddles at edges
		if(paddle1.y<=0) {
			paddle1.y=0;
		}
		if(paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT)) {
			paddle1.y=(GAME_HEIGHT-PADDLE_HEIGHT);
		}
		if(paddle2.y<=0) {
			paddle2.y=0;
		}
		if(paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT)) {
			paddle2.y=(GAME_HEIGHT-PADDLE_HEIGHT);
		}	
		
		// Bounce ball off top and bottom
		if(ball.y <= 0 ) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= (GAME_HEIGHT-BALL_DIAMETER)) {
			ball.setYDirection(-ball.yVelocity);
		}
		
		//ball off paddles
		if(ball.intersects(paddle1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);     // Same as * -1  Inverts the value
			ball.xVelocity++;  		 //Speed up ball after each hit....
			ball.hit();
			if(ball.yVelocity < 0) {
				ball.yVelocity++;	//Speed up ball after each hit....
			}else {
				ball.yVelocity--;
			}
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);     // Same as * -1  Inverts the value
			ball.xVelocity++;  		 //Speed up ball after each hit....
			ball.hit();
			if(ball.yVelocity < 0) {
				ball.yVelocity = ball.yVelocity + random.nextInt(5); //Speed up ball after each hit....
				
			}else {
				ball.yVelocity = ball.yVelocity*random.nextInt(5) * -1;
				
			}
			
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		//point Score and resets game with new paddles and ball.
		
		if(ball.x <= 0 ) {
			score.player2++;
			newPaddles();
			newBall();
			System.out.println(score.player1 + " <------> "+score.player2);
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER ) {
			score.player1++;
			newPaddles();
			newBall();
			System.out.println(score.player1 + " <------> "+score.player2);
		}
		
	}
	
	public void run() {
		// game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while (true) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
				if(delta >= 1) {
					move();
					CheckCollision();
					repaint();
					delta--;
					
					
					
					
				}
			
		}
		
		
	}
	
	public class AL extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			paddle1.keyPressed(e);
			paddle2.keyPressed(e);
			
		}
		public void keyReleased(KeyEvent e) {
			paddle1.keyReleased(e);
			paddle2.keyReleased(e);
		}
	}
}
