package packetHelpers;

import java.net.DatagramPacket;
import java.util.Arrays;

public class ReaderWriterTest {

	public static void main(String[] args) throws Exception {
		
		byte[] data = "abc".getBytes();
		ScottyPacketWriter writer = new ScottyPacketWriter(data);
		writer.setIPAddress("1.2.3.4");
		writer.setPort(256);
		writer.setAyeNumber(4);
		writer.setDataRate(8);
		writer.setSequenceNumber(2);
		writer.setEngage(true);
		writer.setAye(true);
		writer.setEngage(false);
		
		DatagramPacket d = writer.getPacket();
		
		byte[] checkData = d.getData();
		assert checkData.length == 18;
		
		
		System.out.println("IP Address: ");
		String ip1 = String.format("%8s", Integer.toBinaryString(checkData[0] & 0xFF)).replace(' ', '0');
		System.out.println(ip1);
		String ip2 = String.format("%8s", Integer.toBinaryString(checkData[1] & 0xFF)).replace(' ', '0');
		System.out.println(ip2);
		String ip3 = String.format("%8s", Integer.toBinaryString(checkData[2] & 0xFF)).replace(' ', '0');
		System.out.println(ip3);
		String ip4 = String.format("%8s", Integer.toBinaryString(checkData[3] & 0xFF)).replace(' ', '0');
		System.out.println(ip4);
		
		System.out.println("port: ");
		String port1 = String.format("%8s", Integer.toBinaryString(checkData[4] & 0xFF)).replace(' ', '0');
		System.out.println(port1);
		String port2 = String.format("%8s", Integer.toBinaryString(checkData[5] & 0xFF)).replace(' ', '0');
		System.out.println(port2);

		System.out.println("data rate");
		String dataRate1 = String.format("%8s", Integer.toBinaryString(checkData[6] & 0xFF)).replace(' ', '0');
		System.out.println(dataRate1);
		String dataRate2 = String.format("%8s", Integer.toBinaryString(checkData[7] & 0xFF)).replace(' ', '0');
		System.out.println(dataRate2);
		
		System.out.println("Sequence number");
		String sequenceNumber = String.format("%8s", Integer.toBinaryString(checkData[8] & 0xFF)).replace(' ', '0');
		System.out.println(sequenceNumber);
		
		System.out.println("Aye number");
		String ayeNum = String.format("%8s", Integer.toBinaryString(checkData[9] & 0xFF)).replace(' ', '0');
		System.out.println(ayeNum);
		
		System.out.println("Flags");
		String flags = String.format("%8s", Integer.toBinaryString(checkData[10] & 0xFF)).replace(' ', '0');
		System.out.println(flags);
		

		System.out.println("DataLength / FileNameLength");
		String datalength = String.format("%8s", Integer.toBinaryString(checkData[12] & 0xFF)).replace(' ', '0');
		System.out.println(datalength);
		
		System.out.println("Data");
		for(int i = 16 ; i < checkData.length; i++) {
			String dataOut = String.format("%8s", Integer.toBinaryString(checkData[i] & 0xFF)).replace(' ', '0');
			System.out.println(dataOut);	
		}
		

		System.out.println();
		System.out.println();
		System.out.println();
		
		
		ScottyPacketReader reader = new ScottyPacketReader(d);
		System.out.println(reader.getDataAsString());
		
		
		
		
		
	}

}
