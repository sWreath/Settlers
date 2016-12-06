package Utilities;

import java.io.*;
import java.net.*;

public class ClientNetwork {
	private int port;
	private String hostIP;
	private Socket ServerConnection;
	private ListenThread Listener;
	
	public ClientNetwork(String HostIP, int HostPort){
		hostIP = HostIP;
		port = HostPort;
	}
	
	public void start() throws UnknownHostException, IOException{
		ServerConnection = new Socket(hostIP,port);
		Listener = new ListenThread(ServerConnection,0);
		Listener.start();
	}

	public static void main(String[] args) throws Exception {
		ClientNetwork Connection = new ClientNetwork("127.0.0.1", 3000);
		Connection.start();
		/*
		Socket sock = new Socket("127.0.0.1", 3000);
		// reading from keyboard (keyRead object)
		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
		// sending to client (pwrite object)
		OutputStream ostream = sock.getOutputStream(); 
		PrintWriter pwrite = new PrintWriter(ostream, true);

		// receiving from server ( receiveRead  object)
		//InputStream istream = sock.getInputStream();
		//BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		System.out.println("Start the chitchat, type and press Enter key");

		String receiveMessage, sendMessage;
		*/               
		/*while(true)
		{
			sendMessage = keyRead.readLine();  // keyboard reading
			pwrite.println(sendMessage);       // sending to server
			pwrite.flush();                    // flush the data
			if((receiveMessage = receiveRead.readLine()) != null) //receive from server
			{
				System.out.println(receiveMessage); // displaying at DOS prompt
			}         
		}*/               

	}

}
