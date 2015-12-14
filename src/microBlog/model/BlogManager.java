package microBlog.model;

import java.util.HashMap;

import javax.jms.*;
import javax.swing.DefaultComboBoxModel;

import org.apache.activemq.ActiveMQConnectionFactory;

public class BlogManager {
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Topic destination;
	private String username;
	private IMessageHandler messageHandler;
	private HashMap<String, String> messages;
	private HashMap<String, MessageProducer> producers;
	private HashMap<String, MessageConsumer> consumers;
	private String activeTag;
	private DefaultComboBoxModel tagsModel;

	public BlogManager(IMessageHandler messageHandler, String username,DefaultComboBoxModel tagsModel) {
		this.tagsModel = tagsModel;
		this.messageHandler = messageHandler;
		this.username = username;
		producers = new HashMap<String, MessageProducer>();
		consumers = new HashMap<String, MessageConsumer>();

		this.messages = new HashMap<String, String>();
		try {
			this.initJMS(username);
		} catch (Exception e) {

		}
	}

	private void initJMS(String channelName) throws JMSException {
		connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		subscribe(channelName);		
	}

	public void sendMessage(String tag, String message) {
		try {
			TextMessage m = session.createTextMessage(message);
			((MessageProducer) producers.get(tag)).send(m);
		} catch (Exception e) {

		}
	}

	public void subscribe(String tag) {
		try {
			Topic destination = session.createTopic(tag);

			MessageConsumer consumer = session.createConsumer(destination);
			consumers.put(tag, consumer);
			consumer.setMessageListener(new MessageListener() {
				public void onMessage(javax.jms.Message receivedMsg) {
					TextMessage msg = (TextMessage) receivedMsg;
					try {
						String tag = msg.getJMSDestination().toString()
								.substring(8);
						messages.put(tag, msg.getText());
						messageHandler.onMessage(tag, msg.getText());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			MessageProducer producer = session.createProducer(destination);
			producers.put(tag, producer);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			tagsModel.addElement(tag);

		} catch (Exception e) {
			e.printStackTrace();
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