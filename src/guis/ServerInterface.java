package guis;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import starTrekReference.ServerWorker;

public class ServerInterface extends JFrame{

	JButton exitB;
	JScrollPane messageP;
	JTextArea messageTA;
	ServerWorker worker;
	
	public ServerInterface (){
		
		exitB = new JButton("Exit");
		exitB.addActionListener(new buttonH());
		messageTA = new JTextArea();
		messageTA.setEditable(false);
		messageP = new JScrollPane(messageTA);
		
		setLayout(new BorderLayout());
		
		add(messageP, BorderLayout.CENTER);
		add(exitB, BorderLayout.SOUTH);
		
		setResizable(false);
		setSize(500, 300);
		setVisible(true);
		
		worker = new ServerWorker();
		worker.execute();
		
	}
	
	class buttonH implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			//stop accepting connection requests, finish all connections and exit
			worker.stopAccepting();
			
			
			
			
			
			
			System.exit(0);
		}
		
	}
	
	
}
