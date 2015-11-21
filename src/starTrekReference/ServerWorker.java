package starTrekReference;

import javax.swing.SwingWorker;

public class ServerWorker extends SwingWorker {

	boolean accepting;
	int engagedConnections;
	
	public ServerWorker () {
		accepting = true;
		engagedConnections = 0;
	}
	
	@Override
	protected Object doInBackground() throws Exception {
		// TODO This is where the server logic will go
		while (accepting){//server is accepting new connections
			
			
			
		}
		
		//cleanup
		
		return null;
	}
	
	public void stopAccepting(){
		accepting = false;
	}

}
