package network.command.users;

import java.io.IOException;
import java.net.UnknownHostException;

import network.command.interfaces.CommandListener;
import network.command.source.CommandHandler;
import network.command.source.CommandInfo;
import network.command.source.CommandStorage;
import network.core.users.NetworkClient;

public class CommandClient extends NetworkClient {
	CommandStorage cmd=CommandStorage.getInstance();
	public void registerCommand(String name,int arguments,String usage, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, arguments,usage,listener));
	}
	public void connect(String hostName,int port, String name) throws UnknownHostException, IOException{
		super.connect(hostName, port, name);
		new Thread(new CommandHandler()).start();
	}
	public CommandStorage getCommandStorage(){
		return cmd;
	}
	
}
