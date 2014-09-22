package network.core.users;

import java.util.Map;

import network.core.interfaces.PacketReceiveListener;
import network.core.source.NetworkStorage;

public class AbstractNetworkUser {
	NetworkStorage sk=NetworkStorage.getInstance();
	
	public Map<PacketReceiveListener,String> getReceiveListeners(){
		return sk.receiveListeners;
	}
	public void addReceiveListener(PacketReceiveListener l,String header){
		sk.receiveListeners.put(l,header);
	}
	public void addReceiveListener(PacketReceiveListener l){
		sk.receiveListeners.put(l,"none");
	}
	public NetworkStorage getNetworkStorage(){
		return sk;
	}
}
