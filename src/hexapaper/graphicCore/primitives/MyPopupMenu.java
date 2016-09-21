package hexapaper.graphicCore.primitives;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import hexapaper.graphicCore.Canvas;
import hexapaper.graphicCore.GCMath;
import hexapaper.language.HPStrings;
import hexapaper.source.HPSklad;
import logLibrary.StackTraceTools;
import mathLibrary.vector.Vector2D;

public class MyPopupMenu extends JPopupMenu {

	private Canvas canvas;

	private HPStrings strings = HPSklad.getInstance().str;

	public MyPopupMenu(Canvas canvas) {
		this.canvas = canvas;
		setupPopupMenu();
	}

	private void setupPopupMenu() {
		// Set position
		this.add(new JMenuItem(new AbstractAction(strings.GCsetPosition) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Vector2D poloha = canvas.getPositionHC();
				String ret = JOptionPane.showInputDialog(strings.GCsetPositionMessage,
						(int) poloha.u1 + ", " + (int) poloha.u2);
				if (ret != null) {
					String[] tmpf = ret.split(",");
					try {
						int x = Integer.parseInt(tmpf[0].trim());
						int y = Integer.parseInt(tmpf[1].trim());
						canvas.setPositionHC(new Vector2D(x, y));
						canvas.repaint();
					} catch (NumberFormatException er) {
						System.err.println(StackTraceTools.shortenedStackTrace(er, 5));
					}
				}
			}
		}));
	}

}
