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

	public AnnotationChecker(Object obj, NetworkServer server) {
		this(server, obj);
		this.srv = server;
	}
	
	public AnnotationChecker(Object obj, NetworkClient cln) {
		this(cln, obj);
		this.cln = cln;
	}
	
	private AnnotationChecker(AbstractNetworkUser cln, Object obj){
		this.obj = obj;
		this.usr = cln;
	}

	public void processClass() throws IllegalArgumentException,
			IllegalAccessException {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(Annotations.ClientConnectAnnotation.class)
					&& f.get(obj) instanceof ClientConnectListener) {
				srv.addClientConnectListener((ClientConnectListener) f.get(obj));
			}
			if (f.isAnnotationPresent(Annotations.ClientDisconnectAnnotation.class)
					&& f.get(obj) instanceof ClientDisconnectListener) {
				srv.addClientDisconnectListener((ClientDisconnectListener) f
						.get(obj));
			}
			if (f.isAnnotationPresent(Annotations.ConnectAnnotation.class)
					&& f.get(obj) instanceof ConnectListener) {
				cln.addConnectListener((ConnectListener) f.get(obj));
			}
			if (f.isAnnotationPresent(Annotations.DisconnectAnnotation.class)
					&& f.get(obj) instanceof DisconnectListener) {
				cln.addDisconnectListener((DisconnectListener) f.get(obj));
			}
			if (f.isAnnotationPresent(Annotations.PacketReceiveAnnotation.class)
					&& f.get(obj) instanceof PacketReceiveListener) {
				usr.addReceiveListener(
						(PacketReceiveListener) f.get(obj),
						f.getAnnotation(
								Annotations.PacketReceiveAnnotation.class)
								.header());
			}
		}
	}
}
