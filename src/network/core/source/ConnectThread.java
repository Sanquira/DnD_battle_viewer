package network.core.source;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectThread extends Thread{
	private ServerSocket server;
	final private NetworkStorage sk=NetworkStorage.getInstance();
	private Socket client;
	public void registerClient(ObjectInputStream IStream, ObjectOutputStream OStream) throws ClassNotFoundException, IOException{
    	MessagePacket connectMessage;
    	IStream.readObject();
    	connectMessage = (MessagePacket) IStream.readObject();
    	if(!sk.isConnected(connectMessage.getNick())){
    		ClientInfo clientInfo = new ClientInfo(connectMessage.getNick() , client,IStream,OStream);
    		if(checkNick(connectMessage.getNick())){
    			sk.clients.add(clientInfo);
    			sk.callClientConnectEvent(clientInfo);
    		}
    		else{
    			clientInfo.kick("Unsupported chars");
    		}
    	}
		else{
			OStream.writeObject(new MessagePacket(connectMessage.getNick(), "corekick", "Same nick playing"));
			OStream.flush();
			if (!client.isInputShutdown()) {
				client.shutdownInput();
			}
			if (!client.isOutputShutdown()) {
				client.shutdownOutput();
			}
			if (!client.isClosed()) {
				client.close();
			}
		}
	}
	@Override
	public void run() {
		while(true){
		try {
			client=server.accept();
			ObjectOutputStream OStream = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream IStream = new ObjectInputStream(client.getInputStream());
			OStream.writeObject(null);
			registerClient(IStream,OStream);        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}
	public boolean checkNick(String nick){
		String[] banned={"ě","š","č","ř","ž","ý","á","í","ů","ú"," "};
		for(String chars:banned){
			if(nick.contains(chars)){
				return false;
			}
		}
		return true;		
	}
	public ConnectThread(ServerSocket server){
		super("Connect Thread");
		this.server=server;
		super.start();
	}
}
