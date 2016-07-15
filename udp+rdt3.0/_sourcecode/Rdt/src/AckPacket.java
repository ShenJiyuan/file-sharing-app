/**
 * AckPacket : Encapsulate an ACK packet.
 *
 * This object contains only two public member, the ack number,
 * and a flag to indicate if the packet is corrupted.
 * The object is serialized so that we can send it over the
 * network using Object I/O stream.
 */

@SuppressWarnings("serial")
class AckPacket implements java.io.Serializable {
	public int ack;
	public boolean isCorrupted;

	// Create a normal ACK packet with given ack number.
	AckPacket(int ack)
	{
		this.ack = ack;
		this.isCorrupted = false;
	}
}
