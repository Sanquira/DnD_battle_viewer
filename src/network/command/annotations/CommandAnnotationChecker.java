package network.command.annotations;

import java.lang.reflect.Field;

import network.command.interfaces.CommandListener;
import network.command.users.CommandClient;
import network.command.users.CommandServer;

public class CommandAnnotationChecker {
	private Object obj;
	private CommandServer srv;
	private CommandClient cln;
	public CommandAnnotationChecker(Object obj, CommandServer srv){
		this.obj = obj;
		this.srv = srv;
	}
	public CommandAnnotationChecker(Object obj, CommandClient cln){
		this.obj = obj;
		this.cln = cln;
	}
	
	public void processClass() throws IllegalArgumentException, IllegalAccessException{
		for(Field f:obj.getClass().getDeclaredFields()){
			f.setAccessible(true);
			if(f.isAnnotationPresent(CommandAnnotation.class) && f.get(obj) instanceof CommandListener){
				CommandAnnotation ann = f.getAnnotation(CommandAnnotation.class);
				if(srv!=null){
					if(ann.arg()>=0){
						srv.registerCommand(ann.name(), ann.arg(), ann.usage(), ann.help(), (CommandListener) f.get(obj));
					}
					else{
						srv.registerCommand(ann.name(), ann.min(), ann.max() , ann.usage(), ann.help(), (CommandListener) f.get(obj));
					}
					//System.out.println("Zaregistrován listener");
				}
				else{
					if(ann.arg()>=0){
						cln.registerCommand(ann.name(), ann.arg(), ann.usage(), ann.help(), (CommandListener) f.get(obj));
					}
					else{
						cln.registerCommand(ann.name(), ann.min(), ann.max(), ann.usage(), ann.help(), (CommandListener) f.get(obj));
					}
					System.out.println("Zaregistrován listener");
				}
			}
		}	
	}
}
