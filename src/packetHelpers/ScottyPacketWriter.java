package packetHelpers;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class ScottyPacketWriter {

	final int HEADERSIZE = 16;
	final int MAXDATASIZE = 65491;
	
	private DatagramPacket packet;
	private byte[] header;
	private byte[] data;
	
	public ScottyPacketWriter(byte[] data) throws Exception {
		if(data.length > MAXDATASIZE) {
			header = new byte[HEADERSIZE];
			this.data = data;
		} else {
			throw new Exception("data argument in ScottyPacketWriter exceeded Maximum length");
		}
	}
	
	//create engage packet
	
	//create 
	
	//Return string? not sure about this
	public void setIPAddress(String address) throws Exception {
		
		InetAddress a = InetAddress.getByName(address);
		
		byte[] ipAddress = a.getAddress();
		if(ipAddress.length != 4) {
			throw new Exception("Ya done goofed again");
		}
		
		System.arraycopy(ipAddress, 0, header, 0, 4);
	}
	public void setPort(int portNum) throws Exception {
		byte[] port = ByteBuffer.allocate(2).putInt(portNum).array();
		if(port.length > 2) {
			throw new Exception("Port too large");
		} 
		
		if(port.length < 2) {
			//May need to allocate more mem for small ports
			throw new Exception("Port too small");
		}
		
		System.arraycopy(port, 0, header, 3, 2);
	}
	public void setDataRate(int dataRateValue) {
		
		//TODO check if fileName flag is set, if not process
		byte[] dataRate = ByteBuffer.allocate(2).putInt(dataRateValue).array();
		
		System.arraycopy(dataRate, 0, header, 5, 2);
	}
	public byte setSequenceNumber() throws Exception {
		byte[] sequenceNumber = Arrays.copyOfRange(header, 8, 9);
		if(sequenceNumber.length != 1) {
			throw new Exception("Ya dun goofed");
		}
		return sequenceNumber[0];
	}
	public byte setAyeNumber() throws Exception {
		byte[] ayeNumber = Arrays.copyOfRange(header, 9, 10);
		if(ayeNumber.length != 1) {
			throw new Exception("Ya dun goofed");
		}
		return ayeNumber[0];
	}
	
	//flag setters
	
	public void setFileName(String fileNameString) {
		//check for under maxsize
		byte[] fileName = fileNameString.getBytes();
		byte[] fileNameLength = ByteBuffer.allocate(2).putInt(fileName.length).array();
		
		
	}
	
	public void setDataLength() {
		//if filename, this should throw exception
		
		byte[] dataLength = Arrays.copyOfRange(header, 12, 14);
		
	}
	
	//If for some reason we care about setting the checksum
	public byte setChecksum() throws Exception {
		byte[] checkSum = Arrays.copyOfRange(header, 15, 16);
		if(checkSum.length != 1) {
			throw new Exception("Ya dun goofed n");
		}
		return checkSum[0];
	}
	
	/**
	 * Returns true if CRC8 Checksum on Scotty Packet is valid
	 * @return
	 */
	public boolean isValid(byte[] packetContents) {
		CRC8 crc = new CRC8();
		byte valid = crc.checksum(packetContents);
		if(valid == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
}
