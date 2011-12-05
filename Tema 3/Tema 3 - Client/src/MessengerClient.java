import java.net.*;
import java.io.*;

public class MessengerClient
{
    public static void main (String[] args)
    {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String adresa = new String(args[0]);
        int port = Integer.parseInt(args[1]);

        try
        {
            socket = new Socket(adresa, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host: localhost.");
            System.exit(-1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(-1);
        }

        BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromClient;

        try
        {
            do
            {
                fromClient= keyboardInput.readLine();
                if (fromClient != null)
                {
                    System.out.println("Client: " + fromClient);
                    out.println(fromClient);
                }
                fromServer = in.readLine();
                if (fromServer == null)
                    break;
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("end_connection"))
                    break;
            }
            while (fromServer != null);

            out.close();
            in.close();
            keyboardInput.close();
            socket.close();
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(-1);
        }
    }
}
