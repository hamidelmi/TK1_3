package microBlog.controller;

import javax.swing.SwingUtilities;

import microBlog.view.*;

public class MainController {
	private MainView mainView;
	private boolean guiInitiated = false;
	private String username;

	public MainController(String arg) {
		mainView = new MainView();
		String title = "Login";
		while (!guiInitiated) {
			username = mainView.showLoginDialog(title);
			System.out
					.println("Try to connet '" + username + "' to the server");
			try {
//				initConnection(ipAddress);
//
//				System.out.println("Connected to the server\r\nReady to play");
//
//				gameView.startGame(username);
//				gameView.setVisible(true);
//				gameView.updateScores(playersScore);
//				gameView.showFly(x, y);
				guiInitiated = true;
			} catch (Exception ex) {
				title = ex.getCause().getMessage();
				System.out.println("Connection failed");
				ex.printStackTrace();
			}
		}
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				new MainController(args.length > 0 ? args[0] : "");
			}
		});
	}
}
