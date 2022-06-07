import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Brick extends GameObject{
	
	private int red, green, blue, result;
	
	public Brick(int x, int y, int w, int h){
		super(x, y, w, h);


		Random r = new Random();
		int low = 0;
		int high = 255;
		result = r.nextInt(high-low) + low;
		if(result < 124){
			result = 0;
		}
		else {
			result = 255;
		}
	}

	public void paint(Graphics page)
	{
		//ImageIcon icon = new ImageIcon("images/brick.png");
		//page.add(new JPanel(icon));
		page.setColor(new Color(255, result, 0));
		page.fillRect(x, y, width, height);
	}
}