package hexapaper.network.server;

import hexapaper.entity.HPEntity;
import hexapaper.source.HPSklad;

import java.awt.Color;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import network.core.annotations.Annotations.ConnectAnnotation;
import network.core.annotations.Annotations.DisconnectAnnotation;
import network.core.annotations.Annotations.PacketReceiveAnnotation;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.MessagePacket;
import addons.dice.EE;

public class ClientListeners {
	private HexaClient hexaClient;
	private HPSklad storage;
	// Connect Listeners
	@ConnectAnnotation
	private ConnectListener cnt = new ConnectListener() {
		public void Connect(Socket c) {
			try {
				hexaClient.send(HPSklad.VERSION, "version");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Připojeno k serveru");
			storage.isConnected = true;
			storage.client = hexaClient;
			storage.updateConnect();
			storage.colorJMenu();
			//storage.RMenu.cpane.updateCreate();
		}
	};
	// Disconnect Listeners
	@DisconnectAnnotation
	private DisconnectListener dsc = new DisconnectListener() {
		public void Disconnect(Socket s, Exception e, String reason, boolean kicked) {
			e.printStackTrace();
			storage.isConnected = false;
			storage.isPJ = false;
			storage.updateConnect();
			String message = storage.str.DisconnectMessage;
			if(kicked){
				message = storage.str.KickMessage;
			}
			JOptionPane.showMessageDialog(storage.hraciPlocha,
					message+reason,
					storage.str.DisconnectWindow,
					JOptionPane.WARNING_MESSAGE);
		}
	};
	@PacketReceiveAnnotation(header = "RadiusHexapaper")
	private PacketReceiveListener RadiusHexapaper = new PacketReceiveListener() {
		public void packetReceive(MessagePacket p) {
			storage.setStatus("Přijímání rozsahu HexaPapaeru");
			// System.out.println("Received hexapaper Radiuses from server");
			Object[] paper = (Object[]) p.getObject();
			storage.c.gridRa = (int) paper[0];
			storage.c.gridSl = (int) paper[1];
			storage.c.RADIUS = (int) paper[2];
			storage.initLoad(new ArrayList<HPEntity>());
			storage.setStatus("Rozměry HexaPapaperu přijaty");
		}
	};
	@PacketReceiveAnnotation(header = "EntityHexapaper")
	private PacketReceiveListener EntityHexapaper = new PacketReceiveListener() {
		public void packetReceive(MessagePacket p) {
			storage.setStatus("Přijímání entit HexaPaperu");
			storage.souradky = (ArrayList<HPEntity>) p.getObject();
			storage.initLoad(storage.souradky);
			storage.setStatus("HexaPaperu načten ze serveru");
		}
	};
	@PacketReceiveAnnotation(header = "rotateEnt")
	private PacketReceiveListener rotateEnt = new PacketReceiveListener() {
		public void packetReceive(MessagePacket p) {
			Integer[] table = (Integer[]) p.getObject();
			//System.out.println(table[0] + ":" + table[1] + ":" + table[2]);
			for (HPEntity ent : storage.souradky) {
				if (ent.loc.getX().equals(table[0]) && ent.loc.getY().equals(table[1])) {
					//System.out.println("cool");
					ent.loc.setDir(table[2]);
					ent.recreateGraphics();
					storage.setStatus("Rotována entita");
					// System.out.println("Předělána entita");
					storage.hraciPlocha.repaint();
				}
			}
		}
	};
	@PacketReceiveAnnotation(header = "paintEnt")
	PacketReceiveListener paintEnt = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			Object[] table=(Object[]) p.getObject();
			if((Integer) table[0]<storage.souradky.size()){
				storage.souradky.get((Integer) table[0]).setBcg((Color) table[1]);
				storage.hraciPlocha.repaint();
			}
			//System.out.println(storage.souradky.get((Integer) table[0]).getBcg().getRGB());
		}		
	};
	@PacketReceiveAnnotation(header = "requestPJInfo")
	private PacketReceiveListener requestPJInfo = new PacketReceiveListener() {
		public void packetReceive(MessagePacket p) {
			storage.isPJ = true;
			storage.updateConnect();
			// System.out.println("Requested PJ info");
			hexaClient.radiusHexapaper();
			hexaClient.updateHexapaper();
			hexaClient.updateDatabase();
			storage.setStatus("Nastaven PJ");
			//System.out.println("došel request");
		}
	};
	@PacketReceiveAnnotation(header = "removePJ")
	private PacketReceiveListener removePJ = new PacketReceiveListener() {
		public void packetReceive(MessagePacket p) {
			storage.isPJ = false;
			storage.PJInfo.setVisible(false);
			// System.out.println("No longer PJ");
			storage.updateConnect();
			storage.setStatus("Odebrán PJ");
		}
	};
	@PacketReceiveAnnotation(header = "insertEnt")
	PacketReceiveListener insertEnt = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			// System.out.println("Vložena entita");
			Object[] table = (Object[]) p.getObject();
			storage.hraciPlocha.insertEntity((int) table[0], ((HPEntity) table[1]), true);
			storage.hraciPlocha.repaint();
			storage.setStatus("Získáná entita");
		}
	};
	@PacketReceiveAnnotation(header = "EntChangeTag")
	PacketReceiveListener EntChangeName = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			Object[] table = (Object[]) p.getObject();
			// System.out.println("přijato"+(Integer) table[0]+","+(Integer)
			// table[1]);
			// System.out.println(table[0]+":"+table[1]+":"+table[2]+":"+table[3]);
			for (HPEntity ent : storage.souradky) {
				if (ent.loc.getX().equals((Integer) table[0]) && ent.loc.getY().equals((Integer) table[1])) {
					ent.setTag((String) table[2]);
					storage.hraciPlocha.repaint();
					// System.out.println("Změnen nick a tag Entity");
				}
			}
		}
	};
	@PacketReceiveAnnotation(header = "kick")
	PacketReceiveListener kick = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			JOptionPane.showMessageDialog(storage.hraciPlocha,
					storage.str.KickMessage + (String) p.getObject(),
					storage.str.KickWindow,
					JOptionPane.WARNING_MESSAGE);
			storage.setStatus("Vyhozen ze serveru");
		}
	};
	@PacketReceiveAnnotation(header = "dice")
	PacketReceiveListener dice = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			Integer roll = ((Integer[]) p.getObject())[0];
			Integer range = ((Integer[]) p.getObject())[1];
			Integer modifier = ((Integer[]) p.getObject())[2];
			String message;
			if ((roll + modifier) == -666) {
				new Thread(new EE().new HaGay()).start();
				return;
			}
			if (modifier == 0) {
				message = p.getNick() + " si hodil " + roll + " na " + range + " kostce.";
			}
			else {
				message = p.getNick() + " si hodil " + (roll + modifier) + " na " + range + " kostce se základním hodem " + roll;
			}
			// String
			// message=p.getNick()+" si hodil "+(roll+modifier)+" na "+range+" kostce se základním hodem "+roll;
			//System.out.println(message);
			storage.getDiceLog().addMessage(message);
		}
	};
	@PacketReceiveAnnotation(header = "version")
	PacketReceiveListener version = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			try {
				hexaClient.send(HPSklad.VERSION, "version");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// storage.setStatus("Server vyžádal vaši verzi");
		}
	};
