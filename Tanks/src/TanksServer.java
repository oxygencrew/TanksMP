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
			ObjectInputStream fromClient = new ObjectInputStream(connectionSocket.getInputStream());
			Tank tank = (Tank) fromClient.readObject();
			JOptionPane.showMessageDialog(null, tank.getPos());
		}
	}

}
