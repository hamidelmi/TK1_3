package microBlog.view;

import java.awt.*;

import javax.swing.*;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
	private LoginView loginDialog;
	private JPanel topPanel, bottomPanel;
	private JTextArea allMessages;
	private JTextField messageText, channelText;
	private JList tagsList;
	private JComboBox channelsComboBox;
	private DefaultComboBoxModel tagsModel;
	private microBlog.controller.MainController controller;

	public MainView(microBlog.controller.MainController controller,
			DefaultComboBoxModel tagsModel) {
		this.setTitle("Micro blog!");
		this.setBackground(Color.BLACK);
		this.setSize(640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.controller = controller;
		this.tagsModel = tagsModel;
	}

	/**
	 * Shows a login dialog (modal) and asks for a name
	 * 
	 * @return player name
	 */
	public String showLoginDialog(String title) {
		Window win = SwingUtilities.getWindowAncestor(this);
		loginDialog = new LoginView(win, title, ModalityType.APPLICATION_MODAL);
		loginDialog.setVisible(true);

		return loginDialog.getUsername();
	}

	public void showMainView(String username) {
		setTitle(username);

		topPanel = new JPanel(new GridLayout(1, 2));
		topPanel.setBackground(Color.WHITE);
		allMessages = new JTextArea();
		topPanel.add(allMessages);

		tagsList = new JList(this.tagsModel);
		JScrollPane tagPane = new JScrollPane(tagsList);

		topPanel.add(tagPane);

		bottomPanel = new JPanel(new GridLayout(2, 2));
		bottomPanel.setBackground(Color.YELLOW);
		messageText = new JTextField();
		channelText = new JTextField();
		JButton sendButton = new JButton("Send");
		JButton subscribeButton = new JButton("Subscribe");
		channelsComboBox = new JComboBox(this.tagsModel);

		JPanel sendPanel = new JPanel(new GridLayout(1, 2));
		sendPanel.add(messageText);
		sendPanel.add(channelsComboBox);

		bottomPanel.add(sendPanel);
		bottomPanel.add(sendButton);
		bottomPanel.add(channelText);
		bottomPanel.add(subscribeButton);

		JPanel p = new JPanel(new GridLayout(2, 1));
		p.setBackground(Color.BLUE);
		p.add(topPanel);
		p.add(bottomPanel);

		add(p);

		this.setVisible(true);

		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.sendMessage(messageText.getText());
			}
		});
		subscribeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//
			}
		});
	}

	public void setMessage(String message) {
		allMessages.append(message + System.lineSeparator());

	}

}
