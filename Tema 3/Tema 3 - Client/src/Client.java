/*
 * Aplicatii Integrate pentru Intreprinderi
 * 
 * Stefan-Dobrin Cosmin
 * 342C4
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import aii.arhiva.Arhiva;
import aii.jurnal.Jurnal;
import aii.rad.RegistruActivitatiDidactice;

/**
 * The Class Client.
 */
public class Client {

	private static final int JURNAL = 0;
	private static final int ARHIVA = 1;
	private static final int RAD = 2;

	
	
	private static void testingUnit(int mode,BufferedReader fromSocket, PrintWriter toSocket) throws IOException
	{
		//TEST Jurnal
		if(mode==ARHIVA)
		{
			String response;
			String message;
			
			//TEST Disciplina
			message="stabilire_plan_de_invatamant#6#" +
					"adaugare#987~Inteligenta Artificiala~Obligatoriu~5~6~Examen~4~1~0#" +
					"stergere#987#" +
					"editare#987~Aplicatii Integrate pentru Intreprinderi~Optional~5~6~Colocviu~4~1~0#" +
					"stergere#988#" +
					"adaugare#988~Inteligenta Artificiala~Obligatoriu~5~6~Examen~4~1~0#" +
					"editare#988~Aplicatii Integrate pentru Intreprinderi~Optional~5~6~Colocviu~4~1~0";
					
					
			toSocket.println(message);
			System.out.println("Test 1 "+mode+":"+message);
			response=fromSocket.readLine();
			System.out.println("Raspuns "+mode+":"+response);
		}
	}
	
	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		Integer mode=ARHIVA;
		BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		String line;
		Boolean done = false;
		String adresa = "";
		int port = 0;

		System.out.println("Introduceti componenta cu care doriti sa comunicati:"
				+ "0 - Jurnal, 1 - Arhiva, 2 - Registru Activitati Didactice");

		while (!done) {
			try {
				System.out.print("> ");
				//TODO: de reparat
				//line = keyboardInput.readLine();
				line="1";
				switch (Integer.parseInt(line)) {
				case JURNAL:
					mode = JURNAL;
					System.out.println("Mod selectat: Jurnal");
					port = Jurnal.SERVER_PORT;
					adresa = Jurnal.SERVER_ADDRESS;
					done = true;
					break;
				case ARHIVA:
					mode = ARHIVA;
					System.out.println("Mod selectat: Arhiva");
					port = Arhiva.SERVER_PORT;
					adresa = Arhiva.SERVER_ADDRESS;
					done = true;
					break;
				case RAD:
					mode = RAD;
					System.out.println("Mod selectat: RegistruActivitatiDidactice");
					port = RegistruActivitatiDidactice.SERVER_PORT;
					adresa = RegistruActivitatiDidactice.SERVER_ADDRESS;
					done = true;
					break;
				default:
					System.err.println("Mod necorespunzator. Incercati din nou:");
				}
			} catch (Exception e1) {
				System.err.println("Mod necorespunzator. Incercati din nou:");
				e1.printStackTrace();
			}
		}

		try {
			socket = new Socket(adresa, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost.");
			System.exit(-1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: localhost.");
			System.exit(-1);
		}

		String fromServer;
		String fromClient;
		
		//Testing
		try {
			testingUnit(mode, in, out);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		try {
			do {
				fromClient = keyboardInput.readLine();
				if (fromClient != null) {
					System.out.println("Client: " + fromClient);
					out.println(fromClient);
				}
				fromServer = in.readLine();
				if (fromServer == null)
					break;
				System.out.println("Server: " + fromServer);
				if (fromServer.equals("end_connection"))
					break;
			} while (fromServer != null);

			out.close();
			in.close();
			keyboardInput.close();
			socket.close();
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: localhost.");
			System.exit(-1);
		}
	}
}
