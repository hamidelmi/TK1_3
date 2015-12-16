package microBlog.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.Dialog.ModalityType;
import java.awt.event.*;


public class MainView extends JFrame {
	private LoginView loginDialog;
	private JPanel topPanel, bottomPanel;
	private JTextArea allMessages;
	private JTextField messageText, tagText, tagsText;
	private JList tagsList;	
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
		tagsList.addListSelectionListener(
			new ListSelectionListener() {
			      public void valueChanged(ListSelectionEvent listSelectionEvent) {
			    	  if (!listSelectionEvent.getValueIsAdjusting()){
			    		  int selections[] = tagsList.getSelectedIndices();
			    		  if (selections.length > 0)
			    			  setMessage(controller.getMessages(selections[0]), false);
			    	  }
			      }
			});
		JScrollPane tagPane = new JScrollPane(tagsList);

		topPanel.add(tagPane);

		bottomPanel = new JPanel(new GridLayout(2, 2));
		bottomPanel.setBackground(Color.YELLOW);
		messageText = new JTextField();
		tagText = new JTextField();
		
		JButton subscribeButton = new JButton("Subscribe/Create");
		

		JPanel sendPanel = new JPanel(new GridLayout(1, 2));
		JPanel sendOptionPanel = new JPanel(new GridLayout(1, 1));
		
		tagsText = new JTextField();
		
		sendOptionPanel.add(tagsText);
		
		sendPanel.add(sendOptionPanel);
		JButton sendButton = new JButton("Send");
		sendPanel.add(sendButton);

		bottomPanel.add(messageText);
		bottomPanel.add(sendPanel);
		bottomPanel.add(tagText);
		bottomPanel.add(subscribeButton);

		JPanel p = new JPanel(new GridLayout(2, 1));
		p.setBackground(Color.BLUE);
		p.add(topPanel);
		p.add(bottomPanel);

		add(p);

		this.setVisible(true);

		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.sendMessage(tagsText.getText(), messageText.getText());
			}
		});
		subscribeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.subsribe(tagText.getText());
			}
		});
		
		tagsText.setText("tag1;tag2;tag3");
		messageText.setText("Messsage to be sent");
		tagText.setText("new tag/user name");
	}
	

	public void setMessage(String message) {
		setMessage(message, true);
	}
	
	private void setMessage(String message, boolean appened ) {
		if (appened)
			allMessages.append(message + System.lineSeparator());
		else
			allMessages.setText(message);
	}
}
