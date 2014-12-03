package dungeonmapper.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dungeonmapper.dungeonMapper;
import dungeonmapper.source.DMSklad;

public class DMListenery {

	DMSklad sk = DMSklad.getInstance();

	public class KonecHardListener implements WindowListener {
		@Override
		public void windowClosing(WindowEvent paramWindowEvent) {
			Object[] opt = { sk.str.get("ano"), sk.str.get("ne") };
			// int t =
			// JOptionPane.showOptionDialog(paramWindowEvent.getComponent(),
			// sk.str.get("zpravaZtrataDat"), sk.str.get("ztrataDat"),
			// JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
			// opt, opt[0]);
			int t = JOptionPane.OK_OPTION;
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

	public class KonecListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new KonecHardListener().windowClosing(new WindowEvent(dungeonMapper.DMfrm, 1));
		}

	}

	public class RMToggleButListener implements ActionListener {

		String but = "null";

		public RMToggleButListener(String name) {
			but = name;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (Arrays.toString(sk.drawOrders).contains(but)) {
				sk.drawOrder = but;
			}
			if (Arrays.toString(sk.drawShapes).contains(but)) {
				if (!((JToggleButton) arg0.getSource()).isSelected()) {
					sk.drawShape = "null";
				} else {
					sk.drawShape = but;
				}
			}
		}
	}
	
	public class ScrollListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			sk.drawPlane.repaint();
		}

	}
}
