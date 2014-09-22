package network.command.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandStorage {
	private static CommandStorage instance;
	public static int UNLIMITED=Integer.MAX_VALUE;
	public List<CommandInfo> cmdlisteners=new ArrayList<>();
	
	public static CommandStorage getInstance(){
		if(instance==null){
			instance=new CommandStorage();
		}
		return instance;
	}
	public Boolean checkCommand(String userInput) {
		String[] str=userInput.split("\\s");
		String name=str[0];
		ArrayList<String> list=new ArrayList<String>(Arrays.asList(str));
		list.remove(name);
		for(CommandInfo c:cmdlisteners){
			if(c.getName().equals(name)){
				if(checkSize(c.getMin(),c.getMax(),list.size())){
					c.execute(list);
					return true;
				}
				System.out.println(Language.usage+c.getUsage());
				return true;
			}
		}
		return false;
		
	}
	public boolean checkSize(int min,int max, int size) {
		if(size>=min&&size<=max){
			return true;
		}
		if(min==max&&min==UNLIMITED){
			return true;
		}
		if(size<min){
			System.out.println(Language.tooFewArguments);
		}
		if(size>max){
			System.out.println(Language.tooManyArguments);
		}					
		return false;
	}
	public String ArgsString(int pos,List<String> args){
		String builder="";
		for(int i=pos++;i<args.size();i++){
			builder+=" "+args.get(i);
		}
		return builder;
	}
	public CommandInfo getCommand(String name){
		for(CommandInfo c:cmdlisteners){
			if(c.getName().equals(name)){
				return c;
			}
		}
		return null;
	}
	
}
