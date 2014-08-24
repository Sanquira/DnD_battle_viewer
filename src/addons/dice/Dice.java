package addons.dice;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.Canvas;

public class Dice {

	private JFrame frmKostka;
	JTextField text;
	public JLabel label;
	public ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			text.setText(String.valueOf(button.getText()));
		}
	};

	// /**
	// * Launch the application.
	// */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// Dice window = new Dice();
	// window.frmKostka.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public Dice() {
		initialize();
		frmKostka.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmKostka = new JFrame();
		frmKostka.setTitle("Kostka");
		// frmKostka.setBounds(100, 100, 450, 300);
		frmKostka.setSize(450, 300);
		frmKostka.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmKostka.getContentPane().setLayout(null);

		label = new JLabel("0");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 50));
		label.setForeground(Color.BLACK);
		label.setBackground(Color.LIGHT_GRAY);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(28, 11, 285, 208);
		frmKostka.getContentPane().add(label);

		JButton btnRoll = new JButton("Roll");
		btnRoll.setBounds(28, 230, 89, 23);
		btnRoll.addActionListener(new ActionListener() {
			;
			public void actionPerformed(ActionEvent ae)
			{
				String str = text.getText();
				final int min = 1;
				final Random rand = new Random();
				final int max = Integer.valueOf(str);
				label.setText(String.valueOf(rand.nextInt((max - min) + 1) + min));
			}
		});
		frmKostka.getContentPane().add(btnRoll);

		text = new JNumberTextField();
		text.setText("6");
		text.setBounds(227, 231, 86, 20);
		frmKostka.getContentPane().add(text);
		text.setColumns(10);

		Canvas canvas = new Canvas();
		canvas.setBackground(Color.BLACK);
		canvas.setBounds(320, 0, 1, 261);
		frmKostka.getContentPane().add(canvas);

		JButton six = new JButton("6");
		six.setBounds(320, 0, 114, 31);
		six.addActionListener(listener);
		frmKostka.getContentPane().add(six);

		JButton ten = new JButton("10");
		ten.addActionListener(listener);
		ten.setBounds(319, 32, 115, 30);
		frmKostka.getContentPane().add(ten);

		JButton hundred = new JButton("100");
		hundred.addActionListener(listener);
		hundred.setBounds(319, 63, 115, 30);
		frmKostka.getContentPane().add(hundred);
	}
}
