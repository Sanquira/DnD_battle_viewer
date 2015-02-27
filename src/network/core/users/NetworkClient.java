package network.core.users;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import network.core.annotations.AnnotationChecker;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.source.DefaultListeners;
import network.core.source.MessagePacket;
import network.core.source.PacketReceiveHandler;
import network.core.source.ClientSyncThread;

public class NetworkClient extends AbstractNetworkUser{
    private Socket socket=null;
    private String nick;
    private PacketReceiveHandler PacketReceiveThread;
    private ClientSyncThread ClientSyncThread;
    private ObjectOutputStream o;
    private ObjectInputStream i;

    public void addDisconnectListener(DisconnectListener l){
    	sk.disconnectListeners.add(l);
    }
    public void addConnectListener(ConnectListener l){
    	sk.connectListeners.add(l);
    }
    public void addKickListener(PacketReceiveListener l){
    	addReceiveListener(l,"corekick");
    }
    public NetworkClient(){
    	socket = new Socket();
    	sk.reset();
    	super.start();
    }
    public ObjectOutputStream getOutputStream(){
    	return o;
    }
    public ObjectInputStream getInputStream(){
    	return i;
    }
    public void connect(int timeout, String host, int port, String nick) throws IOException,UnknownHostException{
    	socket.connect(new InetSocketAddress(host, port), 1000);
    	this.nick=nick;
    	i=new ObjectInputStream(socket.getInputStream());
    	o=new ObjectOutputStream(socket.getOutputStream());
    	PacketReceiveThread = new PacketReceiveHandler(i,this);
    	o.writeObject(null);
    	o.writeObject(new MessagePacket(nick,"connect", null));
    	o.writeObject(null); 
    	new DefaultListeners(this);
    	ClientSyncThread = new ClientSyncThread(timeout,this);
    	sk.callConnectEvent(socket);
    }
    public void connect(String host, int port, String nick) throws UnknownHostException, IOException{
    	connect(sk.defaultclientTimeout,host,port,nick);
    }
    public void close() throws IOException{
    	socket.close();
    }
    public Socket getSocket(){
    	return socket;
    }
    public void send(Object ob) throws IOException{
    	try{
    		o.writeObject(new MessagePacket(nick, ob));
    		o.flush();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		disconnect();
    	}
    }
    public void send(Object ob,String header) throws IOException{
    	try{
    		if(!socket.isOutputShutdown()){
    			o.writeObject(new MessagePacket(nick,"check", 1));
    			o.writeObject(new MessagePacket(nick,header, ob));
    			o.flush();
    		}
    	}
    	catch(IOException e){
    		e.printStackTrace();
    		disconnect();
    	}	       	
    }
    public void disconnect() throws IOException{
    	if(socket.isConnected()){
    		if(!socket.isInputShutdown()){
    			socket.shutdownInput();
    		}
    		if(!socket.isOutputShutdown()){
    			socket.shutdownOutput();
    		}	
    		if(!socket.isClosed()){
    			socket.close();
    		}
    		PacketReceiveThread.interrupt();
    	}
    	this.interrupt();
    	ClientSyncThread.interrupt();
    	//socket.close();    	    	
    }
    public void registerClass(Object obj){
    	try {
			new AnnotationChecker(obj,this).processClass();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}


