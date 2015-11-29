package starTrekReference;

import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.SwingWorker;

public class ServerWorker extends SwingWorker {

	boolean accepting;
	int engagedConnections;
	FileReader reader;
	FileWriter writer;
	
	public ServerWorker () {
		accepting = true;
		engagedConnections = 0;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		// TODO This is where the server logic will go
		while (accepting){//server is accepting new connections
			//before we receive shutdown from server gui
			
			//listen for incoming packet
			//if engage , reply to setup connection by completing handshake
			//handshake section
			
			
			
			//end handshke section
			
			//loop to recieve/send data
			//while(!stahp){
			
			
//			 s.setSoTimeout(1000);   // set the timeout in millisecounds.
//
//		        while(true){        // recieve data until timeout
//		            try {
//		                s.receive(dp);
//		                String rcvd = "rcvd from " + dp.getAddress() + ", " + dp.getPort() + ": "+ new String(dp.getData(), 0, dp.getLength());
//		                System.out.println(rcvd);
//		            }
//		            catch (SocketTimeoutException e) {
//		                // timeout exception.
//		                System.out.println("Timeout reached!!! " + e);
//		                s.close();
//		            }
//		        }
			
			
			
			//}end while
			
			
			
			
		}
		
		//after we receive shutdown from gui
		//finish off all active connections
		
		
		
		
		
		
		//cleanup
		
		return null;
	}
	
	public void stopAccepting(){
		accepting = false;
	}

}
