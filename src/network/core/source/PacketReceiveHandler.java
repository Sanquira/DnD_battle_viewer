package network.core.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class PacketReceiveHandler implements Runnable{
	private ObjectInputStream input;
	private NetworkStorage sk=NetworkStorage.getInstance();
	private String nick;
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
       		}
       		else{
       			if(sk.clients.containsKey(nick)){
           			try{
           				ClientInfo c=sk.clients.get(nick);
           				c.getSocket().close();
           				sk.clients.remove(c.getNick());
           				sk.callClientDisconnectEvent(c);
           			}
           			catch(IOException p){
           				p.printStackTrace();
           			}
       			}	      			
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
	public PacketReceiveHandler(ObjectInputStream input,String nick){
		this.input=input;
		this.nick=nick;
	}
}
