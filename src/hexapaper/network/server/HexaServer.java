package hexapaper.network.server;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import network.command.interfaces.CommandListener;
import network.command.users.CommandServer;
import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ConnectListener;
import network.core.source.ClientInfo;

public class HexaServer {
	private static boolean isConsole=true;
	private static CommandServer s=null;
	private static CommandListener setPJ=new CommandListener(){

		@Override
		public void CommandExecuted(List<String> args) {
			ClientInfo c=s.getNetworkStorage().getClientByName(args.get(0));
			c.getAtributes().put("PJ", Boolean.parseBoolean(args.get(1)));
			System.out.println("Hráči " +c.getNick()+" byl nastaven PJ na hodnotu "+Boolean.parseBoolean(args.get(1)));
		}
		
	};
	private static CommandListener isPJ=new CommandListener(){

		@Override
		public void CommandExecuted(List<String> args) {
			ClientInfo c=s.getNetworkStorage().getClientByName(args.get(0));
			if((boolean) c.getAtributes().get("PJ")){
				System.out.println("Ano");
				return;
			}
			System.out.println("Ne");
			
		}
		
	};
	private static ClientConnectListener connect=new ClientConnectListener(){

		@Override
		public void clientConnect(ClientInfo c) {
			c.getAtributes().put("PJ", false);
			System.out.println("Hráč "+c.getNick()+" se právě připojil");
		}
	};
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
		registerInitialEvents(s);
		try {
			s.create(args[0],1055);
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void registerInitialEvents(CommandServer s) {
		s.addClientConnectListener(connect);
		s.registerCommand("PJ", 2, "PJ <Player> <True/False>", setPJ);
		s.registerCommand("isPJ",1,"isPJ <Player>",isPJ);
	}
}
