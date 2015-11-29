package packetHelpers;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.InputMismatchException;


public class ScottyPacketWriter {

	final int HEADERSIZE = 16;
	final int MAXDATASIZE = 65491; // UDP max data size == 65505
	
	//Flags
	private final int AYESET = 1 << 7;
	private final int ENGAGESET = 1 << 6;
	private final int STAHPSET = 1 << 5;
	private final int ENGAGETYPE = 1 << 4;// 0 == request, 1 == reply
	private final int DIRECTION = 1 << 3; // 0 == download, 1 == upload
	private final int NEWFILESET = 1 << 2;
	
	private DatagramPacket packet;
	private byte[] header;
	private byte[] data;
	private byte[] flags;
	
	public ScottyPacketWriter(byte[] data) throws Exception {
		if(data.length < MAXDATASIZE) {
			header = new byte[HEADERSIZE];
			flags = new byte[1];
			setDataLength(data.length);
			this.data = data;
		} else {
			throw new Exception("data argument in ScottyPacketWriter exceeded Maximum length");
		}
	}
	
	//create engage packet constructor
	
	//create blah blah packet constructor
	
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
		if(portNum > 65535) {
			throw new Exception("Port too large");
		}
		ByteBuffer myByteBuffer = ByteBuffer.allocate(4);
		myByteBuffer = myByteBuffer.putInt(portNum);
		byte[] port = myByteBuffer.array();
		if(port.length < 2) {
			throw new Exception("Port too small");
		}
		System.arraycopy(port, 2, header, 4, 2);
	}
	public void setDataRate(int dataRateValue) throws Exception {
		if(dataRateValue > 65535) {
			throw new Exception("AyeNumber exceeds max value");
		}
		//TODO check if fileName flag is set, if not process
		byte[] dataRate = ByteBuffer.allocate(4).putInt(dataRateValue).array();
		
		System.arraycopy(dataRate, 2, header, 6, 2);
	}
	public void setSequenceNumber(int sequenceNumberValue) throws Exception {
		if(sequenceNumberValue > 255) {
			throw new Exception("AyeNumber exceeds max value");
		}
		byte[] sequenceNumber = ByteBuffer.allocate(4).putInt(sequenceNumberValue).array();
		
		System.arraycopy(sequenceNumber, 3, header, 8, 1);
	}
	public void setAyeNumber(int ayeNumberValue) throws Exception {
		if(ayeNumberValue > 255) {
			throw new Exception("AyeNumber exceeds max value");
		}
		byte[] ayeNumber = ByteBuffer.allocate(4).putInt(ayeNumberValue).array();
		
		System.arraycopy(ayeNumber, 3, header, 9, 1);
	}
	
	//flag setters
	public void setEngage(boolean engaged) throws Exception {
		if((engaged == true && !isEngaged()) || (engaged == false && isEngaged())) {
			flags[0] = (byte) (flags[0] ^ ENGAGESET);
		} 
	}
	
	public void setAye(boolean ayed) throws Exception {
		if((ayed == true && !isAye()) || (ayed == false && isAye())) {
			flags[0] = (byte) (flags[0] ^ AYESET);
		}
	}
	
	/**
	 * Pass in 0 for download and 1 for upload
	 * @param direction
	 * @throws Exception 
	 */
	public void setDirection(int direction) throws Exception {
		//direction == 0 for download
		//direction == 1 for upload
		if(direction < 0 || direction > 1) {
			throw new InputMismatchException("Engage type may only be 0 or 1 (Request of Reply)");
		}
		
		if((direction == 0 && isUpload()) || (direction == 1 && isDownload())) {
			flags[0] = (byte) (flags[0] ^ DIRECTION);
		}
	}
	
	public void setEngageType(int engageType) throws Exception {
		//engageType == 0 for request
		//engageType == 1 for reply
		if(engageType < 0 || engageType > 1) {
			throw new InputMismatchException("Engage type may only be 0 or 1 (Request of Reply)");
		}
		
		if((engageType == 0 && isReply()) || (engageType == 1 && isRequest())) {
			flags[0] = (byte) (flags[0] ^ ENGAGETYPE);
		}
	}
	
	public void setNewFile() {
		flags[0] = (byte) (flags[0] ^ NEWFILESET);
	}
	
	public void setFileName(String fileNameString) throws Exception {
		if(fileNameString.length() > 50) {
			throw new Exception("FileName exceeds max length");
		}
		//check for flag bit
		//check for under maxsize
		byte[] fileName = fileNameString.getBytes();
		byte[] fileNameLength = ByteBuffer.allocate(4).putInt(fileName.length).array();
		
		System.arraycopy(fileNameLength, 2, header, 12, 2);
		System.arraycopy(fileName, 0, data, 0, fileName.length);
	}
	
	public void setDataLength(int datalengthValue) {
		//check for flag bit
		//if filename, this should throw exception
		byte[] dataLength = ByteBuffer.allocate(4).putInt(datalengthValue).array();
		
		System.arraycopy(dataLength, 2, header, 12, 2);
	}
	
	private void setChecksum(byte[] fullScottyPacket) throws Exception {
		CRC8 crc = new CRC8();
		//Place checksum at end of packet
		int lastIndex = fullScottyPacket.length - 1;
		fullScottyPacket[lastIndex] = crc.checksum(fullScottyPacket);
	}
	
	public DatagramPacket getPacket() throws Exception {
		
		byte[] fullScottyPacket = new byte[HEADERSIZE + data.length+1];
		
		//Place header and data into a single byte array, calc and set checksum
		System.arraycopy(flags, 0, header, 10, 1);
		System.arraycopy(header, 0, fullScottyPacket, 0, HEADERSIZE);
		System.arraycopy(data, 0, fullScottyPacket, HEADERSIZE, data.length);
		setChecksum(fullScottyPacket);
		
		return new DatagramPacket(fullScottyPacket, fullScottyPacket.length);		
	}
	
	
	
	
	/////Flag getters
	public int getFlag(int flag) throws Exception {
		byte[] flags = Arrays.copyOfRange(header, 10, 11);
		if(flags.length != 1) {
			throw new Exception("Ya dun goofed n");
		}
		return flags[0] & flag; //bitwise and
	}
	
	
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
	
	
}
