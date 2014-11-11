package network.core.users;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import network.core.interfaces.PacketReceiveListener;
import network.core.source.NetworkStorage;

public class AbstractNetworkUser {
	NetworkStorage sk=NetworkStorage.getInstance();
	
	public Map<String, CopyOnWriteArrayList<PacketReceiveListener>> getReceiveListeners(){
		return sk.receiveListeners;
	}
	public void addReceiveListener(PacketReceiveListener listener,String header){
		ConcurrentMap<String, CopyOnWriteArrayList<PacketReceiveListener>> listeners = sk.receiveListeners;
		if(sk.receiveListeners.containsKey(header)){
			listeners.get(header).add(listener);
			return;
		}
		CopyOnWriteArrayList<PacketReceiveListener> clearList = new CopyOnWriteArrayList<PacketReceiveListener>();
		clearList.add(listener);
		sk.receiveListeners.put(header, clearList);
	}
	public NetworkStorage getNetworkStorage(){
		return sk;
	}
}
