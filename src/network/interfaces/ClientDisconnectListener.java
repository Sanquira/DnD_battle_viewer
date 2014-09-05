package network.interfaces;

import network.source.ClientInfo;

public interface ClientDisconnectListener {
	public void clientDisconnect(ClientInfo c);
}
