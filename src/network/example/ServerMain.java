package network.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import network.interfaces.ClientConnectListener;
import network.interfaces.ClientDisconnectListener;
import network.interfaces.PacketReceiveListener;
import network.source.ClientInfo;
import network.source.MessagePacket;
import network.users.NetworkServer;


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
			}
			
		};
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
		s.addClientConnectListener(t);
		s.addClientDisconnectListener(d);
		s.addReceiveListener(p);
		try {
			s.create(1055);
            String userInput;
			while ((userInput = stdIn.readLine()) != null) {
                s.broadcast(userInput);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
