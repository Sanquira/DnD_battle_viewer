package hexapaper.network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import hexapaper.entity.HPEntity;
import hexapaper.gui.HraciPlocha;
import hexapaper.source.HPSklad;
import network.command.interfaces.CommandListener;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.MessagePacket;

public class ClientListeners {
	private HexaClient hexaClient;
	private HPSklad storage;
	//Connect Listeners
	private ConnectListener cnt=new ConnectListener(){
		public void Connect(Socket c) {
			System.out.println("Připojeno k serveru");
			storage.isConnected=true;
			storage.client=hexaClient;
		}		
	};
	//Disconnect Listeners
	private DisconnectListener dsc=new DisconnectListener(){
		public void Disconnect(Socket s) {
			storage.isConnected=false;
			storage.isPJ=false;
			storage.updateConnect();
			JOptionPane.showMessageDialog(storage.hraciPlocha,
				    storage.str.get("DisconnectMessage"),
				    storage.str.get("DisconnectWindow"),
				    JOptionPane.WARNING_MESSAGE);	
		}		
	};	
	//Receive Listeners
	private PacketReceiveListener RadiusHexapaper=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			storage.setStatus("Přijímání rozsahu HexaPapaeru");
			//System.out.println("Received hexapaper Radiuses from server");
			Object[] paper=(Object[]) p.getObject();
			storage.gridRa=(int) paper[0];
			storage.gridSl=(int) paper[1];
			storage.RADIUS=(int) paper[2];
			storage.hraciPlocha=new HraciPlocha();
			storage.setStatus("Rozměry HexaPapaperu přijaty");
		}		
	};
	private PacketReceiveListener EntityHexapaper=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			storage.setStatus("Přijímání entit HexaPaperu");
			storage.souradky = (ArrayList<HPEntity>) p.getObject();
			storage.initLoad(storage.souradky);
			storage.setStatus("HexaPaperu načten ze serveru");
		}		
	};
	private PacketReceiveListener rotateEnt=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			Integer[] table=(Integer[]) p.getObject();
			System.out.println(table[0]+":"+table[1]+":"+table[2]);
			for(HPEntity ent:storage.souradky){
				if(ent.loc.getX().equals(table[0])&&ent.loc.getY().equals(table[1])){
					System.out.println("cool");
					ent.loc.setDir(table[2]);
					//System.out.println("Předělána entita");
					storage.hraciPlocha.repaint();
				}
			}
		}		
	};	
	private PacketReceiveListener DBa=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			//System.out.println("Databaze Artefaktu updatnuta");
			storage.databazeArtefaktu=(ArrayList<HPEntity>) p.getObject();
			storage.RMenu.updateDatabase();
		}		
	};
	private PacketReceiveListener DBc=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			//System.out.println("Databaze Postav updatnuta");
			storage.databazePostav=(ArrayList<HPEntity>) p.getObject();
			storage.RMenu.updateDatabase();
		}		
	};
	private PacketReceiveListener requestPJInfo=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			storage.isPJ=true;
			storage.updateConnect();
			//System.out.println("Requested PJ info");
			hexaClient.radiusHexapaper();
			hexaClient.updateHexapaper();
			hexaClient.updateDatabase();
			storage.setStatus("Nastaven PJ");
		}		
	};
	private PacketReceiveListener removePJ=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			storage.isPJ=false;
			storage.PJInfo.setVisible(false);
			//System.out.println("No longer PJ");
			storage.updateConnect();
			storage.setStatus("Odebrán PJ");
		}		
	};
	PacketReceiveListener insertEnt=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			//System.out.println("Vložena entita");
			Object[] table=(Object[]) p.getObject();
			storage.hraciPlocha.insertEntity((int) table[0], ((HPEntity) table[1]), true);
			storage.setStatus("Získáná entita");
		}		
	};
	PacketReceiveListener EntChangeName=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Object[] table=(Object[]) p.getObject();
			//System.out.println("přijato"+(Integer) table[0]+","+(Integer) table[1]);
			//System.out.println(table[0]+":"+table[1]+":"+table[2]+":"+table[3]);
			for(HPEntity ent:storage.souradky){
				if(ent.loc.getX().equals((Integer) table[0])&&ent.loc.getY().equals((Integer) table[1])){
					ent.setTag((String) table[2]);
					storage.hraciPlocha.repaint();
					//System.out.println("Změnen nick a tag Entity");
				}
			}
		}		
	};
	PacketReceiveListener kick=new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			JOptionPane.showMessageDialog(storage.hraciPlocha,
				    storage.str.get("KickMessage")+(String) p.getObject(),
				    storage.str.get("KickWindow"),
				    JOptionPane.WARNING_MESSAGE);
			storage.setStatus("Vyhozen ze serveru");
		}		
	};
	PacketReceiveListener dice=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Integer roll=((Integer[]) p.getObject())[0];
			Integer range=((Integer[]) p.getObject())[1];
			Integer modifier=((Integer[]) p.getObject())[2];
			String message;
			if(modifier==0){
				message=p.getNick()+" si hodil "+roll+" na "+range+" kostce.";
			}
			else{
				message=p.getNick()+" si hodil "+(roll+modifier)+" na "+range+" kostce se základním hodem "+roll;
			}
			//String message=p.getNick()+" si hodil "+(roll+modifier)+" na "+range+" kostce se základním hodem "+roll;
			System.out.println(message);
			storage.getDiceLog().addMessage(message);
		}		
	};
	PacketReceiveListener version=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			try {
				hexaClient.send(storage.VERSION, "version");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			storage.setStatus("Server vyžádal vaši verzi");
		}		
	};
	PacketReceiveListener PConnect=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			String Message=p.getNick()+" se připojil na server";
			System.out.println(Message);
			storage.setStatus(Message);
			storage.getPJLog().addMessage(Message);
		}		
	};
	PacketReceiveListener PDisconnect=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			String Message=p.getNick()+" se odpojil z serveru";
			System.out.println(Message);
			storage.setStatus(Message);
			storage.getPJLog().addMessage(Message);
		}		
	};	
	CommandListener oclose=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			try {
				storage.client.getOutputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	};
	CommandListener iclose=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			try {
				storage.client.getInputStream().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	};
	public ClientListeners(HexaClient hexaClient, HPSklad storage) {
		this.hexaClient=hexaClient;
		this.storage=storage;
		hexaClient.addConnectListener(cnt);
		hexaClient.addDisconnectListener(dsc);
		hexaClient.addReceiveListener(DBa,"DBartefact");
		hexaClient.addReceiveListener(DBc,"DBcharacter");
		hexaClient.addReceiveListener(RadiusHexapaper,"RadiusHexapaper");
		hexaClient.addReceiveListener(EntityHexapaper,"EntityHexapaper");
		hexaClient.addReceiveListener(requestPJInfo,"requestPJInfo");
		hexaClient.addReceiveListener(dice,"dice");
		hexaClient.addReceiveListener(PConnect,"PlayerConnect");
		hexaClient.addReceiveListener(PDisconnect,"PlayerDisconnect");
		hexaClient.addReceiveListener(removePJ,"removePJ");
		hexaClient.addReceiveListener(rotateEnt,"rotateEnt");
		hexaClient.addReceiveListener(insertEnt,"insertEnt");
		hexaClient.addReceiveListener(EntChangeName,"EntChangeTag");
		hexaClient.addReceiveListener(kick, "kick");
		hexaClient.addReceiveListener(version, "version");
		hexaClient.registerCommand("iclose", 0, "", "", iclose);
		hexaClient.registerCommand("oclose", 0, "", "", oclose);
	}
}
