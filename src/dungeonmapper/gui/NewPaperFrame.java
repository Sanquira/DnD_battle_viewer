package dungeonmapper.gui;

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
import dungeonmapper.source.DMSklad;


public class NewPaperFrame extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DMSklad sk = DMSklad.getInstance();
	JFrame frame;

	public NewPaperFrame() {
		frame = new JFrame(sk.str.CreatePaperFrame);
		frame.setSize(300, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		setLayout(new GridLayout(4, 1, 0, 10));
		setBorder(new TitledBorder(sk.str.CreatePaperFrame));
		init();
		frame.add(this);
		frame.setVisible(true);
	}

	JTextField polhexvalue;
	JTextField numRowValue;
	JTextField numColValue;

	protected void init() {
		JPanel prvni = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel polhex = new JLabel(sk.str.Size);
		polhexvalue = new JNumberTextField();
		polhexvalue.setText("20");
		polhexvalue.addFocusListener(new Listener());
		prvni.add(polhex);
		prvni.add(polhexvalue);

		JPanel druhy = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel numRow = new JLabel(sk.str.Rows);
		numRowValue = new JNumberTextField();
		numRowValue.setText("300");
		numRowValue.addFocusListener(new Listener());
		druhy.add(numRow);
		druhy.add(numRowValue);

		JPanel treti = new JPanel(new GridLayout(1, 2, 10, 0));
		JLabel numCol = new JLabel(sk.str.Columns);
		numColValue = new JNumberTextField();
		numColValue.setText("300");
		numColValue.addFocusListener(new Listener());
		treti.add(numCol);
		treti.add(numColValue);

		JButton hotovo = new JButton(sk.str.CreatePaperButton);
		hotovo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent paramActionEvent) {
				vytvorHexu();
				
			}
		});

		add(prvni);
		add(druhy);
		add(treti);
		add(hotovo);
	}

	private void vytvorHexu() {
		sk.CSIZE = Integer.valueOf(polhexvalue.getText());
		sk.ROWS = Integer.valueOf(numRowValue.getText());
		sk.COLS = Integer.valueOf(numColValue.getText());
		frame.dispose();
		sk.drawPlane.init();
	}

	private class Listener extends FocusAdapter {
		@Override
		public void focusGained(FocusEvent e) {
			((JTextField) e.getComponent()).setSelectionStart(0);
			((JTextField) e.getComponent()).setSelectionEnd(((JTextField) e.getComponent()).getText().length());
		}
	}

}
