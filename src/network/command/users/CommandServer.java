package network.command.users;

import java.io.IOException;

import network.command.annotations.CommandAnnotationChecker;
import network.command.interfaces.CommandListener;
import network.command.source.CommandHandler;
import network.command.source.CommandInfo;
import network.command.source.CommandStorage;
import network.core.users.NetworkServer;

public class CommandServer extends NetworkServer {
	CommandStorage cmd=CommandStorage.getInstance();
	private CommandHandler cmdThread;
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
	public void create(int timeout,String hostname, int port) throws IOException{
		super.create(hostname,port,timeout);
		registerInitialcommands();
		cmdThread=new CommandHandler();
	}
	public void create(String hostname, int port) throws IOException{
		create(getNetworkStorage().defaultserverTimeout,hostname,port);
	}
	public void create(int port, int timeout) throws IOException{
		super.create(port, timeout);
		registerInitialcommands();
		cmdThread=new CommandHandler();
	}
	public void create(int port) throws IOException{
		create(port,getNetworkStorage().defaultserverTimeout);
	}
	public CommandStorage getCommandStorage(){
		return cmd;
	}

	public void registerInitialcommands(){
		registerCommand("help", 0, "Help", "Zobrazí všechny dostupné příkazy", cmd.help);
	}
	@Override
	public void registerClass(Object obj){
		super.registerClass(obj);
		try {
			new CommandAnnotationChecker(obj,this).processClass();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void shutdown() throws IOException{
		super.shutdown();
		cmdThread.interrupt();
	}
}
