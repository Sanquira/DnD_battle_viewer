package hexapaper.network.server;

import java.io.IOException;
import network.command.users.CommandServer;

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
			s.create(args[0],Integer.parseInt(args[1]));
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
