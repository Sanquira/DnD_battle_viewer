/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addons.miny;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Aleš
 */
public class sklad {

	static ImageIcon[] ikony = new ImageIcon[8];
	static ImageIcon bum = new ImageIcon(sklad.class.getResource("obr/bum.png"));
	static ImageIcon mina = new ImageIcon(sklad.class.getResource("obr/mina.png"));
	static ImageIcon vlajka = new ImageIcon(sklad.class.getResource("obr/pozor.png"));

	public static void nactiIkony() {
		for (int i = 1; i < 9; i++) {
			ikony[i - 1] = new ImageIcon(sklad.class.getResource("obr/" + i + ".png"));
		}

	}
}
