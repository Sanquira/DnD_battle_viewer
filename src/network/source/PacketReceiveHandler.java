package network.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class PacketReceiveHandler implements Runnable{
	private ObjectInputStream input;
	private NetworkStorage sk=NetworkStorage.getInstance();
	private int pos=0;
	private Socket socket;
	@Override
	public void run() {
		try{
        	MessagePacket inputLine;
        	input.readObject();
        	while ((inputLine = (MessagePacket) input.readObject()) != null) {
        		sk.callReceiveEvent(inputLine);
        	}
        }
        catch(IOException e){
       		if(socket!=null){
       			sk.callDisconnectEvent(socket);
       			System.out.println("Odpojen od serveru");
       		}
       		else{
       			ClientInfo c=sk.clients.get(pos);
       			sk.callClientDisconnectEvent(c);
       			System.out.println("Client Odpojen: "+c.getNick());
       		}
        }
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public PacketReceiveHandler(ObjectInputStream input,Socket s){
		this.input=input;
		this.socket=s;		
	}
	public PacketReceiveHandler(ObjectInputStream input,int pos){
		this.input=input;
		this.pos=pos;
	}
}
