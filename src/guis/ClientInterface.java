package guis;
import javax.swing.*;

import starTrekReference.*;

import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientInterface extends JFrame{
	
	private final String ENGAGE_TEXT = "Engage", EXIT_TEXT = "Exit", UPLOAD = "Upload", DOWNLOAD = "Download";
	private String filename;
	private String serverIP;
	private int serverPort;
	private boolean download;
	private int rate;
	private JButton engageB, exitB;
	private JLabel fileNameL, serverIPL, serverPortL, rateL, upDownL;
	private JTextField fileNameTF, serverIPTF, serverPortTF, rateTF;
	private JComboBox upDownCB;
	private JTextArea messageTA;
	private JScrollPane messageSP;
	private JPanel buttonP, mainP;
	private ButtonHandler handler = new ButtonHandler();
	private TransporterModule tpHelper = new TransporterModule();

	public ClientInterface(String wormholeIP, int wormholePort){
		super();
		
		//create the GUI
		
		//JLabels
		fileNameL = new JLabel("Filename:");
		serverIPL = new JLabel("Server IP:");
		serverPortL = new JLabel("Server Port:");
		rateL = new JLabel("Maximum Transfer Rate:");
        upDownL = new JLabel("Upload/Download");
		
		//JTextFields
		fileNameTF = new JTextField();
		serverIPTF = new JTextField();
		serverPortTF = new JTextField();
		rateTF = new JTextField();
		
		//JComboBox
		String upDownOptions[] = {UPLOAD, DOWNLOAD};
		upDownCB = new JComboBox<String>(upDownOptions);
		
		//JTextPane
		messageTA = new JTextArea(5, 20);
		messageTA.setEditable(false);
		messageTA.setText("Welcome");
		messageSP = new JScrollPane(messageTA);
		
		//JButtons
		engageB = new JButton(ENGAGE_TEXT);
		engageB.addActionListener(handler);
		exitB = new JButton(EXIT_TEXT);
		exitB.addActionListener(handler);
		
		//JPanels
		buttonP = new JPanel();
		buttonP.setLayout(new GridLayout(1,2));
		buttonP.add(engageB);
		buttonP.add(exitB);
		
		mainP = new JPanel();
		LayoutManager mainGrid = new GridLayout(11,1, 3, 3);
		mainP.setLayout(mainGrid);
        mainP.add(fileNameL);
        mainP.add(fileNameTF);
        mainP.add(serverIPL);
        mainP.add(serverIPTF);
        mainP.add(serverPortL);
        mainP.add(serverPortTF);
        mainP.add(rateL);
        mainP.add(rateTF);
        mainP.add(upDownL);
        mainP.add(upDownCB);
        mainP.add(buttonP);
		
		//window properties
		setTitle("SCOTTY File Transfer Client");
        setSize(300, 400);
        LayoutManager mainLayout = new BorderLayout();
        setLayout(mainLayout);
        this.add(mainP, BorderLayout.CENTER);
        this.add(messageSP, BorderLayout.SOUTH);
        
        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
		
	}
	
	private void connect() {
		String temp;
		try {
			filename = fileNameTF.getText();
			if (filename.equals("")) {
				throw new Exception("Filename cannot be empty!");
			}

			serverIP = serverIPTF.getText();
			if (serverIP.equals("")) {
				throw new Exception("Server IP cannot be empty!");
			}

			temp = serverPortTF.getText();
			if (temp.equals("")) {
				throw new Exception("Server Port cannot be empty!");
			}
			serverPort = Integer.parseInt(temp);

			temp = rateTF.getText();
			if (temp.equals("")) {
				throw new Exception("Rate cannot be empty!");
			}
			rate = Integer.parseInt(rateTF.getText());
			download = (upDownCB.getSelectedItem() == DOWNLOAD);
			System.out.printf(
					"Filename:    	%s%n" + "Server IP:   	%s%n" + "Server Port: 	%-5d%n"
							+ "Maximum rate: 	%-5d ms%n" + "Mode:		%s%n",
					filename, serverIP, serverPort, rate, upDownCB.getSelectedItem());
		} catch (Exception e) {
			messageTA.setText("Error encountered:\n" + e.getMessage());
		}
	}

	// action listener for buttons
	class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == engageB){
				connect();
			}
			else if (e.getSource() == exitB) {
				System.exit(0);
			}
		}
	}

}
