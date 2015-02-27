package network.core.source;

import java.io.IOException;
import java.net.Socket;
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
	public String reason;
	public int defaultserverTimeout = 5000;
	public int defaultclientTimeout = 7000;
	public static String defaultReason = "Server closed";
	public static String version = "0.4";
	//public ConcurrentMap<String,ClientInfo> clients=new ConcurrentHashMap<String,ClientInfo>();
	public CopyOnWriteArrayList<ClientInfo> clients=new CopyOnWriteArrayList<>();
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
		c.setInitialized(true);
		c.send(0,"serverCheck");
	}
	public void callDisconnectEvent(Socket s,IOException e){
		String reason = defaultReason;
		boolean kicked = false;
		if(this.reason!=null){
			reason = this.reason;
			kicked = true;
		}
		for(DisconnectListener l:disconnectListeners){
			l.Disconnect(s,e,reason,kicked);
		}
	}
	public void callClientDisconnectEvent(ClientInfo c,IOException e){
		for(ClientDisconnectListener l:clientdisconnectListeners){
			l.clientDisconnect(c,e);
		}
	}
	public void callConnectEvent(Socket s){
		for(ConnectListener l:connectListeners){
			l.Connect(s);
		}
	}
	public ClientInfo getClientByName(String nick){
		for(ClientInfo ci:clients){
			String n = ci.getNick().replaceAll("[^\\x20-\\x7e]", "");
			if(n.equals(nick.replaceAll("[^\\x20-\\x7e]", ""))){
				return ci;
			}
		}
		return null;
	}
	public boolean isConnected(String nick){
		return getClientByName(nick)!=null;
	}
	public void disconnectClient(String nick,IOException e){
		if(getClientByName(nick)!=null){
			ClientInfo c = getClientByName(nick);
			c.remove();
			clients.remove(c);
			callClientDisconnectEvent(c,e);
		}	
	}
	public void disconnectClient(ClientInfo ci,IOException e){
		ci.remove();
		clients.remove(ci);
		callClientDisconnectEvent(ci,e);
	}
	public void kick(String nick, String reason){
		ClientInfo cln = getClientByName(nick);
		if(cln!=null){
			cln.kick(reason);
			clients.remove(cln);
		}
	}
	public void reset() {
		receiveListeners=new ConcurrentHashMap<String,CopyOnWriteArrayList<PacketReceiveListener>>();
		clientconnectListeners=new CopyOnWriteArrayList<>();
		connectListeners=new CopyOnWriteArrayList<>();
		disconnectListeners=new CopyOnWriteArrayList<>();
		clientdisconnectListeners=new CopyOnWriteArrayList<>();
		clients=new CopyOnWriteArrayList<>();
	}
}
