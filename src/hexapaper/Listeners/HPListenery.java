package hexapaper.Listeners;

import hexapaper.hexapaper;
import hexapaper.entity.Artefact;
import hexapaper.entity.EditableEntity;
import hexapaper.entity.HPEntity;
import hexapaper.entity.Postava;
import hexapaper.graphicCore.MyCanvas;
import hexapaper.gui.frames.ClientConnectFrame;
import hexapaper.gui.frames.EditDatabaseFrame;
import hexapaper.gui.frames.NewPaperFrame;
import hexapaper.gui.panels.EditDatabasePanel;
import hexapaper.source.HPSklad;
import hexapaper.source.HPSklad.prvekkNN;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import network.command.users.CommandClient;
import addons.dice.Dice;
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
			FileHandler fileHandler=FileHandler.showDialog(sk.str.Hex_ext, sk.str.Hex_text,false);
			HexWrapper HWrapper=fileHandler.load(HexWrapper.class);
			if(HWrapper!=null){
				if(sk.checkVersion(HWrapper.Version)){
					sk.c.RADIUS=HWrapper.Radius;
					sk.c.gridRa=HWrapper.GridRA;
					sk.c.gridSl=HWrapper.GridSl;
					sk.initLoad(HWrapper.load());
					if (sk.isConnected && sk.isPJ) {
						sk.client.radiusHexapaper();
						sk.client.updateHexapaper();
					}
				}
			}
		}
	}

	public class UlozListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//new SaveFile(sk.souradky, sk.RADIUS, sk.gridSl, sk.gridRa);
			if(sk.souradky.size() == 0){
				return;
			}
			FileHandler fileHandler = FileHandler.showDialog(sk.str.Hex_ext, sk.str.Hex_text,true);
			try {
				sk.saveMap(fileHandler);
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
			if(sk.editDatabase == null){
				sk.editDatabase = new EditDatabaseFrame();
			}
			sk.editDatabase.showFrame();
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
			try {
				sk.c.saveConfig();
				sk.c.saveDb();
				sk.c.saveMap();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public class HraciPlochaListener extends MouseAdapter {
		
//		private Point startPos = new Point(0,0);
//		private Point endPos = new Point(0,0);
//		private boolean dragging = false;
		//private kNN NN = new kNN();
		
		@Override
		public void mouseMoved(MouseEvent e) {
			sk.updatePosition(e.getX(), e.getY());
			if(sk.isConnected&&!sk.isPJ){
				return;
			}
			MyCanvas t = (MyCanvas) e.getComponent();
			if(t != null){
				//ArrayList<prvekkNN> idx = NN.getkNNindexes(e.getX(), e.getY());
				t.drawCursor(e.getX(), e.getY());
			}	
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				double x = e.getX();
				double y = e.getY();
				ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
				MyCanvas t = (MyCanvas) e.getComponent();
				t.drawCursor(x, y);
				//Point2D.Double p = sk.getPosition(x, y);
				//if (p.x <= sk.c.gridRa && p.y <= sk.c.gridSl) {
					//System.out.println(sk.insertingEntity);
					if (sk.repeatableInsert) {
						t.insertEntity(idx.get(0).getIdx(), sk.insertedEntity, true);
						//System.out.println("inserting");
					}
					if (sk.colorAdd){
						colorHex(idx);
					}
					//ins2 = true;
				//}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (sk.canEvent) {
				double x = e.getX();
				double y = e.getY();
				ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
				//Point2D.Double  p = sk.getPosition(x, y);
				//if (p.x <= sk.c.gridRa && p.y <= sk.c.gridSl) {
				//if (idx.get(0).getVzd() <= sk.c.RADIUS) {
					MyCanvas t = (MyCanvas) e.getComponent();
					if (e.getButton() == MouseEvent.BUTTON3) {					
						t.rotateEntity(idx);
						sk.hraciPlocha.revalidate();
						sk.hraciPlocha.repaint();
					}
					if(SwingUtilities.isLeftMouseButton(e)){
						if (sk.insertingEntity) {
							t.insertEntity(idx.get(0).getIdx(), sk.insertedEntity, true);
							sk.hraciPlocha.revalidate();
							sk.hraciPlocha.repaint();
						}
						else {
							if ((sk.isConnected && sk.isPJ) || !sk.isConnected) {
								if(sk.colorAdd){
									colorHex(idx);
	//								sk.souradky.get(idx.get(0).getIdx()).setBcg(sk.color);
	//								Object[] o={idx.get(0).getIdx(),sk.color};
	//								sk.send(o,"paintEnt",true);
								}
								HPEntity en = sk.souradky.get(idx.get(0).getIdx());
								if(en instanceof EditableEntity){
									sk.RMenu.updateEntity((EditableEntity) en);
									//sk.RMenu.cpane.redrawProperities(idx.get(0));
									ins = true;
									t.saveEntity(idx.get(0).getIdx());
									//System.out.println("Přesouvám");
								}	
							}
						}
					//}
					//t.revalidate();
					//t.repaint();
					//System.out.println("Vloženo, překresleno");
				}
			}
		}
		
		private void colorHex(ArrayList<prvekkNN> idx){
			sk.souradky.get(idx.get(0).getIdx()).setBcg(sk.color);
			sk.hraciPlocha.revalidate();
			sk.hraciPlocha.repaint();
			Object[] o={idx.get(0).getIdx(),sk.color};
			sk.send(o,"paintEnt",true);	
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (ins && e.getButton() == MouseEvent.BUTTON1 && sk.canEvent) {
				ins = false;
				ins2 = false;
				double x = e.getX();
				double y = e.getY();
				ArrayList<prvekkNN> idx = NN.getkNNindexes(x, y);
				MyCanvas t = (MyCanvas) e.getComponent();
				t.moveEntity(t.oldIdx, idx.get(0).getIdx());
				//t.releaseEntity(idx.get(0).getIdx());
				//t.repaint();
			}
		}

	}

	public class ExportArtDat implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			FileHandler fh=FileHandler.showDialog(sk.str.Db_ext, sk.str.Db_text, true);
			try {
				sk.saveArtefacts(fh);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class ExportArtOne implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			//new ExportOneFrame(sk.databazeArtefaktu);
		}
	}

	public class ExportPostDat implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			FileHandler fh=FileHandler.showDialog(sk.str.Db_ext, sk.str.Db_text, true);
			try {
				sk.saveCharacters(fh);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public class ExportPostOne implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			//new ExportOneFrame(sk.databazePostav);
		}
	}

	public class ImportAP implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			if (sk.isConnected && !sk.isPJ) {
				return;
			}
			FileHandler fh=FileHandler.showDialog(sk.str.Db_ext, sk.str.Db_text, false);
			if(fh!=null){fh.load(DatabaseWrapper.class).loadDatabase();}
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
	public class DiceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			new Dice();
		}

	}
	public class ExportLangListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent paramActionEvent) {
			
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
	public class ChangeLang implements ActionListener {
			
		private String name;
		private hexapaper frame;
		public ChangeLang(String name,hexapaper frame){
			this.name = name;
			this.frame = frame;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			sk.SetupLang(name);
			sk.c.Language = name;
			JOptionPane.showMessageDialog(frame,
				    sk.str.LanguageChangeMessage,
				    sk.str.LanguageChange,
				    JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	public class Dsc implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println("Dsc by button");
				sk.client.disconnect("Disconnected");
				sk.client = null;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}		
	}

}
