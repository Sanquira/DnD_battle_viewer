package hexapaper.network.server;

import hexapaper.entity.HPEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.command.interfaces.CommandListener;
import network.command.users.CommandServer;
import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.ClientInfo;
import network.core.source.MessagePacket;

public class ServerListeners {
	private CommandServer server;
	private Object[] hexapaperList=null;
	private ArrayList<HPEntity> souradky=null;
	private ArrayList<HPEntity> DBArtefact=null;
	private ArrayList<HPEntity> DBCharacter=null;
	private ClientInfo PJ=null;
	//ClientConnectListeners
	ClientConnectListener connect=new ClientConnectListener(){
		public void clientConnect(ClientInfo c) {
			System.out.println("Client připojen "+(String) c.getNick());
			if(PJ!=null){
				try {
					c.send(hexapaperList, "hexapaper");
					c.send(DBArtefact,"DBartefact");
					c.send(DBCharacter,"DBcharacter");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}			
	};
	//ClientDisconnectListeners
	ClientDisconnectListener disconnect=new ClientDisconnectListener(){
		public void clientDisconnect(ClientInfo c) {
			System.out.println("Client odpojen: "+c.getNick());
			if(PJ!=null){
				if(PJ.equals(c)){
					System.out.println("PJ se odpojil");
					PJ=null;
				}	
			}
		}		
	};
	//ReceiveListeners
	PacketReceiveListener hexapaper=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println("Hexapaper přijat"); 
			hexapaperList=(Object[]) p.getObject();
			souradky=(ArrayList<HPEntity>) hexapaperList[3];
			try {
				server.rebroadcast(p.getNick(), hexapaperList,"hexapaper");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	};
	PacketReceiveListener DBa=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println("Artefacty přijaty"); 
			DBArtefact=(ArrayList<HPEntity>) p.getObject();
			try {
				server.rebroadcast(p.getNick(), DBArtefact,"DBartefact");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	};
	PacketReceiveListener insertEnt=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Object[] table=(Object[]) p.getObject();
			souradky.set((Integer) table[0], ((HPEntity) table[1]).clone());
			try {
				server.rebroadcast(p.getNick(), p.getObject(),p.getHeader());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	};
	PacketReceiveListener DBc=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println("Postavy přijaty"); 
			DBCharacter=(ArrayList<HPEntity>) p.getObject();
			try {
				server.rebroadcast(p.getNick(), DBCharacter,"DBcharacter");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	};
	PacketReceiveListener rotateEnt=new PacketReceiveListener(){

		@Override
		public void packetReceive(MessagePacket p) {
			Integer[] table=(Integer[]) p.getObject();
			System.out.println(table[0]+":"+table[1]+":"+table[2]);
			for(HPEntity ent:souradky){
				if(ent.loc.getX()==table[0]&&ent.loc.getY()==table[1]){
					ent.loc.setDir(table[2]);
					System.out.println("Předělána entita");
				}
			}
			try {
				server.rebroadcast(p.getNick(), p.getObject(),"rotateEnt");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	};
	//CommandListeners
	private CommandListener setPJ=new CommandListener(){
		public void CommandExecuted(List<String> args) {
			ClientInfo c=server.getNetworkStorage().getClientByName(args.get(0));
			if (c!=null){
				try {
					if(PJ!=null){
						PJ.send(0, "removePJ");
					}	
					PJ=c;
					c.send(0, "requestPJInfo");
					System.out.println("Hráči " +c.getNick()+" byl nastaven PJ");					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			System.out.println("Player "+args.get(0)+" is not connected");
		}		
	};
	private CommandListener isPJ=new CommandListener(){
		public void CommandExecuted(List<String> args) {
			if(PJ!=null){
				System.out.println("PJ is "+PJ.getNick());
				return;
			}
			System.out.println("PJ is not defined");		
		}	
	};
	public ServerListeners(CommandServer s){
		this.server=s;
		s.addClientConnectListener(connect);
		s.addClientDisconnectListener(disconnect);
		s.addReceiveListener(hexapaper, "hexapaper");
		s.addReceiveListener(DBa, "DBartefact");
		s.addReceiveListener(DBc, "DBcharacter");
		s.addReceiveListener(rotateEnt, "rotateEnt");
		s.addReceiveListener(insertEnt, "insertEnt");
		s.registerCommand("pj", 1, "pj <Name>", "Check if player is PJ", isPJ);
		s.registerCommand("setpj", 1, "setpj <Name>", "Set PJ", setPJ);
	}
}
