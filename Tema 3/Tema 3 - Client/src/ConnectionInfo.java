/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionInfo {
	Socket socket = null;

	PrintWriter toServer = null;
	BufferedReader fromServer = null;

	public ConnectionInfo(Socket socket, PrintWriter toServer, BufferedReader fromServer) {
		super();
		this.socket = socket;
		this.toServer = toServer;
		this.fromServer = fromServer;
	}
}
