package packetHelpers;
import java.net.DatagramPacket;
import java.util.Arrays;


public class ScottyPacketReader {

	final int HEADERSIZE = 16;
	
	private DatagramPacket packet;
	private byte[] header;
	private byte[] data;
	private final int AYESET = 1 << 7;
	private final int ENGAGESET = 1 << 6;
	private final int STAHPSET = 1 << 5;
	private final int ENGAGETYPE = 1 << 4; // 0 == request, 1 == reply
	private final int DIRECTION = 1 << 3; // 0 == download, 1 == upload
	private final int NEWFILESET = 1 << 2;
	
	
	public ScottyPacketReader(DatagramPacket packet) throws Exception{
		if(packet.getData().length < HEADERSIZE) {
			throw new Exception("Message length is less than minimum Scotty packet size.");
		}
		this.packet = packet;//necessary?? idk
		byte[] packetContents = packet.getData();
		
		header = new byte[HEADERSIZE];
		
		//populate header with first 16 bytes of data
		header = Arrays.copyOfRange(packetContents, 0, HEADERSIZE);
		
		//populate data with everything after first 16 bytes
		data = Arrays.copyOfRange(packetContents, HEADERSIZE, packetContents.length-1);
	}
	
	public byte[] getHeader() {
		return header;
	}
	public byte[] getData() {
		return data;
	}
	public String getDataAsString() {
		return new String(data);
	}
	
	//Return string? not sure about this
	public String getIPAddress() {
		return new String(Arrays.copyOfRange(header, 0, 4));
	}
	//TODO return int
	public byte[] getPort() {
		byte[] port = Arrays.copyOfRange(header, 4, 6);
		
		return port;
	}
	//TODO return int
	public byte[] getDataRate() {
		byte[] dataRate = Arrays.copyOfRange(header, 6, 8);
		
		return dataRate;
	}
	public byte getSequenceNumber() throws Exception {
		byte[] sequenceNumber = Arrays.copyOfRange(header, 8, 9);
		if(sequenceNumber.length != 1) {
			throw new Exception("Ya dun goofed n");
		}
		return sequenceNumber[0];
	}
	public byte getAyeNumber() throws Exception {
		byte[] ayeNumber = Arrays.copyOfRange(header, 9, 10);
		if(ayeNumber.length != 1) {
			throw new Exception("Ya dun goofed n");
		}
		return ayeNumber[0];
	}
	
	public int getFlag(int flag) throws Exception {
		byte[] flags = Arrays.copyOfRange(header, 10, 11);
		if(flags.length != 1) {
			throw new Exception("Ya dun goofed n");
		}
		return flags[0] & flag; //bitwise and
	}
	
	//flag getters
	public boolean isFileName() throws Exception  {
		int mask = getFlag(NEWFILESET);
		if(mask == 1)
			return true;
		else
			return false;
	}
	
	public boolean isAye() throws Exception {
		int mask = getFlag(AYESET);
		if(mask == 1)
			return true;
		else
			return false;
	}
	public boolean isEngaged() throws Exception {
		int mask = getFlag(ENGAGESET);
		if(mask == 1)
			return true;
		else
			return false;
	}
	public boolean isStahped() throws Exception {
		int mask = getFlag(STAHPSET);
		if(mask == 1)
			return true;
		else
			return false;
	}
	public boolean isUpload() throws Exception {
		int mask = getFlag(DIRECTION);
		if(mask == 1)
			return true;
		else
			return false;
	}
	public boolean isDownload() throws Exception {
		int mask = getFlag(DIRECTION);
		if(mask == 1)
			return false;
		else
			return true;
	}
	public boolean isRequest() throws Exception {
		int mask = getFlag(ENGAGETYPE);
		if(mask == 1) {
			return false;
		} else {
			return true;
		}
	}
	public boolean isReply() throws Exception {
		int mask = getFlag(ENGAGETYPE);
		if(mask == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public byte[] getFileName() {
		//if datalength, this should throw exception
		
		byte[] fileName = Arrays.copyOfRange(header, 12, 14);
		
		return fileName;
	}
	
	public byte[] getDataLength() {
		//if filename, this should throw exception
		
		byte[] dataLength = Arrays.copyOfRange(header, 12, 14);
		
		return dataLength;
	}
	
	//If for some reason we care about getting the checksum
	public byte getChecksum() throws Exception {
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
