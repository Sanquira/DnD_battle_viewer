package network.core.source;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;

public class NetworkStorage {
	private static NetworkStorage instance;
	public List<ClientInfo> clients=new ArrayList<>();
	public CopyOnWriteArrayList<PacketReceiveListener> receiveListeners=new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<ClientConnectListener> clientconnectListeners=new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<ConnectListener> connectListeners=new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<DisconnectListener> disconnectListeners=new CopyOnWriteArrayList<>();
	public CopyOnWriteArrayList<ClientDisconnectListener> clientdisconnectListeners=new CopyOnWriteArrayList<>();
	public int port;
		
	public static NetworkStorage getInstance() {
		if (instance == null) {
			instance = new NetworkStorage();
		}
		return instance;
	}
	public void callReceiveEvent(MessagePacket inputLine) {
		for(PacketReceiveListener l:receiveListeners){
			l.packetReceive(inputLine);
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
		for(ClientInfo c:clients){
			if(c.getNick().equals(nick)){
				return c;
			}
		}
		return null;
	}
}
