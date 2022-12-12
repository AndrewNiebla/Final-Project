import javax.swing.JFrame;

public class SnakeGUI extends JFrame {
	SnakeGUI(){
		
		this.add(new ControlPanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}