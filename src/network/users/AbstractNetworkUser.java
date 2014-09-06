package network.users;

import java.util.List;

import network.interfaces.PacketReceiveListener;
import network.source.NetworkStorage;

public class AbstractNetworkUser {
	public NetworkStorage sk=NetworkStorage.getInstance();
	
	public List<PacketReceiveListener> getReceiveListeners(){
		return sk.receiveListeners;
	}
	public void addReceiveListener(PacketReceiveListener l){
		sk.receiveListeners.add(l);
	}
}
