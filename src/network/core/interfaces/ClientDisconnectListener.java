package network.core.interfaces;

import network.core.source.ClientInfo;

public interface ClientDisconnectListener {
	public void clientDisconnect(ClientInfo c);
}
