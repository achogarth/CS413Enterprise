package starTrekReference;

import enterpriseExceptions.MalfunctionException;
import guis.*;

public class Enterprise {
	
	static int WormholePort;
	static String WormholeIP;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String mode = args[0];

			switch (mode.charAt(0)) {
			case 'c':
			case 'C':
				WormholeIP = args[1];
				
				try {
					WormholePort = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					throw new MalfunctionException();
				}
				
				new ClientInterface(WormholeIP, WormholePort);
				
				break;
			case 'S':
			case 's':
				new ServerInterface();
				break;
			default:
				throw new MalfunctionException("Invalid mode: C or c for client, S or s for server");
			}
		} 
		catch (MalfunctionException e){
			System.out.println(e.getMessage());
		}

	}

}
