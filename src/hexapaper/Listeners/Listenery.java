package hexapaper.Listeners;

import hexapaper.entity.Artefact;
import hexapaper.entity.Wall;
import hexapaper.gui.ArtefactAddFrame;
import hexapaper.gui.HraciPlocha;
import hexapaper.gui.NewPaperFrame;
import hexapaper.gui.PostavaAddFrame;
import hexapaper.source.Sklad;
import hexapaper.source.Sklad.prvekkNN;
import hexapaper.source.kNN;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class Listenery {

	Sklad sk = Sklad.getInstance();

	public class NovaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new NewPaperFrame();
		}
	}

	public class NactiListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sk.insertedEntity = new Wall(sk.LocDontCare);
			sk.insertingEntity = true;

		}
	}

	public class UlozListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sk.insertedEntity = new Artefact("A", sk.LocDontCare, null);
			sk.insertingEntity = true;

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
			System.exit(0);

		}

	}

	boolean ins = false;
	boolean ins2 = false;

	public class HraciPlochaListener implements MouseListener, MouseMotionListener {

		private kNN NN = new kNN();

		@Override
		public void mouseClicked(MouseEvent e) {
			double x = e.getX();
			double y = e.getY();
			ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
			if (e.getButton() == MouseEvent.BUTTON3) {
				HraciPlocha t = (HraciPlocha) e.getComponent();
				t.rotateEntity(idx);
			}
			if (e.getButton() == MouseEvent.BUTTON1 && sk.insertingEntity) {
				HraciPlocha t = (HraciPlocha) e.getComponent();
				t.insertEntity(idx, sk.insertedEntity, true);
			}
			if (e.getButton() == MouseEvent.BUTTON1 && !sk.insertingEntity) {
				sk.RMenu.redrawProperities(idx.get(0));
			}
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
			if (!sk.insertingEntity && e.getButton() == MouseEvent.BUTTON1) {
				ins = true;
				double x = e.getX();
				double y = e.getY();
				ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
				HraciPlocha t = (HraciPlocha) e.getComponent();
				t.saveEntity(idx.get(0).getIdx());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (ins && ins2 && e.getButton() == MouseEvent.BUTTON1) {
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

}
