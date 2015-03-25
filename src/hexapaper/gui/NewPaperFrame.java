package hexapaper.gui;

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


public class NewPaperFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HPSklad sk = HPSklad.getInstance();
	JFrame frame;

	public NewPaperFrame() {
		frame = new JFrame(sk.str.vytvorPaper);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		setLayout(new GridLayout(4, 1, 0, 10));
		setBorder(new TitledBorder(sk.str.vytvorPaper));
		init();
		frame.add(this);
		frame.setVisible(true);
	}

	JTextField polhexvalue;
	JTextField numRowValue;
	JTextField numColValue;

	protected void init() {
		JPanel prvni = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel polhex = new JLabel(sk.str.Radius);
		polhexvalue = new JNumberTextField();
		polhexvalue.setText(String.valueOf(sk.c.RADIUS));
		polhexvalue.addFocusListener(new Listener());
		prvni.add(polhex);
		prvni.add(polhexvalue);

		JPanel druhy = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel numRow = new JLabel(sk.str.LineCount);
		numRowValue = new JNumberTextField();
		numRowValue.setText(String.valueOf(sk.c.gridRa));
		numRowValue.addFocusListener(new Listener());
		druhy.add(numRow);
		druhy.add(numRowValue);

		JPanel treti = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel numCol = new JLabel(sk.str.CollumnCount);
		numColValue = new JNumberTextField();
		numColValue.setText(String.valueOf(sk.c.gridSl));
		numColValue.addFocusListener(new Listener());
		treti.add(numCol);
		treti.add(numColValue);

		JButton hotovo = new JButton(sk.str.vytvorPaper);
		hotovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				vytvorHexu();
				sk.odblokujListenery();
			}
		});

		add(prvni);
		add(druhy);
		add(treti);
		add(hotovo);
	}

	private void vytvorHexu() {
		sk.c.RADIUS = Integer.valueOf(polhexvalue.getText());
		sk.c.gridRa = Integer.valueOf(numRowValue.getText());
		sk.c.gridSl = Integer.valueOf(numColValue.getText());
		frame.dispose();
		sk.c.saveConfig();
		sk.hraciPlocha.init(sk.c.gridSl,sk.c.gridRa,sk.c.RADIUS);
		sk.hraciPlocha.revalidate();
		sk.hraciPlocha.repaint();
		if(sk.isConnected&&sk.isPJ){
			sk.client.radiusHexapaper();
			sk.client.updateHexapaper();	
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
