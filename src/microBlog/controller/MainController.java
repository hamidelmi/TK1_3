package microBlog.controller;

import java.util.List;

import microBlog.view.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import org.apache.activemq.*;
import microBlog.model.*;

public class MainController implements IMessageHandler {
	private MainView mainView;
	private boolean guiInitiated = false;
	private String username;
	private BlogManager blogManager;
	private DefaultComboBoxModel tagsModel;

	public MainController(String arg) {
		tagsModel = new DefaultComboBoxModel();
		mainView = new MainView(this, tagsModel);

		String title = "Login";
		while (!guiInitiated) {
			username = mainView.showLoginDialog(title);

			try {
				blogManager = new BlogManager(this, username, tagsModel);
				mainView.showMainView(username);
				guiInitiated = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("JMS error:");
				e.printStackTrace();
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

	public void onMessage(String tag, String message) {
		mainView.setMessage(tag + ": " + message);
	}

	public void sendMessage(String tags, String message) {
		blogManager.sendMessage(username, message);
		String[] tagsArray = tags.split(";", -1);
		for (String tag : tagsArray)
			if (!(tag == ";" || tag.isEmpty()))
				blogManager.sendMessage(tag, message);
	}

	public void subsribe(String tag) {
		blogManager.subscribe(tag);
	}
	
	public String getMessages(int index) {
		List<String> msgs = blogManager.getMessages(tagsModel.getElementAt(index).toString());
		String result = "";
		
		for (String m: msgs)
			result += m + System.lineSeparator();
		return result;
	}
}
