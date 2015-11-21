package starTrekReference;

public class InternetWormhole {
	
	double errorRateP, corruptRateQ;

	public InternetWormhole(String ip, int port, double errorRate, double corruptRate){
		errorRateP = errorRate;
		corruptRateQ = corruptRate;
	}
	
	public static void main(String[] args){
		
	}
	
	private byte[] corruptPacket(byte[] input){
		//useless comment to test git
		return input;
	}
	
}
