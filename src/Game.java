import javax.swing.*;

public class Game implements Runnable {
	
	JFrame frame;
	GameComp gameComp;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}

	@Override
	public void run() {
		frame = new JFrame("BRICK BREAKER");
		gameComp = new GameComp();
		frame.getContentPane().add(gameComp);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setUndecorated(true);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		gameComp.requestFocusInWindow();
	}
}
