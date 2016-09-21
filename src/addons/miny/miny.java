package addons.miny;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class miny extends JFrame {

	int[][] pole, hraciPlocha;
	boolean[][] viditelne;
	int velikostButtonu = 20;
	int velikost, pocetMin;
	JPanel hlavniPlocha;
	JButton[][] min;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	// <editor-fold defaultstate="collapsed" desc="miny - konstruktor">
	public miny(int velikost, int pocet) {
		this.velikost = velikost;
		this.pocetMin = pocet;
		vytvorPole(velikost, pocet);
		vytvorButtony(velikost);
		sklad.nactiIkony();
		int sirkaOkna = velikost * velikostButtonu;
		int vyskaOkna = sirkaOkna;
		setLocation((dim.width / 2) - sirkaOkna / 2, (dim.height / 2) - vyskaOkna / 2);
		setVisible(true);
		setResizable(false);
		setTitle("Piskvorky beta");
		add(hlavniPlocha);
		pack();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="vytvorPole - vytvori pole o zadane velikosti a umisti do nej miny">
	private void vytvorPole(int velikost, int pocet) {
		pole = new int[velikost][velikost];
		hraciPlocha = new int[velikost][velikost];
		viditelne = new boolean[velikost][velikost];
		for (int i = 0; i < pocet; i++) {
			int X, Y;
			do {
				X = (int) (Math.random() * (velikost));
				Y = (int) (Math.random() * (velikost));
			} while (pole[X][Y] == -1);
			pole[X][Y] = -1;
		}
		spoctiOkoli();
		for (int i = 0; i < velikost; i++) {
			System.arraycopy(pole[i], 0, hraciPlocha[i], 0, velikost);
		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="spoctiOkoli - pricte do okoli miny jednicku">
	private void spoctiOkoli() {
		for (int i = 0; i < pole.length; i++) {
			for (int j = 0; j < pole[i].length; j++) {
				viditelne[i][j] = false;
				if (pole[i][j] == -1) {
					try {
						if (pole[i + 1][j] != -1) {
							pole[i + 1][j] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					try {
						if (pole[i - 1][j] != -1) {
							pole[i - 1][j] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					try {
						if (pole[i + 1][j + 1] != -1) {
							pole[i + 1][j + 1] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					try {
						if (pole[i + 1][j - 1] != -1) {
							pole[i + 1][j - 1] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					try {
						if (pole[i - 1][j + 1] != -1) {
							pole[i - 1][j + 1] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					try {
						if (pole[i - 1][j - 1] != -1) {
							pole[i - 1][j - 1] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					try {
						if (pole[i][j + 1] != -1) {
							pole[i][j + 1] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
					try {
						if (pole[i][j - 1] != -1) {
							pole[i][j - 1] += 1;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
					}
				}

			}

		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="rozhodniViditelnost - nastavi viditelnost na true pokud na tom miste je 0">
	private void rozhodniViditelnost(int i, int j) {
		fronta FIFO = new fronta();
		FIFO.addLast(i, j);
		while (true) {
			int[] first = FIFO.deteteFirst();
			if (first == null) {
				return;
			}
			if (hraciPlocha[first[0]][first[1]] == 0 && !viditelne[first[0]][first[1]]) {
				viditelne[first[0]][first[1]] = true;
				if (first[0] >= 1) {
					FIFO.addLast(first[0] - 1, first[1]);
				}
				if (first[0] < velikost - 1) {
					FIFO.addLast(first[0] + 1, first[1]);
				}
				if (first[1] < velikost - 1) {
					FIFO.addLast(first[0], first[1] + 1);
				}
				if (first[1] >= 1) {
					FIFO.addLast(first[0], first[1] - 1);
				}
			}
		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="vytvorButtony - vytvori buttony pro hru">
	private void vytvorButtony(int velikost) {
		hlavniPlocha = new JPanel(new GridLayout(velikost, velikost));
		min = new JButton[velikost][velikost];
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				min[i][j] = new JButton();
				min[i][j].setPreferredSize(new Dimension(velikostButtonu, velikostButtonu));
				min[i][j].setBackground(Color.lightGray);
				min[i][j].addMouseListener(new minyListener());
				hlavniPlocha.add(min[i][j]);
			}
		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="minyListener - posunuje hru">
	public class minyListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent me) {
			Object source = me.getSource();
			for (int i = 0; i < velikost; i++) {
				for (int j = 0; j < velikost; j++) {
					if (source == min[i][j]) {
						if (hraciPlocha[i][j] == 0 && me.getButton() == 1 && !viditelne[i][j]) {
							rozhodniViditelnost(i, j);
						}
						if (hraciPlocha[i][j] == -1 && me.getButton() == 1) {
							prohra();
						}
						if (me.getButton() == 3 && !viditelne[i][j]) {
							viditelne[i][j] = true;
							hraciPlocha[i][j] = -2;
							pocetMin--;
							break;
						}
						if (me.getButton() == 3 && hraciPlocha[i][j] == -2) {
							viditelne[i][j] = false;
							pocetMin++;
							hraciPlocha[i][j] = pole[i][j];
							break;
						}
						if (me.getButton() == 1 && hrajuDoCisel(i, j) && !viditelne[i][j]) {
							viditelne[i][j] = true;
						}
					}
				}
			}
			obnovIkony();
			overVyhru();
		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="overVyhru - vyhra je poduk nezbyly zadne miny a vsechny policka jsou viditelna">
	private void overVyhru() {
		boolean vyhra = true;
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				if (!viditelne[i][j]) {
					vyhra = false;
				}
			}
		}
		if (pocetMin == 0 && vyhra) {
			JOptionPane.showMessageDialog(this, "Gratujuli! Vyhrál jsi", "Výhra", JOptionPane.WARNING_MESSAGE);
			dispose();
		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="hrajuDoCisel - vraci true kdyz kliknu na cislo">
	private boolean hrajuDoCisel(int i, int j) {
		for (int k = 1; k < 9; k++) {
			if (hraciPlocha[i][j] == k) {
				return true;
			}
		}
		return false;
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="prohra - definuje co se stane kdyz prohrajes">
	private void prohra() {
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {

				switch (hraciPlocha[i][j]) {
				case -2: {
					min[i][j].setIcon(sklad.vlajka);
					break;
				}
				case -1: {
					min[i][j].setIcon(sklad.bum);
					break;
				}
				case 0: {
					min[i][j].setBackground(Color.white);
					break;
				}
				default: {
					min[i][j].setIcon(vratIkonu(i, j));
				}
				}
			}
		}
		JOptionPane.showMessageDialog(this, "Prohrál jsi!", "Prohra", JOptionPane.WARNING_MESSAGE);
		dispose();
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="odhalCisla - odhali nejblizsi okoli bile plochy tj. cisla">
	private void odhalCisla() {
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				try {
					if (hraciPlocha[i + 1][j] == 0 && viditelne[i + 1][j] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}
				try {
					if (hraciPlocha[i - 1][j] == 0 && viditelne[i - 1][j] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}
				try {
					if (hraciPlocha[i][j + 1] == 0 && viditelne[i][j + 1] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}
				try {
					if (hraciPlocha[i][j - 1] == 0 && viditelne[i][j - 1] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}
				try {
					if (hraciPlocha[i + 1][j + 1] == 0 && viditelne[i + 1][j + 1] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}
				try {
					if (hraciPlocha[i - 1][j - 1] == 0 && viditelne[i - 1][j - 1] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}
				try {
					if (hraciPlocha[i - 1][j + 1] == 0 && viditelne[i - 1][j + 1] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}

				try {
					if (hraciPlocha[i + 1][j - 1] == 0 && viditelne[i + 1][j - 1] && hraciPlocha[i][j] != 0) {
						viditelne[i][j] = true;
						min[i][j].setIcon(vratIkonu(i, j));
					}
				} catch (Exception e) {
				}
				if (hraciPlocha[i][j] == 0) {
					min[i][j].setIcon(null);
				}
			}
		}
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="vratIkonu - vraci ikonu CISLA">
	private ImageIcon vratIkonu(int i, int j) {
		for (int k = 0; k < 9; k++) {
			if (hraciPlocha[i][j] == k) {
				return sklad.ikony[k - 1];
			}
		}
		return sklad.ikony[1];
	}

	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="obnovIkony - prekresli hraci plochu">
	private void obnovIkony() {
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				min[i][j].setIcon(null);
				if (viditelne[i][j] == true) {
					switch (hraciPlocha[i][j]) {
					case -2: {
						min[i][j].setIcon(sklad.vlajka);
						break;
					}
					case 0: {
						min[i][j].setBackground(Color.white);
						break;
					}
					default: {
						min[i][j].setIcon(vratIkonu(i, j));
					}
					}
				}
			}
		}
		odhalCisla();
	}
	// </editor-fold>
}
