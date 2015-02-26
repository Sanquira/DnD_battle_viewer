package network.core.annotations;

import java.lang.reflect.Field;

import network.core.interfaces.ClientConnectListener;
import network.core.interfaces.ClientDisconnectListener;
import network.core.interfaces.ConnectListener;
import network.core.interfaces.DisconnectListener;
import network.core.interfaces.PacketReceiveListener;
import network.core.users.AbstractNetworkUser;
import network.core.users.NetworkClient;
import network.core.users.NetworkServer;

public class AnnotationChecker {

	private Object obj;
	private NetworkServer srv;
	private AbstractNetworkUser usr;
	private NetworkClient cln;
	public AnnotationChecker(Object obj, NetworkServer server){
		this.obj = obj;
		this.srv = server;
		this.usr = server;
	}
	public AnnotationChecker(Object obj, NetworkClient cln){
		this.obj = obj;
		this.cln = cln;
		this.usr = cln;
	}
	
	public void processClass() throws IllegalArgumentException, IllegalAccessException{
		for(Field f:obj.getClass().getDeclaredFields()){
			f.setAccessible(true);
			if(f.isAnnotationPresent(Annotations.ClientConnectAnnotation.class)){
				srv.addClientConnectListener((ClientConnectListener) f.get(obj));
			}
			if(f.isAnnotationPresent(Annotations.ClientDisconnectAnnotation.class)){
				srv.addClientDisconnectListener((ClientDisconnectListener) f.get(obj));
			}
			if(f.isAnnotationPresent(Annotations.ConnectAnnotation.class)){
				cln.addConnectListener((ConnectListener) f.get(obj));
			}
			if(f.isAnnotationPresent(Annotations.DisconnectAnnotation.class)){
				cln.addDisconnectListener((DisconnectListener) f.get(obj));
			}
			if(f.isAnnotationPresent(Annotations.PacketReceiveAnnotation.class)){
				usr.addReceiveListener((PacketReceiveListener) f.get(obj), f.getAnnotation(Annotations.PacketReceiveAnnotation.class).header());
			}
		}
	}
}
