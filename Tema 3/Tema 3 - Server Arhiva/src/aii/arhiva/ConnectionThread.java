/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
package aii.arhiva;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import aii.protocol.MessageParser;
import aii.protocol.MessageStructure;
import aii.protocol.MessageStructure.Type;

/**
 * The Class ConnectionThread.
 */
public class ConnectionThread extends Thread {

	/** The socket. */
	private Socket socket = null;

	/** The arhiva. */
	private Arhiva arhiva;
	
	/** The Constant DEBUG. */
	public static final Boolean DEBUG=true;
	
	/** The done. */
	private Boolean done=false;
	
	/**
	 * Debug message printer.
	 *
	 * @param string the string
	 */
	private static void debug(String string)
	{
		if(DEBUG)
			System.out.println("[DEBUG][Thread "+Thread.currentThread().getId()+"] "+string);
	}

	/**
	 * Instantiates a new connection thread.
	 * 
	 * @param socket
	 *            the socket
	 */
	public ConnectionThread(Socket socket) {
		super("ConnectionThread");
		this.socket = socket;
		this.arhiva = new ArhivaServer();
	}

	/**
	 * Analyze a message received from the client and returns the response
	 * message.
	 * 
	 * @param message
	 *            the message
	 * @return the response message string that is sent to the client.
	 */
	private String analyze(String message) {

		//Get message structure
		MessageStructure structure=MessageParser.getMessageStructure(message);
		debug("New message structure: "+structure);
		
		//No corresponding message structure was found
		if(structure==null)
		{
			System.err.println("Unrecognized message type!");
			return "Unkown or Illegal message";
		}
		
		//It's an exit message
		if(structure.type.equals(Type.EXIT))
		{
			done=true;
			return "Closing connection";
		}
		
		//It's not addressed to Arhiva
		if(!structure.type.equals(Type.ARHIVA))
		{
			System.err.println("Message is not for this component!");
			return "Illegal message for ARHIVA!";
		}
		

		
		
		//Process the message an return the message to the client
		return arhiva.processMessage(message, structure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		debug("New client connected");
		try {
			PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));

			String message;

			// Read new messages from clients
			while ((message = fromClient.readLine()) != null) {
				// Analyze and process the message and then send response
				debug("New message from Client:" + message);
				toClient.println(analyze(message));
				if (done)
					break;
			}

			// End the connection
			debug("Closing connection with client");
			toClient.close();
			fromClient.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		boolean listening = true;
		
		// Prepare the port
		int port = Arhiva.SERVER_PORT;

		// Create the socket for new connections
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(-1);
		}
		ConnectionThread.debug("Created listening socket. Listening...");
		
		// Listen to new connection requests
		while (listening) {
			try {
				// If a request was made, start a new thread to handle it
				new ConnectionThread(serverSocket.accept()).start();
			} catch (Exception e) {
				System.err.println("Error while creating the socket.");
				System.exit(-1);
			}
		}

		// Close the socket
		ConnectionThread.debug("Closing the socket");
		try {
			serverSocket.close();
		} catch (Exception e) {
			System.err.println("Error while closing the socket.");
			System.exit(-1);
		}
	}
}
