package network.core.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectThread implements Runnable{
	private ServerSocket server;
	final private NetworkStorage sk=NetworkStorage.getInstance();
	private Socket client;
	private ObjectInputStream IStream;
	private ObjectOutputStream OStream;
	private String name;
	public void registerClient(ObjectInputStream IStream) throws ClassNotFoundException, IOException{
    	MessagePacket connectMessage;
    	IStream.readObject();
    	connectMessage = (MessagePacket) IStream.readObject();
		if(!sk.clients.containsKey(connectMessage.getNick())){
	    	name=connectMessage.getNick();
			ClientInfo c = new ClientInfo(client.getRemoteSocketAddress().toString(), client.getLocalPort(),connectMessage.getNick() , client,IStream,OStream);
			sk.clients.put(c.getNick(),c);
			sk.callClientConnectEvent(c);
		}
		else{
			try {
				OStream.close();
				IStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void run() {
		while(true){
		try {
			client=server.accept();
			OStream=new ObjectOutputStream(client.getOutputStream());
			IStream=new ObjectInputStream(client.getInputStream());
			OStream.writeObject(null);
			registerClient(IStream);
			Thread t=new Thread(new PacketReceiveHandler(IStream,name)); 
			t.setName("PacketReceiveHandler-"+name);
			t.start();            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}
	public ConnectThread(ServerSocket s){
		this.server=s;
	}
}
