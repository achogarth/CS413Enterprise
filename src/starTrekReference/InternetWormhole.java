package starTrekReference;

import java.util.Random;

public class InternetWormhole implements Runnable{
	
	double errorRate, corruptRate, compare;
	Random random = new Random();
	byte[] currentPacket;

	public InternetWormhole(String ip, int port, double errorRate, double corruptRate){
		errorRate = errorRate;
		corruptRate = corruptRate;
		run();
	}
	
	public static void main(String[] args){
		new InternetWormhole("127.0.0.1", 1, 0.5, 0.5); //temporary for testing
	}
	
	private byte[] corruptPacket(byte[] input){
		Random r = new Random();
		
		//Generate a bitmask with only one bit set
		byte bitMask = 1;
		byte randShift = (byte) r.nextInt(8);
		bitMask <<= randShift;
		
		//calculate a random index within range and save it
		int randIndex = r.nextInt(input.length);
		
		//XOR the given byte with the bitmask.  Thus result has one bit error.
		input[randIndex] ^= bitMask;
		
		return input;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		while (true){
			//get next packet from client/server
			
			//check if connection request
			
			//assign new connection
			
			
			//calculate if error
			compare = random.nextDouble();
			if (compare < errorRate){
				//error occurred
				//calculate if corrupt
				compare = random.nextDouble();
				if (compare < corruptRate){
					//corrupt the packet
					currentPacket = corruptPacket(currentPacket);
					//TODO send the packet
					
				}
				//else do nothing, drop the packet
			}//end of error calculation
			
		}//end while loop
		
	}
	
}
