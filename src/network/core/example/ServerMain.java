package network.core.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.ClientInfo;
import network.core.source.MessagePacket;
import network.core.users.NetworkServer;


public class ServerMain {

	public static void main(String[] args) {
		final NetworkServer s=new NetworkServer();
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
				try {
					s.broadcast(p.getNick()+":"+(String) p.getObject());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
		s.addClientConnectListener(t);
		s.addClientDisconnectListener(d);
		try {
			s.create("localhost",1055);
			s.addReceiveListener(p,"re-broadcast");
            String userInput;
			while ((userInput = stdIn.readLine()) != null) {
                s.broadcast(userInput);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
