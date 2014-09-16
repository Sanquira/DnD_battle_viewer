package network.command.examples;

import java.io.IOException;
import java.util.List;

import network.command.interfaces.CommandListener;
import network.command.source.CommandStorage;
import network.command.users.CommandServer;
import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.ClientInfo;
import network.core.source.MessagePacket;


public class CmdServerMain {

	public static void main(String[] args) throws IOException {
		final CommandServer s=new CommandServer();
		ClientConnectListener t=new ClientConnectListener(){
			@Override
			public void clientConnect(ClientInfo c) {
				System.out.println("Client připojen "+(String) c.getNick());			
			}			
		};
		ClientDisconnectListener d=new ClientDisconnectListener(){

			@Override
			public void clientDisconnect(ClientInfo c) {
				System.out.println("Client odpojen: "+c.getNick());
				
			}
			
		};
		PacketReceiveListener p=new PacketReceiveListener(){

			@Override
			public void packetReceive(MessagePacket p) {
				System.out.println(p.getNick()+":"+(String) p.getObject());
				ClientInfo c=s.sk.getClientByName(p.getNick());
				try {
					for(ClientInfo ci:s.sk.clients){
						if(!(ci.getNick()==c.getNick())){
							ci.send(p.getNick(),p.getObject());
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		s.addClientConnectListener(t);
		s.addClientDisconnectListener(d);
		s.create(args[0],1055);
		s.addReceiveListener(p);
		s.registerCommand("broadcast",CommandStorage.UNLIMITED,"Broadcast <Message>", new CommandListener(){

				@Override
				public void CommandExecuted(List<String> args) {
					try {
						String b = "";
						for(String x:args){
							b+=" "+x;
						}
						s.broadcast(b);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
		});
		s.registerCommand("send",2,CommandStorage.UNLIMITED,"Send <Message> <ID>", new CommandListener(){

			@Override
			public void CommandExecuted(List<String> args) {
				try {					
					ClientInfo client=s.sk.clients.get(Integer.valueOf(args.get(0)));
					String b = "";
					for(String x:args){
						b+=" "+x;
					}
					client.send(b);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	});
		s.registerCommand("kick",1,"Send <Message> <ID>", new CommandListener(){
			@Override
			public void CommandExecuted(List<String> args) {
				try {					
					ClientInfo ci = s.sk.getClientByName(args.get(0));
					ci.kick();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	});

	}
}
