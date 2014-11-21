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
    	ClientInfo clientInfo;
    	IStream.readObject();
    	connectMessage = (MessagePacket) IStream.readObject();
		if(!sk.clients.containsKey(connectMessage.getNick())&&checkNick(connectMessage.getNick())){
	    	name=connectMessage.getNick();
			clientInfo = new ClientInfo(client.getRemoteSocketAddress().toString(), client.getLocalPort(),connectMessage.getNick() , client,IStream,OStream);
			sk.clients.put(clientInfo.getNick(),clientInfo);
			sk.callClientConnectEvent(clientInfo);
		}
		else{
			try {
				OStream.writeObject(new MessagePacket(connectMessage.getNick(),"corekick",0));
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
		this.server=server;
	}
}
