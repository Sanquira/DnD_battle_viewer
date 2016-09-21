package addons.miny;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class minyNewGame {
	JFrame novaHraOkno = new JFrame("Nová hra");
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	public minyNewGame() {
		int sirkaOkna = (int) (150), vyskaOkna = (int) (150);
		novaHraOkno.setSize(sirkaOkna, vyskaOkna);
		novaHraOkno.setResizable(false);
		novaHraOkno.setLocation((dim.width / 2) - sirkaOkna / 2, (dim.height / 2) - vyskaOkna / 2);
		novaHraOkno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel zavadeci = new JPanel(new GridLayout(3, 1));
		JPanel levy = new JPanel(new GridLayout(2, 1));
		JPanel pravy = new JPanel(new GridLayout(2, 1));
		JLabel levyPopisek = new JLabel("Zadej velikost pole: ");
		JLabel pravyPopisek = new JLabel("Zadej počet min: ");
		levyPopisek.setHorizontalAlignment(0);
		pravyPopisek.setHorizontalAlignment(0);
		final JTextField levyTextField = new JTextField();
		final JTextField pravyTextField = new JTextField();
		levyTextField.setText("30");
		pravyTextField.setText("50");
		levyTextField.setHorizontalAlignment(0);
		pravyTextField.setHorizontalAlignment(0);
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				miny minicky = new miny(Integer.valueOf(levyTextField.getText()), Integer.valueOf(pravyTextField.getText()));
				novaHraOkno.dispose();
			}
		});
		levy.add(levyPopisek);
		levy.add(levyTextField);
		pravy.add(pravyPopisek);
		pravy.add(pravyTextField);
		zavadeci.add(levy);
		zavadeci.add(pravy);
		zavadeci.add(ok);
		novaHraOkno.add(zavadeci);

		novaHraOkno.setVisible(true);
	}

	public static void main(String[] args) {
		new minyNewGame();
	}
}