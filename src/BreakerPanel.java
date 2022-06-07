import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BreakerPanel extends JPanel{
	
	private GamePanel gamePanel;
	
	public BreakerPanel(){
		JLabel scoreLabel = new JLabel("Score Board: 0");
		gamePanel = new GamePanel(scoreLabel);
		
		this.setLayout(new BorderLayout());
		this.add(scoreLabel, BorderLayout.NORTH);
		this.add(gamePanel, BorderLayout.CENTER);
	}
	
	public GamePanel getGamePanel(){
		return gamePanel;
	}
}
