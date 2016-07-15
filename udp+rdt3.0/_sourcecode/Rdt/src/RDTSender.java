/**
 * RDTSender : Encapsulate a reliable data sender that runs
 * over a unreliable channel that may drop and corrupt packets 
 * (but always delivers in order).
 */
import java.io.*;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.*;

/**
 * RDTSender receives a byte array from "above", construct a
 * data packet, and send it via UDT.  It also receives
 * ack packets from UDT.
 */
class RDTSender {
	UDTSender udt;
    int seqNumber;
    String hostname;
    int port;

	RDTSender(String hostname, int port) throws IOException
	{
		try {
			udt = new UDTSender(hostname, port);
		} catch (ConnectException e) {
			System.out.println("Connection refused. Ensure receiver is accepting connections.");
			System.exit(0);
		}
        seqNumber = 0;
        this.hostname = hostname;
        this.port = port;
	}

	/**
	 * send() delivers the given array of bytes reliably and should
	 * not return until it is sure that the packet has been
	 * delivered.
	 */
	void send(byte[] data, int length) throws IOException, ClassNotFoundException
	{
        // send packet
		DataPacket p = new DataPacket(data, length, seqNumber);
		udt.send(p);
		
		// start timer
		Timer timeOut = new Timer();
		timeOut.schedule(new senderWithTimer(p), 100, 100);
		
        // receive ACK
		AckPacket ack = udt.recv();
		
		// waits for ack until valid ack is received
		while(ack.isCorrupted==true || ack.ack!=seqNumber) {
			ack = udt.recv();
		}
		
		// handle pkts with correct ack
		assert (ack.isCorrupted==false && ack.ack==seqNumber);
		timeOut.cancel();
		
		// alternate sequence number
		seqNumber = alterateBit(seqNumber);
	}

	/**
	 * Swap the bit around
	 * i.e. 1 -> 0, 0 -> 1
	 */
	private int alterateBit(int bit) {
		assert (bit==1 || bit==0);
		if(bit==0) {
			bit = 1;
		} else {
			bit = 0;
		}
		
		return bit;
	}

	/**
	 * close() is called when there is no more data to send.
	 * This method creates an empty packet with 0 bytes and
	 * send it to the receiver, to indicate that there is no
	 * more data.
	 * 
	 * This method should not return until it is sure that
	 * the empty packet has been delivered correctly.  It 
	 * catches any EOFException (which signals the receiver
	 * has closed the connection) and close its own connection.
	 */
	void close() throws IOException, ClassNotFoundException
	{
		DataPacket p = new DataPacket(null, 0, seqNumber);
		udt.send(p);
		System.out.println("S: send terminating pkt");
		
		// start timer
		Timer timeOut = new Timer();
		timeOut.schedule(new senderWithTimer(p), 100, 100);
				
		try {
			AckPacket ack = udt.recv();
			
			while(ack.isCorrupted==true || ack.ack!=seqNumber) {
				ack = udt.recv();
			}
			
			// handle pkts with correct ack
			assert (ack.isCorrupted==false && ack.ack==seqNumber);
			timeOut.cancel();
		} catch (EOFException e) {
			System.out.println("S: connection to R closed");
			timeOut.cancel();
		} catch (SocketException e) {
			System.out.println("S: connection to R closed");
			timeOut.cancel();
		} finally {
			udt.close();
		}
	}
	
	class senderWithTimer extends TimerTask {
		DataPacket p;
		
		public senderWithTimer(DataPacket p) {
			this.p = p;
		}

		@Override
		/**
		 * run() is called when timer timeouts.
		 * Packet is retransmitted and timer is restarted.
		 */
		public void run() {
			try {
				System.out.println("S: Timeout");
				udt.send(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}