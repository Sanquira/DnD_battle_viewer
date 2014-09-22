package network.command.source;

import java.util.List;

import network.command.interfaces.CommandListener;

public class CommandInfo {
	private CommandListener listener;
	private String name;
	private int arguments=0;
	private int min=0;
	private int max=0;
	private String usage="";
	public CommandInfo(String name,int arguments, String usage, CommandListener list){
		this.name=name;
		this.listener=list;
		this.setArguments(arguments);
		this.setMax(arguments);
		this.setMin(arguments);
		this.setUsage(usage);
	}
	public CommandInfo(String name,int min,int max, String usage, CommandListener list){
		this.name=name;
		this.listener=list;
		this.setArguments(arguments);
		this.setMax(max);
		this.setMin(min);
		this.setUsage(usage);
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public int getArguments() {
		return arguments;
	}
	public void setArguments(int arguments) {
		this.arguments = arguments;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CommandListener getListener() {
		return listener;
	}
	public void setListener(CommandListener listener) {
		this.listener = listener;
	}
	public CommandInfo(String name, CommandListener list){
		this.name=name;
		this.listener=list;
	}
	public int getMin() {
		return min;
	}
	public void setMin(int min) {
		this.min = min;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public void execute(List<String> args){
		listener.CommandExecuted(args);
	}
	
}
