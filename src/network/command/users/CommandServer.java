package network.command.users;

import java.io.IOException;

import network.command.interfaces.CommandListener;
import network.command.source.CommandHandler;
import network.command.source.CommandInfo;
import network.command.source.CommandStorage;
import network.core.source.ClientInfo;
import network.core.users.NetworkServer;

public class CommandServer extends NetworkServer {
	CommandStorage cmd=CommandStorage.getInstance();
	public CommandServer(){
		cmd.reset();
	}	
	public void registerCommand(String name,int arguments,String usage,String help, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, arguments,usage,help,listener));
	}
	public void setDefaultCommand(CommandListener commandListener){
		cmd.setDefaultCommand(new CommandInfo("default",CommandStorage.UNLIMITED,"","",commandListener));
	}
	public void registerCommand(String name,int min,int max,String usage,String help, CommandListener listener){
		cmd.cmdlisteners.add(new CommandInfo(name, min,max,usage,help,listener));
	}
	public void create(String hostname, int port) throws IOException{
		super.create(hostname,port);
		registerInitialcommands();
		Thread cmd=new Thread(new CommandHandler());
		cmd.setName("CommandThread");
		cmd.start();
	}
	public void create(int port) throws IOException{
		super.create(port);
		registerInitialcommands();
		new Thread(new CommandHandler()).start();
	}
	public CommandStorage getCommandStorage(){
		return cmd;
	}
	public void rebroadcast(String sender, Object o,String header){
		for(ClientInfo c:getNetworkStorage().clients.values()){
			if(!c.getNick().equals(sender)){
				c.send(sender,o,header);
			}
		}
	}
	public void registerInitialcommands(){
		registerCommand("help", 0, "Help", "Zobrazí všechny dostupné příkazy", cmd.help);
	}
}
