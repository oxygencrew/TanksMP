import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


public class Server_Thread implements Runnable 
{
	protected Socket clientSocket = null;
	//protected Socket connectionSocket = null;
	
	String clientSentence;
	String capitalizedSentence;
	
	public Server_Thread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}
	
	public void run()
	{		
		try
		{
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			JOptionPane.showMessageDialog(null, "Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}
		
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
