package network.core.source;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import network.core.annotations.Annotations.PacketReceiveAnnotation;
import network.core.interfaces.PacketReceiveListener;
import network.core.users.NetworkServer;

public class ServerSyncThread extends Thread {
	private NetworkServer server;
	private NetworkStorage storage = NetworkStorage.getInstance();
	private ConcurrentHashMap<ClientInfo, Boolean> responses = new ConcurrentHashMap<ClientInfo, Boolean>();
	private int timeout;

	@PacketReceiveAnnotation(header = "clientCheck")
	private PacketReceiveListener check = new PacketReceiveListener() {
		@Override
		public void packetReceive(MessagePacket p) {
			if (storage.isConnected(p.getNick())) {
				ClientInfo ci = storage.getClientByName(p.getNick());
				responses.put(ci, true);
				ci.send(0,"serverCheck");
			}
		}
	};

	public ServerSyncThread(int timeout, NetworkServer server) {
		super("CheckClientsThread");
		server.registerClass(this);
		this.server = server;
		this.timeout = timeout;
		super.start();
	}

	@Override
	public void run() {
		//System.out.println("CheckThread started");
		while (!interrupted()) {
			checkRespond();
			sendCheck();
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}

	private void checkRespond() {
		for (Entry<ClientInfo, Boolean> entry : responses.entrySet()) {
			ClientInfo ci = entry.getKey();
			Boolean bol = entry.getValue();
			if (!bol && storage.isConnected(ci.getNick())) {
				System.out.println(ci.getNick()+" dsc");
				storage.kick(ci.getNick(), "Timeout");
				// storage.disconnectClient(ci, new IOException("Timeout"));
			}
		}
		responses = new ConcurrentHashMap<ClientInfo, Boolean>();
		for (ClientInfo ci : storage.clients) {
			responses.put(ci, false);
		}
		// System.err.println("Checked");
	}

	private void sendCheck() {
		server.broadcast(0, "clientCheck");
		// System.err.println("sent");
	}
}
