package hexapaper.network.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import network.command.interfaces.CommandListener;
import network.command.users.CommandServer;
import network.core.interfaces.ClientConnectListener;
import network.core.source.ClientInfo;

public class HexaServer extends CommandServer {
	private static boolean isConsole=true;
	private static CommandServer s=null;
	public static void main(String[] args) throws IOException {

		if(isConsole){
			consoleStart(args);
		}
		guiStart();
	}
	private static void guiStart() {
		// TODO Auto-generated method stub
		
	}
	private static void consoleStart(String[] args) {
		s=new CommandServer();
		new ServerListeners(s);
		try {
			s.create("192.168.0.102",10055);
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
