package network.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import network.interfaces.PacketReceiveListener;

public class ConnectThread implements Runnable{
	private ServerSocket server;
	final private NetworkStorage sk=NetworkStorage.getInstance();
	private Socket client;
	private ObjectInputStream IStream;
	private ObjectOutputStream OStream;
	private int pos=0;
	private PacketReceiveListener main=new PacketReceiveListener(){
	

		@Override
		public void packetReceive(MessagePacket p) {
			if(p.getObject() instanceof String){
				if(((String) p.getObject()).equals("initial")){
					sk.receiveListeners.remove(this);
					ClientInfo c=null;
					c = new ClientInfo(client.getRemoteSocketAddress().toString(), client.getLocalPort(),p.getNick() , client,IStream,OStream);
					sk.clients.add(c);			
					sk.callClientConnectEvent(c);
				}
			}	
		}
		
	};
	@Override
	public void run() {
		while(true){
		try {
			client=server.accept();
			OStream=new ObjectOutputStream(client.getOutputStream());
			IStream=new ObjectInputStream(client.getInputStream());;
			sk.receiveListeners.add(main);
			OStream.writeObject(null);
			Thread t=new Thread(new PacketReceiveHandler(IStream,pos)); 
			pos+=1;
		    t.start();
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}
	public ConnectThread(ServerSocket s){
		this.server=s;
	}
}
