import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

public class Paddle extends GameObject{
	
	public Paddle(int x, int y, int w, int h){
		super(x, y, w, h);
	}

	public void paint(Graphics page)
	{
		//ImageIcon icon = new ImageIcon("images/paddle.png");
		//page.add(new JPanel(icon));
		Color red = Color.decode("#252424");
		page.setColor(red);
		page.fillRect(x, y, width, height);
	}
}