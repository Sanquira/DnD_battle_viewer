package network.core.source;

import java.net.Socket;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;

public class NetworkStorage {
	private static NetworkStorage instance;
	public ConcurrentMap<String,ClientInfo> clients=new ConcurrentHashMap<String,ClientInfo>();
	public ConcurrentMap<String,CopyOnWriteArrayList<PacketReceiveListener>> receiveListeners=new ConcurrentHashMap<String,CopyOnWriteArrayList<PacketReceiveListener>>();
	//public ConcurrentMap<PacketReceiveListener,String> receiveListeners=new ConcurrentHashMap<PacketReceiveListener,String>();
	public CopyOnWriteArrayList<ClientConnectListener> clientconnectListeners=new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<ConnectListener> connectListeners=new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<DisconnectListener> disconnectListeners=new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<ClientDisconnectListener> clientdisconnectListeners=new CopyOnWriteArrayList<>();
	public int port;
		
	public static NetworkStorage getInstance() {
		if(instance==null){
			instance = new NetworkStorage();
		}
		return instance;
	}
	public void callReceiveEvent(MessagePacket packet) {
		CopyOnWriteArrayList<PacketReceiveListener> listeners = receiveListeners.get(packet.getHeader());
		if(listeners!=null){
			for(PacketReceiveListener listener:listeners){
				listener.packetReceive(packet);
			}
		}
	}
	public void callClientConnectEvent(ClientInfo c){
		for(ClientConnectListener l:clientconnectListeners){
			l.clientConnect(c);
		}
	}
	public void callDisconnectEvent(Socket s){
		for(DisconnectListener l:disconnectListeners){
			l.Disconnect(s);
		}
	}
	public void callClientDisconnectEvent(ClientInfo c){
		for(ClientDisconnectListener l:clientdisconnectListeners){
			l.clientDisconnect(c);
		}
	}
	public void callConnectEvent(Socket s){
		for(ConnectListener l:connectListeners){
			l.Connect(s);
		}
	}
	public ClientInfo getClientByName(String nick){
		for(Entry<String, ClientInfo> c:clients.entrySet()){
			String clientNick=c.getKey();
			ClientInfo client=c.getValue();	
			if(clientNick.equals(nick)){
				return client;
			}
		}
		return null;
	}
	public void reset() {
		receiveListeners=new ConcurrentHashMap<String,CopyOnWriteArrayList<PacketReceiveListener>>();
		clientconnectListeners=new CopyOnWriteArrayList<>();
		connectListeners=new CopyOnWriteArrayList<>();
		disconnectListeners=new CopyOnWriteArrayList<>();
		clientdisconnectListeners=new CopyOnWriteArrayList<>();
		clients=new ConcurrentHashMap<String,ClientInfo>();
	}
}
