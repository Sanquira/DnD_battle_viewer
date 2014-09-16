package network.core.users;

import java.util.List;

import network.core.interfaces.PacketReceiveListener;
import network.core.source.NetworkStorage;

public class AbstractNetworkUser {
	public NetworkStorage sk=NetworkStorage.getInstance();
	
	public List<PacketReceiveListener> getReceiveListeners(){
		return sk.receiveListeners;
	}
	public void addReceiveListener(PacketReceiveListener l){
		sk.receiveListeners.add(l);
	}
}
