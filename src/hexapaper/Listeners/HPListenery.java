package hexapaper.Listeners;

import hexapaper.hexapaper;
import hexapaper.file.Wrappers.DatabaseWrapper;
import hexapaper.file.Wrappers.HexWrapper;
import hexapaper.gui.ArtefactAddFrame;
import hexapaper.gui.ChangeZoomFrame;
import hexapaper.gui.ClientConnectFrame;
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
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import network.command.users.CommandClient;
import addons.dice.Dice;
import core.kNN;
import core.file.FileHandler;

public class HPListenery {

	HPSklad sk = HPSklad.getInstance();
	
	public class ScrollListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			sk.hraciPlocha.repaint();
		}

	}

	public class NovaListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (sk.isConnected && !sk.isPJ) {
				return;
			}
			new NewPaperFrame();
		}
	}

	public class NactiListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (sk.isConnected && !sk.isPJ) {
				return;
			}
			FileHandler fileHandler=FileHandler.showDialog(sk.str.get("Hex_ext"), sk.str.get("Hex_text"),false);
			HexWrapper HWrapper=fileHandler.load(HexWrapper.class);
			if(sk.checkVersion(HWrapper.Version)){
				sk.RADIUS=HWrapper.Radius;
				sk.gridRa=HWrapper.GridRA;
				sk.gridSl=HWrapper.GridSl;
				sk.initLoad(HWrapper.load());
				if (sk.isConnected && sk.isPJ) {
					sk.client.radiusHexapaper();
					sk.client.updateHexapaper();
				}
			}
		}
	}

	public class UlozListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//new SaveFile(sk.souradky, sk.RADIUS, sk.gridSl, sk.gridRa);
			FileHandler fileHandler=FileHandler.showDialog(sk.str.get("Hex_ext"), sk.str.get("Hex_text"),true);
			HexWrapper HWrapper=sk.wrappers.new HexWrapper(sk.gridSl,sk.RADIUS,sk.gridRa,sk.FILEVERSION);
			HWrapper.addEntities(sk.souradky);
			try {
				fileHandler.write(HWrapper);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public class PridejPostavu implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			if (sk.isConnected && !sk.isPJ) {
				return;
			}
			new PostavaAddFrame();
		}
	}

	public class PridejArtefakt implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			if (sk.isConnected && !sk.isPJ) {
				return;
			}
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
			/*
			 * Object[] opt = { sk.str.get("ano"), sk.str.get("ne") }; int t =
			 * JOptionPane.showOptionDialog(paramWindowEvent.getComponent(),
			 * sk.str.get("zpravaZtrataDat"), sk.str.get("ztrataDat"),
			 * JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
			 * opt, opt[0]); if (t == JOptionPane.OK_OPTION) { System.exit(0);
			 * }//
			 */
			System.exit(0);
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
			ArrayList<prvekkNN> idx = NN.getkNNindexes(e.getX(), e.getY());
			sk.updatePosition((int)idx.get(0).getX1(), (int)idx.get(0).getY1());
			if(sk.isConnected&&!sk.isPJ){
				return;
			}
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
					if ((sk.isConnected && sk.isPJ) || !sk.isConnected) {
						if(sk.colorAdd){
							sk.souradky.get(idx.get(0).getIdx()).setBcg(sk.color);
							Object[] o={idx.get(0).getIdx(),sk.color};
							sk.send(o,"paintEnt",true);
						}
						sk.RMenu.redrawProperities(idx.get(0));
						ins = true;
						HraciPlocha t = (HraciPlocha) e.getComponent();
						t.saveEntity(idx.get(0).getIdx());
					}
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (ins && e.getButton() == MouseEvent.BUTTON1 && sk.canEvent) {
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
			FileHandler fh=FileHandler.showDialog(sk.str.get("Db_ext"), sk.str.get("Db_text"), true);
			DatabaseWrapper db=sk.wrappers.new DatabaseWrapper();
			db.Version=sk.FILEVERSION;
			db.addEntities(sk.databazeArtefaktu);
			try {
				fh.write(db);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			FileHandler fh=FileHandler.showDialog(sk.str.get("Db_ext"), sk.str.get("Db_text"), true);
			DatabaseWrapper db=sk.wrappers.new DatabaseWrapper();
			db.Version=sk.FILEVERSION;
			db.addEntities(sk.databazePostav);
			try {
				fh.write(db);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			if (sk.isConnected && !sk.isPJ) {
				return;
			}
			FileHandler fh=FileHandler.showDialog(sk.str.get("Db_ext"), sk.str.get("Db_text"), false);
			fh.load(DatabaseWrapper.class).loadDatabase();
			//new LoadFile(sk.str.get("desc"), sk.str.get("File_ext"), sk.str.get("Db_ext"));
			//sk.RMenu.updateDatabase();

		}
	}

	public class Client implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new ClientConnectFrame();
			// System.out.println("Pokus o připojení");
			// DateFormat dateFormat = new SimpleDateFormat("ss");
			// Date date = new Date();
			//
			// HexaClient c=new HexaClient();
			// try {
			// c.connect("212.96.186.28", 10055, dateFormat.format(date));
			// } catch (UnknownHostException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
	}
	
	public class Server implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			CommandClient c = new CommandClient();
			try {
				c.connect("localhost", 10555, "Sprt");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	public class Zoom implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new ChangeZoomFrame();
		}
	}

	public class DiceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new Dice();
		}

	}
	public class ExportLangListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			sk.str.saveLang();
		}

	}
	public class PJInfoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			if(sk.isPJ){
				sk.PJInfo.setVisible(true);
			}			
		}

	}

}
