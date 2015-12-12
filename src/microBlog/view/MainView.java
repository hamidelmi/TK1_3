package microBlog.view;

import java.awt.*;
import javax.swing.*;
import java.awt.Dialog.ModalityType;


public class MainView  extends JFrame {
	private LoginView loginDialog;
	
	public MainView() {
		this.setTitle("Micro blog!");
		this.setBackground(Color.BLACK);
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}
	
	/**
	 * Shows a login dialog (modal) and asks for a name
	 * 
	 * @return player name
	 */
	public String showLoginDialog(String title) {
		Window win = SwingUtilities.getWindowAncestor(this);
		loginDialog = new LoginView(win, title,
				ModalityType.APPLICATION_MODAL);
		loginDialog.setVisible(true);

		return loginDialog.getUsername();
	}
}
