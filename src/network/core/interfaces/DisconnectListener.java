package network.core.interfaces;

import java.io.IOException;
import java.net.Socket;

public interface DisconnectListener {
	public void Disconnect(Socket s, IOException e, String reason, boolean kicked);
}
