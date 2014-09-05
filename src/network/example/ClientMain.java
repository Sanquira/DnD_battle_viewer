package network.example;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import network.interfaces.ClientConnectListener;
import network.interfaces.ClientDisconnectListener;
import network.interfaces.ConnectListener;
import network.interfaces.DisconnectListener;
import network.source.ClientInfo;
import network.users.NetworkClient;

public class ClientMain {
	public static void main(String[] args) {
		NetworkClient c=new NetworkClient();
		ConnectListener t=new ConnectListener(){
			@Override
			public void Connect(Socket c) {
				System.out.print("funguje connect");				
			}			
		};
		DisconnectListener d=new DisconnectListener(){

			@Override
			public void Disconnect(Socket s) {
				System.out.print("funguje disconnect");
				
			}
			
		};
		c.addConnectListener(t);
		c.addDisconnectListener(d);
		try {
			c.connect("localhost", 1055, "Sprt");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
