package hexapaper.network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import hexapaper.entity.HPEntity;
import hexapaper.source.HPSklad;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.ClientInfo;
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
	//Receive Listeners
	private PacketReceiveListener hexaPaper=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println("Received hexapaper from server");
			Object[] paper=(Object[]) p.getObject();
			storage.gridRa=(int) paper[0];
			storage.gridSl=(int) paper[1];
			storage.RADIUS=(int) paper[2];
			storage.souradky= (ArrayList<HPEntity>) paper[3];
			storage.initLoad(storage.souradky);
		}		
	};
	private PacketReceiveListener rotateEnt=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			Integer[] table=(Integer[]) p.getObject();
			System.out.println(table[0]+":"+table[1]+":"+table[2]);
			for(HPEntity ent:storage.souradky){
				if(ent.loc.getX().equals(table[0])&&ent.loc.getY().equals(table[1])){
					ent.loc.setDir(table[2]);
					System.out.println("Předělána entita");
					ent.recreateGraphics();
				}
			}
		}		
	};
	private PacketReceiveListener DBa=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println("Databaze Artefaktu updatnuta");
			storage.databazeArtefaktu=(ArrayList<HPEntity>) p.getObject();
			storage.RMenu.updateDatabase();
		}		
	};
	private PacketReceiveListener DBc=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println("Databaze Postav updatnuta");
			storage.databazePostav=(ArrayList<HPEntity>) p.getObject();
			storage.RMenu.updateDatabase();
		}		
	};
	private PacketReceiveListener requestPJInfo=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			storage.PJ=true;
			System.out.println("Requested PJ info");
			hexaClient.updateHexapaper();
			hexaClient.updateDatabase();
		}		
	};
	private PacketReceiveListener removePJ=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			storage.PJ=false;
			System.out.println("No longer PJ");
		}		
	};
	PacketReceiveListener insertEnt=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println("Vložena entita");
			Object[] table=(Object[]) p.getObject();
			storage.hraciPlocha.insertEntity((int) table[0], ((HPEntity) table[1]), true);
		}		
	};
	public ClientListeners(HexaClient hexaClient, HPSklad storage) {
		this.hexaClient=hexaClient;
		this.storage=storage;
		hexaClient.addConnectListener(cnt);
		hexaClient.addReceiveListener(DBa,"DBartefact");
		hexaClient.addReceiveListener(DBc,"DBcharacter");
		hexaClient.addReceiveListener(hexaPaper,"hexapaper");
		hexaClient.addReceiveListener(requestPJInfo,"requestPJInfo");
		hexaClient.addReceiveListener(rotateEnt,"rotateEnt");
		hexaClient.addReceiveListener(insertEnt,"insertEnt");
	}
}
