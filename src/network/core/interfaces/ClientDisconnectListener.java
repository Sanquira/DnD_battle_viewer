package network.core.interfaces;

import java.io.IOException;

import network.core.source.ClientInfo;

public interface ClientDisconnectListener {
	public void clientDisconnect(ClientInfo c,IOException e);
}
