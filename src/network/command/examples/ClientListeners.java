package network.command.examples;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import network.command.interfaces.CommandListener;
import network.command.users.CommandClient;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.MessagePacket;

public class ClientListeners {
	private CommandClient c;
	
	//ConnectListeners - Events fired when connected to server
	
	ConnectListener connect=new ConnectListener(){
		@Override
		public void Connect(Socket c) {
			System.out.println("Připojeno k serveru");
		}			
	};
	
	//DisconnectiIsteners - Events fired when client disconnect from server
	
	DisconnectListener disconnect=new DisconnectListener(){
		@Override
		public void Disconnect(Socket s) {
			System.out.println("Odpojeno od serveru");
			System.exit(1);
			
		}			
	};
	
	//PacketReceiveListeners - Used to control receiving of packet
	
	PacketReceiveListener broadcast=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println(p.getNick()+":"+(String) p.getObject());
							
		}			
	};
	PacketReceiveListener listReceive=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			@SuppressWarnings("unchecked")
			List<String> list=(List<String>) p.getObject();
			System.out.println("Připojení clienti:");
			for(String name:list){
				System.out.println(name);
			}
			System.out.println("==================");
		}		
	};
	PacketReceiveListener connectReceive=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println((String) p.getObject()+" se připojil k serveru");
		}		
	};
	PacketReceiveListener disconnectReceive=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println((String) p.getObject()+" se odpojil k serveru");
		}		
	};
	PacketReceiveListener announce=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println((String)p.getObject());
		}		
	};
	
	//CommandListener - Event when command is executed
	
	CommandListener quit=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			try {					
				c.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}			
	};
	CommandListener defaultsend=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			try {					
				c.send(c.getCommandStorage().ArgsString(0, args), "send");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}			
	};
	CommandListener list=new CommandListener(){
		@Override
		public void CommandExecuted(List<String> args) {
			try {
				c.send(null, "list");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	};
	public ClientListeners(CommandClient c){
		this.c=c;
		c.addConnectListener(connect);
		c.addDisconnectListener(disconnect);
		c.addReceiveListener(broadcast,"broadcast");
		c.addReceiveListener(listReceive,"list");
		c.addReceiveListener(connectReceive,"connect");
		c.addReceiveListener(disconnectReceive,"disconnect");
		c.addReceiveListener(announce,"announce");
		c.setDefaultCommand(defaultsend);
		c.registerCommand("exit",0,"Exit","Ukončí clienta",quit);
		c.registerCommand("list",0,"List","Vypíše seznam clientů",list);
	}
}
