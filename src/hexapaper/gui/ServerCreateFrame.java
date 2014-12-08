package hexapaper.gui;

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
import javax.swing.JOptionPane;
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
	JFrame frame;

	public ServerCreateFrame() {
		frame = new JFrame(sk.str.get("ConnectFrame"));
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		setLayout(new GridLayout(3, 1, 0, 10));
		setBorder(new TitledBorder(sk.str.get("ServerCreateFrame")));
		init();
		frame.getContentPane().add(this);
		frame.setVisible(true);
	}

	JTextField ipfield;
	JTextField portfield;

	protected void init() {
		JPanel first = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel polhex = new JLabel(sk.str.get("ipField"));
		ipfield = new JTextField("192.168.0.10x");
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

		JButton hotovo = new JButton(sk.str.get("ServerCreate"));
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
		String ip = ipfield.getText();
		String port = portfield.getText();
		String[] o ={ip,port};
		Exception fail=HexaServer.consoleStart(o);
		if(fail!=null){
			JOptionPane.showMessageDialog(frame,
				    sk.str.get("ServerError")+fail.getMessage(),
				    sk.str.get("ServerIOError"),
				    JOptionPane.ERROR_MESSAGE);
			System.exit(ERROR);
		}
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
