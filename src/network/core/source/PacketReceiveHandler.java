package network.core.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import network.core.users.NetworkClient;

public class PacketReceiveHandler implements Runnable{
	private ObjectInputStream input;
	private NetworkStorage sk=NetworkStorage.getInstance();
	private String nick;
	private NetworkClient socket;
	private boolean end = true;
	@Override
	public void run() {
		try{
        	Object o;
        	input.readObject();
        	
        	while (end) {
        		o = input.readObject();
        		if(o instanceof MessagePacket){
        			sk.callReceiveEvent((MessagePacket) o);
        		}
        		else{
        			end = false;
        		}
        	}
        	disconnect(new IOException("EOS"));
        }
        catch(IOException e){
        	disconnect(e);
        }	
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public PacketReceiveHandler(ObjectInputStream input,NetworkClient s){
		this.input=input;
		this.socket=s;		
	}
	public PacketReceiveHandler(ObjectInputStream input,String nick){
		this.input=input;
		this.nick=nick;
	}
	public void disconnect(IOException e){
   		if(socket!=null){
   			try {
				socket.disconnect();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   			sk.callDisconnectEvent(socket.getSocket(),e);
   		}
   		else{
   			if(sk.clients.containsKey(nick)){
   				ClientInfo c = null;
   				c=sk.clients.get(nick);
				c.remove();
				sk.clients.remove(c.getNick());
				sk.callClientDisconnectEvent(c,e);
   			}	      			
   		}
	}
}
