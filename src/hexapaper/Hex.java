package hexapaper;

import hexapaper.entity.Artefact;
import hexapaper.entity.Postava;
import hexapaper.file.LoadFile;
import hexapaper.file.SaveFile;
import hexapaper.source.Location;
import hexapaper.source.Sklad.PropPair;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

public class Hex {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hex window = new Hex();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Hex() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(27, 10, 89, 14);
		frame.getContentPane().add(lblNewLabel);

		ArrayList<PropPair> t = new ArrayList<>();
		t.add(new PropPair("Rasa", "Elf"));
		t.add(new PropPair("Health", "8"));
		t.add(new PropPair("Magy", "15"));
		t.add(new PropPair("Weapon", "Big huge Weapon"));
		t.add(new PropPair("Zbroj", "Plate Armor"));
		t.add(new PropPair("Health", "8"));
		final Postava btn = new Postava("Sprt", new Location(5, 4, 0), false, t);
		btn.setBounds(27, 35, 89, 23);
		frame.getContentPane().add(btn);

		final Postava crh = new Postava("Deserd", new Location(5, 4, 0), true, null);
		crh.addParam("sexy", "ano");
		crh.setBounds(27, 62, 89, 23);
		frame.getContentPane().add(crh);

		ArrayList<PropPair> x = new ArrayList<>();
		x.add(new PropPair("sexy", "ano"));
		final Artefact a = new Artefact("Object", new Location(5, 4, 0), x);
		a.setBounds(27, 104, 89, 23);
		frame.getContentPane().add(a);

		JButton newchar = new JButton("New button");
		newchar.setBounds(65, 238, 89, 23);
		newchar.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				List<Postava> t = new ArrayList<>();
				t.add(crh);
				t.add(btn);
				Postava[] p = {};
				new LoadFile();

			}

		});
		frame.getContentPane().add(newchar);
	}
}
