package network.interfaces;

import java.net.Socket;


public interface DisconnectListener {
	public void Disconnect(Socket s);
}
