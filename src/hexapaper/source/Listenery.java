package hexapaper.source;

import hexapaper.entity.Artefact;
import hexapaper.entity.Entity;
import hexapaper.entity.Wall;
import hexapaper.gui.ArtefactAddFrame;
import hexapaper.gui.HraciPlocha;
import hexapaper.gui.NewPaper;
import hexapaper.gui.PostavaAddFrame;
import hexapaper.source.Sklad.prvekkNN;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Action;

public class Listenery {

	Sklad sk = Sklad.getInstance();

	public class NovaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new NewPaper();
		}
	}

	public class NactiListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sk.insertedEntity = new Wall(sk.LocDontCare);
			sk.insertingEntity = true;
			sk.updateCursor();

		}
	}

	public class UlozListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			sk.insertedEntity = new Artefact("A", sk.LocDontCare, null);
			sk.insertingEntity = true;
			sk.updateCursor();

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

	public class HraciPlochaListener implements MouseListener {

		private kNN NN = new kNN();

		@Override
		public void mouseClicked(MouseEvent e) {
			double x = e.getX();
			double y = e.getY();
			ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
			System.out.println(idx.get(0).getIdx());
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
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

}
