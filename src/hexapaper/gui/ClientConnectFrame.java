package hexapaper.gui;

import hexapaper.network.server.HexaClient;
import hexapaper.source.HPSklad;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import core.JNumberTextField;


public class ClientConnectFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HPSklad sk = HPSklad.getInstance();
	JFrame frame;

	public ClientConnectFrame() {
		frame = new JFrame(sk.str.get("ConnectFrame"));
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		setLayout(new GridLayout(4, 1, 0, 10));
		setBorder(new TitledBorder(sk.str.get("ConnectFrame")));
		init();
		frame.getContentPane().add(this);
		frame.setVisible(true);
	}

	JTextField ipfield;
	JTextField portfield;
	JTextField namefield;

	protected void init() {
		JPanel first = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel polhex = new JLabel(sk.str.get("ipField"));
		ipfield = new JTextField("212.96.186.28");
		ipfield.addFocusListener(new Listener());
		first.add(polhex);
		first.add(ipfield);

		JPanel second = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel numRow = new JLabel(sk.str.get("portField"));
		portfield = new JNumberTextField();
		portfield.setText("10555");
		portfield.addFocusListener(new Listener());
		second.add(numRow);
		second.add(portfield);

		JPanel third = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel numCol = new JLabel(sk.str.get("nameField"));
		namefield = new JTextField(sk.lastName);
		namefield.addFocusListener(new Listener());
		third.add(numCol);
		third.add(namefield);

		JButton hotovo = new JButton(sk.str.get("Connect"));
		hotovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				connect();
				sk.odblokujListenery();
			}
		});

		add(first);
		add(second);
		add(third);
		add(hotovo);
	}

	private void connect() {
		if(sk.isConnected){
			try {
				sk.client.close();
				sk.client=null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String ip = ipfield.getText();
		Integer port = Integer.valueOf(portfield.getText());
		String nick = namefield.getText();
		
		sk.lastName = nick;
		HexaClient c=new HexaClient();
		try {
			c.connect(ip, port, nick);
			frame.dispose();
			sk.updateConnect();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame,
				    sk.str.get("ConnectError")+e.getMessage(),
				    sk.str.get("IOError"),
				    JOptionPane.ERROR_MESSAGE);		
		}
	}

	private class Listener extends FocusAdapter {
		@Override
		public void focusGained(FocusEvent e) {
			((JTextField) e.getComponent()).setSelectionStart(0);
			((JTextField) e.getComponent()).setSelectionEnd(((JTextField) e.getComponent()).getText().length());
		}
	}

}
