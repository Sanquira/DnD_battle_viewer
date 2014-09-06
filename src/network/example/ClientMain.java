package network.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import network.interfaces.ConnectListener;
import network.interfaces.DisconnectListener;
import network.interfaces.PacketReceiveListener;
import network.source.MessagePacket;
import network.users.NetworkClient;

public class ClientMain {
	public static void main(String[] args) {
		NetworkClient c=new NetworkClient();
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
				System.out.println("Server: "+(String) p.getObject());
								
			}
			
		};
        BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in));
		c.addConnectListener(t);
		c.addDisconnectListener(d);
		c.addReceiveListener(p);
		try {
			c.connect("localhost", 1055, "Sprt");
			String userInput;
			while ((userInput = stdIn.readLine()) != null) {
               c.send(userInput);
            }        
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
