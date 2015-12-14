package microBlog.controller;

import microBlog.view.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.jms.*;

import org.apache.activemq.*;

public class MainController {
	private MainView mainView;
	private boolean guiInitiated = false;
	private String username;
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Topic destination;
	private MessageProducer producer;
	private MessageConsumer consumer;
	private DefaultComboBoxModel tagsModel;

	public MainController(String arg) {
		tagsModel = new DefaultComboBoxModel();
		mainView = new MainView(this, tagsModel);

		String title = "Login";
		while (!guiInitiated) {
			username = mainView.showLoginDialog(title);

			try {
				initJMS(username);
				mainView.showMainView(username);
				guiInitiated = true;
			} catch (JMSException e) {
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

	private void initJMS(String channelName) throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		destination = session.createTopic(channelName);

		producer = session.createProducer(destination);
		consumer = session.createConsumer(destination);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);

		consumer.setMessageListener(new MessageListener() {
			public void onMessage(javax.jms.Message receivedMsg) {
				TextMessage msg = (TextMessage) receivedMsg;
				try {
					// tagsModel.addElement();
					mainView.setMessage(msg.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		tagsModel.addElement(channelName);
	}

	public void sendMessage(String message) {
		try {
			TextMessage m = session.createTextMessage(message);
			producer.send(m);
		} catch (JMSException e) {

		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if (session != null)
			session.close();
		if (connectionFactory != null)
			connection.close();
	}
}
