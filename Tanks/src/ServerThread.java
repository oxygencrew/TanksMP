import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;


public class ServerThread implements Runnable 
{
	protected Socket clientSocket = null;
	//protected Socket connectionSocket = null;

	String clientSentence;
	String capitalizedSentence;

	public ServerThread(Socket clientSocket)
	{
		this.clientSocket = clientSocket;
	}

	public void run()
	{		
		while(true)
		{
			try
			{
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
				clientSentence = inFromClient.readLine();
				capitalizedSentence = clientSentence.toUpperCase() + '\n';
				outToClient.writeBytes(String.valueOf(TanksServer.currentConnections));
				//outToClient.writeInt(TanksServer.currentConnections);
			}

			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
