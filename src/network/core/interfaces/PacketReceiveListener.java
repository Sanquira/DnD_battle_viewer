package network.core.interfaces;

import network.core.source.MessagePacket;

public interface PacketReceiveListener {
	public void packetReceive(MessagePacket p);
}
