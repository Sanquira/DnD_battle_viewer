package hexapaper.Listeners;

import hexapaper.hexapaper;
import hexapaper.file.LoadFile;
import hexapaper.file.SaveFile;
import hexapaper.gui.ArtefactAddFrame;
import hexapaper.gui.ExportOneFrame;
import hexapaper.gui.HraciPlocha;
import hexapaper.gui.NewPaperFrame;
import hexapaper.gui.PostavaAddFrame;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.prvekkNN;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import addons.dice.Dice;
import core.kNN;

public class HPListenery {

	HPSklad sk = HPSklad.getInstance();

	public class NovaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new NewPaperFrame();
		}
	}

	public class NactiListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LoadFile load = new LoadFile(sk.str.get("Hex_text"), sk.str.get("Hex_ext"));
			sk.initLoad(load.getSouradky());
		}
	}

	public class UlozListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new SaveFile(sk.souradky, sk.RADIUS, sk.gridSl, sk.gridRa);
		}
	}

	public class PridejPostavu implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new PostavaAddFrame();
		}
	}

	public class PridejArtefakt implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new ArtefactAddFrame();
		}
	}

	public class KonecListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new KonecHardListener().windowClosing(new WindowEvent(hexapaper.HPfrm, 1));
		}
	}

	public class KonecHardListener implements WindowListener {
		@Override
		public void windowClosing(WindowEvent paramWindowEvent) {
			Object[] opt = { sk.str.get("ano"), sk.str.get("ne") };
			int t = JOptionPane.showOptionDialog(paramWindowEvent.getComponent(), sk.str.get("zpravaZtrataDat"), sk.str.get("ztrataDat"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, opt, opt[0]);
			if (t == JOptionPane.OK_OPTION) {
				System.exit(0);
			}
		}

		@Override
		public void windowOpened(WindowEvent paramWindowEvent) {
		}

		@Override
		public void windowClosed(WindowEvent paramWindowEvent) {
		}

		@Override
		public void windowIconified(WindowEvent paramWindowEvent) {
		}

		@Override
		public void windowDeiconified(WindowEvent paramWindowEvent) {
		}

		@Override
		public void windowActivated(WindowEvent paramWindowEvent) {
		}

		@Override
		public void windowDeactivated(WindowEvent paramWindowEvent) {
		}
	}

	boolean ins = false;
	boolean ins2 = false;

	public class HraciPlochaListener implements MouseListener, MouseMotionListener {

		private kNN NN = new kNN();

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			HraciPlocha t = (HraciPlocha) e.getComponent();
			t.drawCursor(e.getX(), e.getY());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				ins2 = true;
				HraciPlocha t = (HraciPlocha) e.getComponent();
				t.drawCursor(e.getX(), e.getY());
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (sk.canEvent) {
				double x = e.getX();
				double y = e.getY();
				ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
				if (e.getButton() == MouseEvent.BUTTON3) {
					HraciPlocha t = (HraciPlocha) e.getComponent();
					t.rotateEntity(idx);
				}
				if (e.getButton() == MouseEvent.BUTTON1 && sk.insertingEntity) {
					HraciPlocha t = (HraciPlocha) e.getComponent();
					t.insertEntity(idx.get(0).getIdx(), sk.insertedEntity, true);
				}
				if (e.getButton() == MouseEvent.BUTTON1 && !sk.insertingEntity) {
					ins = true;
					sk.RMenu.redrawProperities(idx.get(0));
					HraciPlocha t = (HraciPlocha) e.getComponent();
					t.saveEntity(idx.get(0).getIdx());
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (ins && ins2 && e.getButton() == MouseEvent.BUTTON1 && sk.canEvent) {
				ins = false;
				ins2 = false;
				double x = e.getX();
				double y = e.getY();
				ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
				HraciPlocha t = (HraciPlocha) e.getComponent();
				t.releaseEntity(idx.get(0).getIdx());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

	public class ExportArtDat implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new SaveFile(sk.databazeArtefaktu);
		}
	}

	public class ExportArtOne implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new ExportOneFrame(sk.databazeArtefaktu);
		}
	}

	public class ExportPostDat implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new SaveFile(sk.databazePostav);
		}
	}

	public class ExportPostOne implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new ExportOneFrame(sk.databazePostav);
		}
	}

	public class ImportAP implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new LoadFile(sk.str.get("desc"), sk.str.get("File_ext"), sk.str.get("Db_ext"));
			sk.RMenu.updateDatabase();
		}
	}

	public class KostkaListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new Dice();
		}

	}
}
