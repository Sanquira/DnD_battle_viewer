package hexapaper.gui.frames;

import hexapaper.network.server.HexaServer;
import hexapaper.source.HPSklad;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import core.JNumberTextField;


public class ServerCreateFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HPSklad sk = HPSklad.getInstance();
	HexaServer server;
	boolean GUI=true;
	JFrame frame;

	public ServerCreateFrame(HexaServer s) {
		server=s;
		frame = new JFrame(sk.str.ConnectFrame);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		setLayout(new GridLayout(3, 1, 0, 10));
		setBorder(new TitledBorder(sk.str.ServerCreateFrame));
		init();
		frame.getContentPane().add(this);
		frame.setVisible(true);
	}
	
	public ServerCreateFrame(boolean GUI,HexaServer s){
		this(s);
		this.GUI=GUI;
	}
	JTextField ipfield;
	JNumberTextField portfield;

	protected void init() {
		JPanel first = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel polhex = new JLabel(sk.str.ipField);
		ipfield = new JTextField(sk.c.serverIP);
		ipfield.addFocusListener(new Listener());
		first.add(polhex);
		first.add(ipfield);

		JPanel second = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel numRow = new JLabel(sk.str.portField);
		portfield = new JNumberTextField();
		portfield.setInt(sk.c.serverport);
		portfield.addFocusListener(new Listener());
		second.add(numRow);
		second.add(portfield);

		JButton hotovo = new JButton(sk.str.ServerCreate);
		hotovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				connect();
			}
		});

		add(first);
		add(second);
		add(hotovo);
	}

	private void connect() {
		sk.c.serverIP = ipfield.getText();
		sk.c.serverport = portfield.getInt();
		sk.c.saveConfig();
		server.start();
		frame.setVisible(false);
	}

	private class Listener extends FocusAdapter {
		@Override
		public void focusGained(FocusEvent e) {
			((JTextField) e.getComponent()).setSelectionStart(0);
			((JTextField) e.getComponent()).setSelectionEnd(((JTextField) e.getComponent()).getText().length());
		}
	}

}
