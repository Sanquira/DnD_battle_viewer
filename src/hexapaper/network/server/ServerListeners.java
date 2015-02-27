package hexapaper.network.server;

import hexapaper.entity.FreeSpace;
import hexapaper.entity.HPEntity;
import hexapaper.source.HPSklad;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import core.Grids;
import core.Location;
import network.command.annotations.CommandAnnotation;
import network.command.interfaces.CommandListener;
import network.command.users.CommandServer;
import network.core.annotations.Annotations.ClientConnectAnnotation;
import network.core.annotations.Annotations.ClientDisconnectAnnotation;
import network.core.annotations.Annotations.PacketReceiveAnnotation;
import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.ClientInfo;
import network.core.source.MessagePacket;

public class ServerListeners {
	private CommandServer server;
	private HPSklad storage=HPSklad.getInstance();
	private int gridSl,gridRa,RADIUS;
	private CopyOnWriteArrayList<HPEntity> souradky=null;
	private ConcurrentMap<String,String> versions=new ConcurrentHashMap<String,String>();
	private ClientInfo PJ=null;
	//ClientConnectListeners
	@ClientConnectAnnotation
	ClientConnectListener connect=new ClientConnectListener(){
		public void clientConnect(ClientInfo c) {			
			if(PJ!=null){
				Object[] o={gridSl,gridRa,RADIUS};
				c.send(o, "RadiusHexapaper");
				c.send(new ArrayList<HPEntity>((CopyOnWriteArrayList<HPEntity>) souradky.clone()), "EntityHexapaper");
				PJ.send(c.getNick(),versions,"PlayerConnect");
			}
			System.out.println(storage.str.sub("ClientConnected","name",c.getNick()));
			//server.rebroadcast(c.getNick(),versions,"PlayerConnect");
		}			
	};
	//ClientDisconnectListeners
	@ClientDisconnectAnnotation
	ClientDisconnectListener disconnect=new ClientDisconnectListener(){
		public void clientDisconnect(ClientInfo c,IOException e) {
			System.out.println(getDisconnectMessage(c,e.getMessage()));
			server.rebroadcast(c.getNick(),versions,"PlayerDisconnect");			
		}		
	};
	//ReceiveListeners
	@PacketReceiveAnnotation(header = "RadiusHexapaper")
	PacketReceiveListener RadiusHexapaper=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println(storage.str.get("RadiusReceived")); 
			Object[] List = (Object[]) p.getObject();
			gridSl=(int) List[0];
			gridRa=(int) List[1];
			RADIUS=(int) List[2];
			souradky = genGrid(gridSl,gridRa,RADIUS);
			server.rebroadcast(p.getNick(), List,"RadiusHexapaper");
		}		
	};
	@PacketReceiveAnnotation(header = "EntityHexapaper")
	PacketReceiveListener EntityHexapaper=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println(storage.str.get("EntityReceived")); 
			souradky=new CopyOnWriteArrayList<HPEntity>((ArrayList<HPEntity>) p.getObject());
			server.rebroadcast(p.getNick(), new ArrayList<HPEntity>(souradky),"EntityHexapaper");
		}		
	};
	@PacketReceiveAnnotation(header = "EntChangeTag")
	PacketReceiveListener EntChangeName=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Object[] table=(Object[]) p.getObject();
			System.out.println(table[0]+":"+table[1]+":"+table[2]);
			for(HPEntity ent:souradky){
				if(ent.loc.getX()==(Integer) table[0]&&ent.loc.getY()==(Integer) table[1]){
					ent.setTag((String) table[2]);
					System.out.println("Změnen nick a tag Entity");
				}
			}
			server.rebroadcast(p.getNick(), p.getObject(),"EntChangeTag");
		}		
	};
	@PacketReceiveAnnotation(header = "insertEnt")
	PacketReceiveListener insertEnt=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			//System.out.println("Test ent!");
			Object[] table=(Object[]) p.getObject();
			Integer i = (Integer) table[0];
			if(i<souradky.size()){
				souradky.set((Integer) table[0], ((HPEntity) table[1]).clone());
				server.rebroadcast(p.getNick(), p.getObject(),p.getHeader());
			}
		}		
	};
	@PacketReceiveAnnotation(header = "paintEnt")
	PacketReceiveListener paintEnt=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Object[] table=(Object[]) p.getObject();
			if((Integer) table[0]<souradky.size()){
				souradky.get((Integer) table[0]).setBcg((Color) table[1]);
				server.rebroadcast(p.getNick(), p.getObject(),p.getHeader());
			}
		}		
	};
	@PacketReceiveAnnotation(header = "rotateEnt")
	PacketReceiveListener rotateEnt=new PacketReceiveListener(){

		@Override
		public void packetReceive(MessagePacket p) {
			Integer[] table=(Integer[]) p.getObject();
			//System.out.println(table[0]+":"+table[1]+":"+table[2]);
			for(HPEntity ent:souradky){
				if(ent.loc.getX()==table[0]&&ent.loc.getY()==table[1]){
					ent.loc.setDir(table[2]);
					//System.out.println("Předělána entita");
				}
			}
			server.rebroadcast(p.getNick(), p.getObject(),"rotateEnt");
		}
		
	};
	@PacketReceiveAnnotation(header = "dice")
	PacketReceiveListener dice=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Integer roll=((Integer[]) p.getObject())[0];
			Integer range=((Integer[]) p.getObject())[1];
			Integer modifier=((Integer[]) p.getObject())[2];
			Map<String,String> map=new HashMap<String,String>();
			map.put("name",p.getNick());
			map.put("roll",String.valueOf(roll+modifier));
			map.put("range",String.valueOf(range));
			map.put("modifier",String.valueOf(modifier));
			String Message;
			if(modifier==0){
				Message=storage.str.sub("DiceRolled", map);
			}
			else{
				Message=storage.str.sub("DiceRolledModifier", map);
			}
			System.out.println(Message);
			if(PJ!=null){
				PJ.send(p.getNick(), p.getObject(), "dice");
			}
		}		
	};
	@PacketReceiveAnnotation(header = "version")
	PacketReceiveListener versionReceive=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Map<String,String> map=new HashMap<String,String>();
			map.put("name",p.getNick());
			map.put("version",(String) p.getObject());
			versions.put(p.getNick(),(String) p.getObject());
			System.out.println(storage.str.sub("ClientVersion", map));
			if(PJ!=null){
				//PJ.send(versions,"versionUpdate");
			}
		}		
	};
	//CommandListeners
	private CommandListener setPJ=new CommandListener(){
		public void CommandExecuted(List<String> args) {
			ClientInfo c=server.getNetworkStorage().getClientByName(args.get(0));
			if (c!=null){
				if(PJ!=null){
					PJ.send(0, "removePJ");
				}	
				PJ=c;
				c.send(0, "requestPJInfo");
				System.out.println(storage.str.sub("ClientsetPJ","name",c.getNick()));
				return;
			}
			System.out.println(storage.str.sub("ClientNoPlayer","name",args.get(0)));
		}		
	};
	private CommandListener isPJ=new CommandListener(){
		public void CommandExecuted(List<String> args) {
			if(PJ!=null){
				System.out.println(storage.str.sub("ServerPJ","name",args.get(0)));
				return;
			}
			System.out.println(storage.str.get("ServerNoPJ"));		
		}	
	};

	private CommandListener kick=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			if(server.getNetworkStorage().isConnected(args.get(0))){
				server.getNetworkStorage().kick(args.get(0), args.get(1));
				System.out.println(getDisconnectMessage(server.getNetworkStorage().getClientByName(args.get(0)),"Vyhozen"));
			}
			//System.out.println("Player is not connected");
		}		
	};
	private CommandListener dicecmd=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			Map<String,String> map=new HashMap<String,String>();
			map.put("name",args.get(2));
			map.put("roll",args.get(0));
			map.put("range", args.get(1));
			Integer[] o = {(Integer) Integer.valueOf(args.get(0)),(Integer) Integer.valueOf(args.get(1)),0};
			if(PJ!=null){
				System.out.println("(Příkaz)"+storage.str.sub("DiceRolled", map));
				PJ.send(args.get(2), o, "dice");
				return;
			}
			System.out.println(storage.str.get("ServerNoPJ"));
		}		
	};
	private CommandListener version=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			ClientInfo c=server.getNetworkStorage().getClientByName(args.get(0));
			if(c!=null){
				c.send(0, "version");
			}
		}		
	};
	@CommandAnnotation(help = "Zorazí hráče", name = "list", usage = "list", arg = 0)
	private CommandListener list=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			System.out.println("Seznam clientů:");
			for(ClientInfo ci:server.getNetworkStorage().clients){
				System.out.println(ci.getNick()+",");
				if(ci.getNick().equalsIgnoreCase("Sprt")){
					System.out.println("true");
				}
			}
			//System.out.println(server.getNetworkStorage().getClientByName("Sprt")==null);
		}		
	};
	private String getErrorMessage(Exception e){
		if(e.getMessage()!=null){
			return e.getMessage();
		}
		else{
			return "Exit";
		}
	}
	private CopyOnWriteArrayList<HPEntity> genGrid(int sloupcu, int radku,int radius) {
		CopyOnWriteArrayList<HPEntity> grid = new CopyOnWriteArrayList<HPEntity>();
		int[][] souradky = Grids.gridHexa(sloupcu, radku, radius);
		for (int i = 0; i < souradky.length; i++) {
			souradky[i][0] += radius;
			souradky[i][1] += Math.round(radius * Math.cos(Math.toRadians(30)));
			grid.add(new FreeSpace(new Location(souradky[i][0], souradky[i][1], 0)));
		}
		return grid;
	}
	private String getDisconnectMessage(ClientInfo c, String message){
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", c.getNick());
		map.put("error", message);
		String Message;
		Message="hráč";
		if(PJ!=null){
			if(PJ.equals(c)){
				Message="PJ";
				PJ=null;					
			}
		}
		map.put("pj", Message);
		return storage.str.sub("ClientDisconnected",map);
	}
	public ServerListeners(CommandServer s){
		this.server=s;
		server.registerClass(this);
		s.registerCommand("pj", 1, storage.str.get("pjUsage"), storage.str.get("pjHelp"), isPJ);
		s.registerCommand("setpj", 1, storage.str.get("setpjUsage"), storage.str.get("setpjHelp"), setPJ);
		s.registerCommand("kick", 2, storage.str.get("kickUsage"), storage.str.get("kickHelp"), kick);
		s.registerCommand("dice", 3, storage.str.get("diceUsage"), storage.str.get("diceHelp"), dicecmd);
		s.registerCommand("version", 1, storage.str.get("versionUsage"), storage.str.get("versionHelp"), version);
	}
}
