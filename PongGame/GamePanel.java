import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
	
	static final int GAME_WIDTH = 1250;
	static final int GAME_HEIGHT = 750;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDEL_WIDTH = 25;
	static final int PADDEL_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddel paddel1;
	Paddel paddel2;
	Ball ball;
	Score score;
	
	GamePanel(){
		newStartButton();
		newPaddels();
		newBall();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	public void newStartButton() {
	    JButton startButton = new JButton("Start");
	}
	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
		
	}
	public void newPaddels() {
		paddel1 = new Paddel(0,(GAME_HEIGHT/2)-(PADDEL_HEIGHT/2),PADDEL_WIDTH,PADDEL_HEIGHT,1);
		paddel2 = new Paddel(GAME_WIDTH-PADDEL_WIDTH,(GAME_HEIGHT/2)-(PADDEL_HEIGHT/2),PADDEL_WIDTH,PADDEL_HEIGHT,2);
	}
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {
		paddel1.draw(g);
		paddel2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	public void move() {
		paddel1.move();
		paddel2.move();
		ball.move();
	}
	public void checkCollision() {
		//bounce ball off top & window edges 
		if(ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		//bounce ball off paddels
		if(ball.intersects(paddel1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.yVelocity>0)
				ball.yVelocity++; 
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(paddel2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.yVelocity<0)
				ball.yVelocity++;
			else
				ball.yVelocity++;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		
		//stop paddels at window edges
		if(paddel1.y <= 0)
			paddel1.y = 0;
		if(paddel1.y >= (GAME_HEIGHT-PADDEL_HEIGHT))
			paddel1.y = GAME_HEIGHT-PADDEL_HEIGHT;
		if(paddel2.y <= 0)
			paddel2.y = 0;
		if(paddel2.y >= (GAME_HEIGHT-PADDEL_HEIGHT))
			paddel2.y = GAME_HEIGHT-PADDEL_HEIGHT;
		
		//give player 1 point, new ball new paddel
		if(ball.x <= 0) {
			score.player2++;
			newPaddels();
			newBall();
			System.out.println("Player2:"+score.player2);
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newPaddels();
			newBall();
			System.out.println("Player1:"+score.player1);
		}
	}
	public void run() {
		//game Loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddel1.keyPressed(e);
			paddel2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddel1.keyReleased(e);
			paddel2.keyReleased(e);
		}
	}
	
}