/**
 * 
 */
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;
/**
 * @author justin
 *
 */
public class TanksServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		ServerSocket serverSocket = new ServerSocket(9000);
		
		while(true)
		{
			Socket connectionSocket = serverSocket.accept();
			System.out.println("client connected");
			DataInputStream textFromClient = new DataInputStream(connectionSocket.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			System.out.println(reader.readLine());
			serverSocket.close();
//			ObjectInputStream fromClient = new ObjectInputStream(connectionSocket.getInputStream());
//			Tank tank = (Tank) fromClient.readObject();
//			JOptionPane.showMessageDialog(null, tank.getPos());
		}
	}

}
