package addons.dice;

import hexapaper.source.HPSklad;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.JNumberTextField;

public class Dice {

	// /////////////////////////////////////////////////////////////////////////////
	// Začátek negrafickych metod
	// /////////////////////////////////////////////////////////////////////////////
	
	private JFrame frmKostka;
	private JTextField text;
	private JLabel label;
	private HPSklad sk=HPSklad.getInstance();

	private ActionListener fastValueButtonListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			text.setText(String.valueOf(button.getText()));
			
		}
	};

	private ActionListener rollListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = text.getText();
			final int min = 1;
			final Random rand = new Random();
			final int max = Integer.valueOf(str);
			Integer number=rand.nextInt((max - min) + 1) + min;
			label.setText(String.valueOf(number));
			if(sk.isConnected&&!sk.isPJ){
				Integer[] i={number,max};
				try {
					sk.client.send(i, "dice");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
		}
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dice window = new Dice();
					window.frmKostka.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Dice() {
		frmKostka = new JFrame();
		frmKostka.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initialize();
		frmKostka.setVisible(true);
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Konec negrafickych metod
	// /////////////////////////////////////////////////////////////////////////////

	/**
	 * TOHLE budes upravovat
	 */
	private void initialize() {
		// nastaveni titulku okna, velikosti a layoutu
		frmKostka.setTitle("Kostka");
		frmKostka.setSize(450, 300);
		frmKostka.setLayout(null);

		// tohle zobrazuje vysledek
		label = new JLabel("0");
		label.setFont(new Font("Times New Roman", Font.PLAIN, 50));
		label.setForeground(Color.BLACK);
		label.setBackground(Color.LIGHT_GRAY);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(28, 11, 285, 208);
		frmKostka.add(label);

		// tohle hazi kostkou
		JButton btnRoll = new JButton("Roll");
		btnRoll.setBounds(28, 230, 89, 23);
		btnRoll.addActionListener(rollListener);
		frmKostka.add(btnRoll);

		// tohle udava rozsah kostky
		text = new JNumberTextField();
		text.setText("6");
		text.setBounds(227, 231, 86, 20);
		text.setColumns(3);
		frmKostka.add(text);
		

		// rychle nastaveni rozsahu na 6
		JButton six = new JButton("6");
		six.setBounds(320, 0, 114, 31);
		//six.addActionListener(rollListener);
		six.addActionListener(fastValueButtonListener);
		
		frmKostka.add(six);

		// rychle nastaveni rozsahu na 10
		JButton ten = new JButton("10");
		ten.addActionListener(fastValueButtonListener);
		ten.setBounds(319, 32, 115, 30);
		frmKostka.add(ten);

		// rychle nastaveni rozsahu na 100
		JButton hundred = new JButton("100");
		hundred.addActionListener(fastValueButtonListener);
		hundred.setBounds(319, 63, 115, 30);
		frmKostka.add(hundred);
	}
}
