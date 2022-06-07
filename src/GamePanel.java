import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener{

	//game
	private int widthPanel;
	private int heightPanel;
	private JLabel scorePanel;
	private boolean pause;
	private boolean pressed;
	private int keyCode;
	private Timer timer;
	private final int DELAY = 100;
	private int score;

	//paddle
	private Paddle paddle;
	private int widthPaddle;
	private int heightPaddle;
	private int paddleSpeed;
	private int paddleX;
	private int paddleY;

	//ball
	private Ball ball;
	private int ballRadius;
	private int ballDiameter;
	private int ballSpeed;
	private int ballX, ballY;
	private int diffX, diffY;
	private boolean stickToPaddle;

	//brick
	private ArrayList<Brick> bricks;
	private int heightbrick;
	private int widthBrick;
	private int brickX, brickY;

	//GamePanel
	public GamePanel(JLabel sl)
	{
		this.setBackground(Color.WHITE);
		scorePanel = sl;
		score = 0;

		addKeyListener(this);
		addMouseListener(this);
		timer = new Timer(DELAY, this);
		setFocusable(true);
	}


	public void startGame(){

		//gamepanel
		widthPanel = this.getWidth();
		heightPanel = this.getHeight();
		System.out.println("Width: " + widthPanel);
		System.out.println("Height: " + heightPanel);
		pause = false;
		pressed = false;

		//paddle
		paddleX = 0;
		paddleY = heightPanel - 50;
		widthPaddle = 100;
		heightPaddle = 20;
		paddle = new Paddle(paddleX, paddleY, widthPaddle, heightPaddle);
		paddleSpeed = 10;

		//ball
		ballSpeed = 10;
		ballDiameter = 20;
		ballRadius = ballDiameter / 2;
		ballX = paddleX + widthPaddle / 2 - ballRadius;
		ballY = paddleY - ballDiameter;
		ball = new Ball(ballX, ballY, ballDiameter);
		stickToPaddle = true;
		diffX = 0;
		diffY = 0;

		//bricks
		brickX = 0;
		brickY = 40;
		widthBrick = 40;
		heightbrick = 20;
		bricks = new ArrayList<Brick>();
		int bricksCount = widthPanel / widthBrick;
		int rows = 4;

		//create bricks
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < bricksCount; j++)
			{
				bricks.add(new Brick(brickX, brickY, widthBrick, heightbrick));
				brickX += (widthBrick + 1);
			}
			brickX = 0;
			brickY += (heightbrick + 1);
		}

		repaint();
		timer.start();
	}

	public void paintComponent(Graphics page)
	{
		super.paintComponent(page);

		paddle.paint(page);
		ball.paint(page);

		for (int i = 0; i < bricks.size(); i++)
			bricks.get(i).paint(page);

	}


	@Override
	public void keyPressed(KeyEvent e) {
		pressed = true;
		keyCode = e.getKeyCode();

		//pause
		if (keyCode == KeyEvent.VK_P)
		{
			if (pause){
				pause = false;
				timer.start();
			}
			else{
				pause = true;
				timer.stop();
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		pressed = false;
	}
	@Override
	public void keyTyped(KeyEvent e) {}


	//  Mouse events
	@Override
	public void mouseClicked(MouseEvent event) {

		Point mouseClick = event.getPoint();
		int clickX = (int)mouseClick.getX();
		int clickY = (int)mouseClick.getY();

		int xP = clickX - ballX;
		int yP = ballY - clickY;
		diffX = (int) (ballSpeed * (xP/Math.sqrt(Math.pow(xP, 2) + Math.pow(yP, 2))));
		diffY = (int) (ballSpeed * (yP/Math.sqrt(Math.pow(xP, 2) + Math.pow(yP, 2))));

		ball.setVelocity(diffX, diffY);


		stickToPaddle = false;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}


	// Timer events
	@Override
	public void actionPerformed (ActionEvent event) {

		if (pressed){
			switch(keyCode)
			{
				case KeyEvent.VK_D:
				case KeyEvent.VK_RIGHT:
					if (paddleX < (widthPanel - widthPaddle))
					{
						paddleX += paddleSpeed;
						paddle.setPos(paddleX, paddleY);
					}
					break;

				case KeyEvent.VK_A:
				case KeyEvent.VK_LEFT:
					if (paddleX > 0)
					{
						paddleX -= paddleSpeed;
						paddle.setPos(paddleX, paddleY);
					}
					break;
				default:
			}
		}

		if (ball.x >= (widthPanel - ballDiameter) ||
				ball.x <= 0){
			ball.diffX *= -1;
		}

		if (ball.y <= 0){
			ball.diffY *= -1;
		}

		else if (ball.y > (heightPanel - ballDiameter)){
			ball.diffY *= -1;
			timer.stop();
			JOptionPane.showMessageDialog(null, "Lose!");
		}

		if (!stickToPaddle){
			ball.move();
		}
		else{
			ballX = paddleX + widthPaddle / 2 - ballRadius;
			ball.setPos(ballX, ballY);
		}

		Point overlapP = ball.overlapPoint(paddle);
		if (overlapP != null && !stickToPaddle){
			System.out.println("Overlap with paddle at " + overlapP.x + ", " + overlapP.y);
			ball.bounce(paddle, overlapP);
		}

		for (int i = 0; i < bricks.size(); i++)
		{
			Point contactPoint = ball.overlapPoint(bricks.get(i));
			if (contactPoint != null){
				System.out.println("Overlap with brick at " + contactPoint.x + ", " + contactPoint.y);
				ball.bounce(bricks.get(i), contactPoint);
				bricks.remove(i);
				score++;
				scorePanel.setText("Score: " + score);
				break;
			}
		}

		repaint();

		if (bricks.size() == 0){
			timer.stop();
			JOptionPane.showMessageDialog(null, "WIN!");
		}

	}
}
