import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle {
	
	Random random;
	int yVelocity;
	int xVelocity;
	int initialSpeed = 6;
	int maxSpeed = 15;
	
	Ball(int x, int y, int width, int height){
		super(x,y,width,height);
		random = new Random();
		int randomXDirection = random.nextInt(2);
		if(randomXDirection == 0)
			randomXDirection--;
		setXDirection(randomXDirection*initialSpeed);
		
		int randomYDirection = random.nextInt(2);
		if(randomYDirection == 0)
			randomYDirection--;
		setXDirection(randomYDirection*initialSpeed);
	}
	public void setXDirection(int randomXdirection) {
		xVelocity = randomXdirection;
	}
	public void setYDirection(int randomYdirection) {
		yVelocity = randomYdirection;
	}
	public void move() {
		x += xVelocity;
		y += yVelocity;
	}
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, height, width);
	}
}
