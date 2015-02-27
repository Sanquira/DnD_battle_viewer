package network.command.examples;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import network.command.annotations.CommandAnnotation;
import network.command.interfaces.CommandListener;
import network.command.users.CommandClient;
import network.core.annotations.Annotations.ConnectAnnotation;
import network.core.annotations.Annotations.DisconnectAnnotation;
import network.core.annotations.Annotations.PacketReceiveAnnotation;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.MessagePacket;

public class ClientListeners {
	private CommandClient c;
	
	//ConnectListeners - Events fired when connected to server
	@ConnectAnnotation
	ConnectListener connect=new ConnectListener(){
		@Override
		public void Connect(Socket c) {
			System.out.println("Připojeno k serveru");
		}			
	};
	
	//DisconnectiIsteners - Events fired when client disconnect from server
	@DisconnectAnnotation
	DisconnectListener disconnect=new DisconnectListener(){
		@Override
		public void Disconnect(Socket s, IOException e, String reason, boolean kicked) {
			System.out.println("Odpojeno od serveru");
			System.exit(1);
			
		}			
	};
	
	//PacketReceiveListeners - Used to control receiving of packet
	@PacketReceiveAnnotation(header = "broadcast")
	PacketReceiveListener broadcast=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println(p.getNick()+":"+(String) p.getObject());
							
		}			
	};
	@PacketReceiveAnnotation(header = "list")
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
	@PacketReceiveAnnotation(header = "connect")
	PacketReceiveListener connectReceive=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println((String) p.getObject()+" se připojil k serveru");
		}		
	};
	@PacketReceiveAnnotation(header = "disconnect")
	PacketReceiveListener disconnectReceive=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println((String) p.getObject()+" se odpojil k serveru");
		}		
	};
	@PacketReceiveAnnotation(header = "announce")
	PacketReceiveListener announce=new PacketReceiveListener(){
		@Override
		public void packetReceive(MessagePacket p) {
			System.out.println((String)p.getObject());
		}		
	};
	
	//CommandListener - Event when command is executed
	@CommandAnnotation(help = "Exit", name = "exit", usage = "Ukončí program", arg = 0)
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
	@CommandAnnotation(help = "List", name = "list", usage = "Vypíše clienty", arg = 0)
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
		c.registerClass(this);
		c.setDefaultCommand(defaultsend);
//		c.registerCommand("exit",0,"Exit","Ukončí clienta",quit);
//		c.registerCommand("list",0,"List","Vypíše seznam clientů",list);
	}
}
