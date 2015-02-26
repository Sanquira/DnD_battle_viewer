package dungeonmapper.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.JNumberTextField;
import core.file.FileHandler;
import dungeonmapper.dungeonMapper;
import dungeonmapper.file.DMWrapper;
import dungeonmapper.gui.NewPaperFrame;
import dungeonmapper.source.DMGridElement;
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
	public class SaveGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			DMWrapper wrapper=new DMWrapper(sk.COLS,sk.ROWS,sk.CSIZE);
			wrapper.addEntities(new HashMap<Integer,ArrayList<DMGridElement>>(sk.drawPlane.layers));
			FileHandler fileHandler=FileHandler.showDialog(sk.str.get("DMm_ext"), sk.str.get("DMm_text"),true);
			try {
				if(fileHandler!=null){fileHandler.write(wrapper);}
				System.out.println("hotovo");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	public class LoadGame implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			FileHandler fh=FileHandler.showDialog(sk.str.get("DMm_ext"), sk.str.get("DMm_text"),false);
			if(fh!=null){
				fh.load(DMWrapper.class).loadEntities();
			}
		}		
	}
	public class NewPaper implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new NewPaperFrame();
		}		
	}
	public class ChangeLayer implements ActionListener {
		private int change;
		public ChangeLayer(int change){
			this.change=change;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			sk.drawPlane.addChsnLay(change);			
		}		
	}
	public class SetLayer implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JNumberTextField txt=(JNumberTextField) e.getSource();
			sk.drawPlane.setChsnLay(txt.getInt());			
		}		
	}
}
