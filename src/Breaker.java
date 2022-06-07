import javax.swing.JFrame;

public class Breaker {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Brick Breaker");
		int width = 410;
		int height = 600;
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		BreakerPanel BreakerPanels = new BreakerPanel();
		frame.add(BreakerPanels);
		frame.setVisible(true);
		BreakerPanels.getGamePanel().startGame();
	}
}
