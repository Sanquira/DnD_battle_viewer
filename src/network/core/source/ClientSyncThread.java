package network.core.source;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import network.core.annotations.Annotations.PacketReceiveAnnotation;
import network.core.interfaces.PacketReceiveListener;
import network.core.users.NetworkClient;

public class ClientSyncThread extends Thread {
	private AtomicBoolean checked = new AtomicBoolean(true); 
	private int timeout;
	private NetworkClient client;
    @PacketReceiveAnnotation(header = "serverCheck")
    private PacketReceiveListener serverCheck = new PacketReceiveListener(){

		@Override
		public void packetReceive(MessagePacket p) {
			checked.lazySet(true);
		}
    	
    };
    @Override
    public void run(){
    	while(!interrupted()){
    		try{
				if (!checked.get()) {
					System.out.println("dis");
					client.disconnect();
					client.getNetworkStorage().callDisconnectEvent(client.getSocket(),new IOException("Timeout"));
				}
				checked.lazySet(false);
				Thread.sleep(timeout);
			} catch (InterruptedException | IOException e) {
				this.interrupt();
			}
    	}
    }
    public ClientSyncThread(int timeout, NetworkClient client){
    	super("ClientSyncThread");
    	client.registerClass(this);
    	this.timeout = timeout;
    	this.client = client;
    	super.start();
    }
}
