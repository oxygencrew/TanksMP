/**
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
		
		JFrame frame = new JFrame();
		JPanel main = new JPanel(new BorderLayout());
		frame.setTitle("chattings");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(640, 480));
		frame.setBackground(Color.green);
		JTextField txt = new JTextField("");
		txt.setPreferredSize(new Dimension(200, 25));
		frame.add(main);
		main.add(txt, BorderLayout.SOUTH);

		frame.setVisible(true);
		String clientSentence;
		String capitalizedSentence;
		ServerSocket welcomeSocket = new ServerSocket(9000);
		Socket connectionSocket = welcomeSocket.accept();
		
		while(true)
		{
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence = inFromClient.readLine();
			txt.setText(clientSentence);
			JOptionPane.showMessageDialog(null, "Received: " + clientSentence);
			System.out.println("Received: " + clientSentence);
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			outToClient.writeBytes(capitalizedSentence);
		}
	}

}
