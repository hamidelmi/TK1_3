package microBlog.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * @author hamidelmi
 *
 *         Show a simple login dialog to ask users his/her name
 */
@SuppressWarnings("serial")
public class LoginView extends JDialog {

	private final JLabel jlblUsername = new JLabel("Username");

	private final JTextField jtfUsername = new JTextField(15);

	private final JButton jbtOk = new JButton("Login");

	private final JLabel jlblStatus = new JLabel(" ");

	/**
	 * Default Constructor
	 */
	public LoginView() {
		this(null, true);
	}

	/**
	 * Constructor
	 * 
	 * @param parent
	 * @param modal
	 */
	public LoginView(final JFrame parent, boolean modal) {
		super(parent, modal);

		init();
	}

	/**
	 * Constructor
	 * 
	 * @param owner
	 * @param title
	 * @param modalityType
	 */
	public LoginView(Window owner, String title,
			Dialog.ModalityType modalityType) {
		super(owner, title, modalityType);

		init();
	}

	private void init() {
		JPanel p3 = new JPanel(new GridLayout(1, 1));
		p3.add(jlblUsername);

		JPanel p4 = new JPanel(new GridLayout(1, 1));
		p4.add(jtfUsername);

		JPanel p1 = new JPanel();
		p1.add(p3);
		p1.add(p4);

		JPanel p2 = new JPanel();
		p2.add(jbtOk);

		JPanel p5 = new JPanel(new BorderLayout());
		p5.add(p2, BorderLayout.CENTER);
		p5.add(jlblStatus, BorderLayout.NORTH);
		jlblStatus.setForeground(Color.RED);
		jlblStatus.setHorizontalAlignment(SwingConstants.CENTER);

		setLayout(new BorderLayout());
		add(p1, BorderLayout.CENTER);
		add(p5, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		final LoginView self = this;
		jbtOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				self.dispose();
			}
		});
	}

	/**
	 * @return username that user entered in the text field
	 */
	public String getUsername() {
		return jtfUsername.getText();
	}
}
