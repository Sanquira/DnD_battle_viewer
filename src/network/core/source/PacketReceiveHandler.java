package network.core.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import network.core.users.NetworkClient;

public class PacketReceiveHandler extends Thread{
	private ObjectInputStream input;
	private NetworkStorage sk=NetworkStorage.getInstance();
	private String nick;
	private NetworkClient socket;
	@Override
	public void run() {
		try{
        	MessagePacket packet;
        	//input.readObject();
        	
        	while (!interrupted()) {
        		packet = (MessagePacket) input.readObject();
				if (packet != null) {
					sk.callReceiveEvent(packet);
					if (socket == null) {
						sk.callReceiveEvent(new MessagePacket(packet.getNick(),
								"clientCheck", null));
					} else {
						sk.callReceiveEvent(new MessagePacket(packet.getNick(),
								"serverCheck", null));
					}
				}
        	}
        	//disconnect(new IOException("EOS"));
        }
        catch(IOException e){
        	disconnect(e);
        }	
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	public PacketReceiveHandler(ObjectInputStream input,NetworkClient s){
		super("PacketReceiveHandler - Main");
		this.input=input;
		this.socket=s;
		super.start();
	}
	public PacketReceiveHandler(ObjectInputStream input,String nick){
		super("PacketReceiveHandler - "+nick);
		this.input=input;
		this.nick=nick;
		super.start();
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
   			sk.disconnectClient(nick, e);
   		}
	}
}
