package network.command.examples;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import network.command.interfaces.CommandListener;
import network.command.users.CommandClient;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.MessagePacket;


public class CmdClientMain {

	public static void main(String[] args) throws IOException {
		final CommandClient c=new CommandClient();
		ConnectListener t=new ConnectListener(){
			@Override
			public void Connect(Socket c) {
				System.out.println("Připojeno k serveru");
			}			
		};
		DisconnectListener d=new DisconnectListener(){

			@Override
			public void Disconnect(Socket s) {
				System.out.println("Odpojeno od serveru");
				System.exit(1);
				
			}
			
		};
		PacketReceiveListener p=new PacketReceiveListener(){

			@Override
			public void packetReceive(MessagePacket p) {
				System.out.println(p.getNick()+":"+(String) p.getObject());
								
			}
			
		};
		c.addConnectListener(t);
		c.addDisconnectListener(d);
		c.addReceiveListener(p);
		try {
			c.connect("localhost", 1055, args[0]);      
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.registerCommand("send",Integer.MIN_VALUE,"Send <Message> <ID>", new CommandListener(){

			@Override
			public void CommandExecuted(List<String> args) {
				try {					
					for(String s:args){
						c.send(s);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	});
		c.registerCommand("quit",0,"Send <Message> <ID>", new CommandListener(){

			@Override
			public void CommandExecuted(List<String> args) {
				try {					
					c.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	});
	}
}
