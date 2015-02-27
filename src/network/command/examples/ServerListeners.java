package network.command.examples;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.command.annotations.CommandAnnotation;
import network.command.interfaces.CommandListener;
import network.command.source.CommandStorage;
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
	@ClientConnectAnnotation
	ClientConnectListener connect=new ClientConnectListener(){
		@Override
		public void clientConnect(ClientInfo c) {
			System.out.println("Client připojen "+(String) c.getNick());
			server.rebroadcast(c.getNick(), c.getNick(),"connect");
		}			
	};
	@ClientDisconnectAnnotation
	ClientDisconnectListener disconnect=new ClientDisconnectListener(){

		@Override
		public void clientDisconnect(ClientInfo c,IOException e) {
			System.out.println("Client odpojen: "+c.getNick()+":"+e.getMessage());
			server.rebroadcast(c.getNick(), c.getNick(),"disconnect");
		}		
	};
	@PacketReceiveAnnotation(header = "send")
	PacketReceiveListener rebroadcast=new PacketReceiveListener(){

		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println(p.getNick()+":"+(String) p.getObject());
			ClientInfo c=server.getNetworkStorage().getClientByName(p.getNick());
			server.rebroadcast(c.getNick(), p.getObject(),"broadcast");
		}
		
	};
	@CommandAnnotation(name = "broadcast",arg = CommandStorage.UNLIMITED,help ="Broadcast <Message>",usage = "Pošle zprávu všem clientům")
	CommandListener broadcast= new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			server.broadcast(server.getCommandStorage().ArgsString(0, args),"broadcast");			
		}		
	};
	CommandListener defaultsend=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			server.broadcast(server.getCommandStorage().ArgsString(0, args), "broadcast");				
		}
		
	};
	@CommandAnnotation(name = "kick",arg = 2,help = "Kick <Client> <Reason>",usage = "Vykopne hráče ze serveru")
	CommandListener kick=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			ClientInfo client = server.getNetworkStorage().getClientByName(args.get(0));
			client.send("Server","Byl jsi vykopnut ze serveru z důvodu: "+args.get(1),"announce");
			server.rebroadcast(client.getNick(), "Hráč "+client.getNick()+" byl vyhozen ze serveru z důvodu: "+args.get(1), "broadcast");
			client.remove();			
		}		
	};
	@CommandAnnotation(name = "list",arg = 0,help = "List",usage = "Zobrazí seznam hráčů")
	CommandListener list=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {	
			for(ClientInfo c:server.getNetworkStorage().clients){
				System.out.println(c.getNick()+":"+c.getHostName());
			}			
		}			
	};
	@CommandAnnotation(name = "send",min = 2,max = CommandStorage.UNLIMITED,help = "Send <Player> <Message>",usage = "Pošle hráči zprávu")
	CommandListener send=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			ClientInfo client=server.getNetworkStorage().getClientByName(args.get(0));;
			if(client!=null){
				client.send(server.getCommandStorage().ArgsString(1, args),"message");
			}						
		}		
	};
	@PacketReceiveAnnotation(header = "list")
	PacketReceiveListener listreceive=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p){
			ClientInfo asker=server.getNetworkStorage().getClientByName(p.getNick());
			List<String> names=new ArrayList<>();
			for(ClientInfo ci:server.getNetworkStorage().clients){
				names.add(ci.getNick());
			}	
			asker.send(names, "list");
		}		
	};
	public ServerListeners(CommandServer server){
		this.server=server;
		server.registerClass(this);
//		server.addReceiveListener(listreceive, "list");
//		server.addClientConnectListener(connect);
//		server.addClientDisconnectListener(disconnect);
//		server.registerCommand("broadcast",CommandStorage.UNLIMITED,"Broadcast <Message>","Pošle zprávu všem clientům",broadcast);
//		server.registerCommand("kick",2,"Kick <Client> <Reason>","Vykopne hráče ze serveru",kick);
//		server.registerCommand("list",0,"List","Zobrazí seznam hráčů",list);
//		server.registerCommand("send",2,CommandStorage.UNLIMITED,"Send <Player> <Message>","Pošle hráči zprávu", send);
		server.setDefaultCommand(defaultsend);
	}
}
