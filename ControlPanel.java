import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class ControlPanel extends JPanel implements ActionListener {
	
	static final int screenWidth = 600;
	static final int screenHeight = 600;
	static final int unitSize = 25;
	static final int gameUnits = (screenWidth * screenHeight)/unitSize;
	static final int delay = 75;
	final int x[] = new int[gameUnits];
	final int y[] = new int[gameUnits];
	int body = 6;
	int applesConsumed = 0;
	int appleX;
	int appleY;
	char directions = 'R';
	boolean run = false;
	Timer timer;
	Random random;
	
	ControlPanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
	}
	public void startGame() {
		newApples();
		run = true;
		timer = new Timer(delay,this);
		timer.start();
	}
	public void paint(Graphics g) {
		super.paint(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(run) {
			g.setColor(Color.green);
			g.fillOval(appleX, appleY, unitSize, unitSize);
			
			for(int i = 0; i < body; i++) {
				if(i == 0) {
					g.setColor(Color.blue);
					g.fillRect(x[i], y[i], unitSize, unitSize);
				}else {
					g.setColor(new Color(45, 180,0));
					g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], unitSize, unitSize);
				}
			}
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.ITALIC, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Current Score:" + applesConsumed, (screenWidth - metrics.stringWidth("Current Score:" + applesConsumed))/2,g.getFont().getSize());
		}else {
			gameOver(g);
		}
		
	}
	public void newApples() {
	appleX = random.nextInt((int)(screenWidth/unitSize)) * unitSize;
	appleY = random.nextInt((int)(screenHeight/unitSize)) * unitSize;
	}
	public void movement() {
		for(int i = body; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		switch(directions) {
		case 'U':
			y[0] = y[0] - unitSize;
			break;
		case 'D':
			y[0] = y[0] + unitSize;
			break;
		case 'L':
			x[0] = x[0] - unitSize;
			break;
		case 'R':
			x[0] = x[0] + unitSize;
			break;
		}
	}
	public void appleChecker() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			body++;
			applesConsumed++;
			newApples();
		}
	}
	public void collissionChecker() {
		// If head collides with it's body
		for(int i = body; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				run = false;
			}
		}
		//If head collides with left border
		if(x[0] < 0) {
			run = false;
		}
		//If head collides with right border
		if(x[0] > screenWidth) {
			run = false;
		}
		//If head collides with the top border
		if(y[0] < 0) {
			run = false;
		}
		//If head touches bottom border
		if(y[0] > screenHeight) {
			run = false;
		}
		if(!run) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//current score text
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.ITALIC, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Current Score:" + applesConsumed, (screenWidth - metrics1.stringWidth("Current Score:" + applesConsumed))/2,g.getFont().getSize());
		//Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 70));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screenWidth - metrics2.stringWidth("Game Over"))/2, screenHeight/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	if(run) {
		movement();
		appleChecker();
		collissionChecker();
	}
		repaint();
	}
public class myKeyAdapter extends KeyAdapter{
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if(directions != 'R') {
				directions = 'L';
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(directions != 'L') {
				directions = 'R';
			}
			break;
		case KeyEvent.VK_UP:
			if(directions != 'D') {
				directions = 'U';
			}
			break;
		case KeyEvent.VK_DOWN:
			if(directions != 'U') {
				directions = 'D';
			}
			break;
		}
	}
}
}