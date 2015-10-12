package hexapaper.network.server;

import hexapaper.entity.HPEntity;
import hexapaper.source.HPSklad;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import core.LangFile;
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
	private HexaServer hexa;
	private HPSklad storage=HPSklad.getInstance();
	private int gridSl,gridRa,RADIUS;
	private ConcurrentMap<Integer,HPEntity> entity;
	private ConcurrentMap<Integer,Color> colors = new ConcurrentHashMap<Integer,Color>();
	private ClientInfo PJ=null;
	private String[] allowedVersions={HPSklad.VERSION};
	//ClientConnectListeners
	@ClientConnectAnnotation
	ClientConnectListener connect=new ClientConnectListener(){
		public void clientConnect(ClientInfo c) {			
			if(PJ!=null){
				Object[] o={gridSl,gridRa,RADIUS};
				c.send(o, "RadiusHexapaper");
				sendEntities(c);
				sendColors(c);
				//PJ.send(c.getNick(),versions,"PlayerConnect");
			}
			System.out.println(LangFile.sub(storage.str.ClientConnected,"name",c.getNick()));
			server.rebroadcast(c.getNick(),null,"PlayerConnect");
			hexa.gui.addPlayer(c.getNick());
		}

		private void sendColors(ClientInfo c) {
			for(Entry<Integer, Color> entry:colors.entrySet()){
				Object[] o = {entry.getKey(),entry.getValue()};
				c.send(o, "paintEnt");
			}
			
		}

		private void sendEntities(ClientInfo cln) {
			for(Entry<Integer,HPEntity> entry:entity.entrySet()){
				Object[] o = {entry.getKey(),entry.getValue()};
				cln.send(o, "insertEnt");
			}
		}			
	};
	//ClientDisconnectListeners
	@ClientDisconnectAnnotation
	ClientDisconnectListener disconnect=new ClientDisconnectListener(){
		public void clientDisconnect(ClientInfo c,Exception e, String reason, boolean kicked) {
			System.out.println(getDisconnectMessage(c,getErrorMessage(e)));
			hexa.gui.removePlayer(c.getNick());
			server.rebroadcast(c.getNick(),null,"PlayerDisconnect");			
		}		
	};
	//ReceiveListeners
	@PacketReceiveAnnotation(header = "RadiusHexapaper")
	PacketReceiveListener RadiusHexapaper=new PacketReceiveListener(){
		public void packetReceive(MessagePacket p) {
			System.out.println(storage.str.RadiusReceived); 
			Object[] List = (Object[]) p.getObject();
			gridSl=(int) List[0];
			gridRa=(int) List[1];
			RADIUS=(int) List[2];
			entity = new ConcurrentHashMap<Integer,HPEntity>();
			server.rebroadcast(p.getNick(), List,"RadiusHexapaper");
		}		
	};
	@PacketReceiveAnnotation(header = "insertEnt")
	PacketReceiveListener insertEnt=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			//System.out.println("Test ent!");
			Object[] table=(Object[]) p.getObject();
			Integer i = (Integer) table[0];
			entity.put(i,((HPEntity) table[1]).clone());
			server.rebroadcast(p);
		}		
	};
	@PacketReceiveAnnotation(header = "paintEnt")
	PacketReceiveListener paintEnt=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			Object[] table=(Object[]) p.getObject();
			Integer pos = (Integer) table[0];
			Color clr = (Color) table[1];
			colors.put(pos, clr);
			server.rebroadcast(p);
		}		
	};
	@PacketReceiveAnnotation(header = "rotateEnt")
	PacketReceiveListener rotateEnt=new PacketReceiveListener(){

		@Override
		public void packetReceive(MessagePacket p) {
			Integer[] table=(Integer[]) p.getObject();
			//System.out.println(table[0]+":"+table[1]+":"+table[2]);
			System.out.println("rotace");
			for(HPEntity ent:entity.values()){
				if(ent.loc.getX()==table[0]&&ent.loc.getY()==table[1]){
					ent.loc.setDir(table[2]);
					//System.out.println("Předělána entita");
				}
			}
			server.rebroadcast(p);
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
				Message=LangFile.sub(storage.str.DiceRolled, map);
			}
			else{
				Message=LangFile.sub(storage.str.DiceRolledModifier, map);
			}
			hexa.gui.diceLog.addMessage(Message);
			if(PJ!=null){
				PJ.send(p);
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
			System.out.println(LangFile.sub(storage.str.ClientVersion, map));
			if(!checkVersion((String) p.getObject())){
				server.getNetworkStorage().getClientByName(p.getNick()).kick("Unsupported version");
			}
			if(PJ!=null){
				//PJ.send(versions,"versionUpdate");
			}
		}		
	};
	@PacketReceiveAnnotation(header = "clientCmd")
	PacketReceiveListener executeCmd = new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket msg) {
			//System.out.println("Přijato");
			if(msg.getNick().equals(PJ.getNick())){
				String result = server.getCommandStorage().checkCommand((String) msg.getObject(),true); 
				server.getNetworkStorage().getClientByName(msg.getNick()).send(result, "cmd");
				//System.out.println("Odeslano");
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
				//c.send((Serializable) versions, "versionUpdate");
				System.out.println(LangFile.sub(storage.str.ClientsetPJ,"name",c.getNick()));
				return;
			}
			System.out.println(LangFile.sub(storage.str.ClientNoPlayer,"name",args.get(0)));
		}		
	};
	private CommandListener isPJ=new CommandListener(){
		public void CommandExecuted(List<String> args) {
			if(PJ!=null){
				System.out.println(LangFile.sub(storage.str.ServerPJ,"name",args.get(0)));
				return;
			}
			System.out.println(storage.str.ServerNoPJ);		
		}	
	};

	private CommandListener kick=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			if(server.getNetworkStorage().isConnected(args.get(0))){
				server.getNetworkStorage().getClientByName(args.get(0)).kick(args.get(1));
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
				System.out.println("(Příkaz)"+LangFile.sub(storage.str.DiceRolled, map));
				PJ.send(args.get(2), o, "dice");
				return;
			}
			System.out.println(storage.str.ServerNoPJ);
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
	private String getDisconnectMessage(ClientInfo c, String message){
		Map<String,String> map=new HashMap<String,String>();
		map.put("name", c.getNick());
		String msg = storage.str.ClientDisconnected;
		if(c.isKicked()){
			msg = storage.str.ClientKicked;
			message = c.getReason();
		}
		map.put("error", message);
		String Player;
		Player="hráč";
		if(PJ!=null){
			if(PJ.equals(c)){
				Player="PJ";
				PJ=null;					
			}
		}
		map.put("pj", Player);
		return LangFile.sub(msg,map);
	}
	private boolean checkVersion(String version){
		for(String s:allowedVersions){
			if(s.equals(version)){
				return true;
			}
		}
		return false;
	}
	public ServerListeners(HexaServer hexa){
		this.server=hexa.server;
		this.hexa = hexa;
		server.registerClass(this);
		server.registerCommand("pj", 1, storage.str.pjUsage, storage.str.pjHelp, isPJ);
		server.registerCommand("setpj", 1, storage.str.setpjUsage, storage.str.setpjHelp, setPJ);
		server.registerCommand("kick", 2, storage.str.kickUsage, storage.str.kickHelp, kick);
		server.registerCommand("dice", 3, storage.str.diceUsage, storage.str.diceHelp, dicecmd);
		server.registerCommand("version", 1, storage.str.versionUsage, storage.str.versionHelp, version);
	}
}