/*	@PacketReceiveAnnotation(header = "addPlayer")
	PacketReceiveListener addPlayer = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			String name = (String) p.getObject();
			storage.PJInfo.addPlayer(name);
		}
	};
	@PacketReceiveAnnotation(header = "removePlayer")
	PacketReceiveListener removePlayer = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			String name = (String) p.getObject();
			storage.PJInfo.removePlayer(name);
		}
	};*/
	@PacketReceiveAnnotation(header = "PlayerConnect")
	PacketReceiveListener PConnect = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			String Message = p.getNick() + " se připojil na server";
			//System.out.println(Message);
			storage.setStatus(Message);
			storage.getPJLog().addMessage(Message);
			storage.PJInfo.addPlayer(p.getNick());
		}
	};
	@PacketReceiveAnnotation(header = "PlayerDisconnect")
	PacketReceiveListener PDisconnect = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			String Message = p.getNick() + " se odpojil z serveru";
			//System.out.println(Message);
			storage.setStatus(Message);
			storage.getPJLog().addMessage(Message);
			storage.PJInfo.removePlayer(p.getNick());
		}
	};
	@PacketReceiveAnnotation(header = "cmd")
	PacketReceiveListener cmd = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.print((String) p.getObject());
		}
	};

	public ClientListeners(HexaClient hexaClient, HPSklad storage) {
		this.hexaClient = hexaClient;
		this.storage = storage;
		hexaClient.registerClass(this);
	}
}
