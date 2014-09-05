package network.interfaces;

import network.source.MessagePacket;

public interface PacketReceiveListener {
	public void packetReceive(MessagePacket p);
}
