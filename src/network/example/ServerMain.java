package network.example;

import java.io.IOException;

import network.interfaces.ClientConnectListener;
import network.interfaces.ClientDisconnectListener;
import network.source.ClientInfo;
import network.users.NetworkServer;


public class ServerMain {

	public static void main(String[] args) {
		NetworkServer s=new NetworkServer();
		ClientConnectListener t=new ClientConnectListener(){
			@Override
			public void clientConnect(ClientInfo c) {
				System.out.print("funguje connect");				
			}			
		};
		ClientDisconnectListener d=new ClientDisconnectListener(){

			@Override
			public void clientDisconnect(ClientInfo c) {
				System.out.print("funguje disconnect");
				
			}
			
		};
		s.addClientConnectListener(t);
		s.addClientDisconnectListener(d);
		try {
			s.create(1055);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
