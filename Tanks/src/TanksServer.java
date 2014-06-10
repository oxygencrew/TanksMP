import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class TanksServer implements Runnable
{
	JFrame frame = new JFrame();
	JPanel main = new JPanel(new BorderLayout());
	JTextField txt = new JTextField("");

	protected static int currentConnections = 0;
	protected int maxConnections = 10;
	
	protected int serverPort = 9000;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped    = false;
	protected Thread runningThread= null;

	public TanksServer(int port)
	{
		this.serverPort = port;
	}
	
	public void run()
	{
		initializeWindow();
		synchronized (this) 
		{
			this.runningThread = Thread.currentThread();
		}

		openServerSocket();
		
		while(!isStopped())
		{
			if(currentConnections < maxConnections)
			{
				Socket clientSocket = null;
				try 
				{
					clientSocket = serverSocket.accept();
				}

				catch (IOException e) 
				{
	                if(isStopped()) 
	                {
	                    System.out.println("Server Stopped.") ;
	                    return;
	                }
	                
	                throw new RuntimeException("Error accepting client connection", e);
	            }

				new Thread(new ServerThread(clientSocket)).start();
			}
			
			else
			{
				System.out.println("Server is full");
			}

		}
		
		System.out.println("Server stopped.");
	}

	private void openServerSocket() 
	{
        try 
        {
            this.serverSocket = new ServerSocket(this.serverPort);
        } 
        
        catch (IOException e) 
        {
            throw new RuntimeException("Cannot open port 9000", e);
        }
    }
	
	private synchronized boolean isStopped() 
	{
        return this.isStopped;
    }
	
	public synchronized void stop()
	{
        this.isStopped = true;
        
        try 
        {
            this.serverSocket.close();
        } 
        
        catch (IOException e) 
        {
            throw new RuntimeException("Error closing server", e);
        }
    }
	
	public void initializeWindow()
	{
		frame.setTitle("chattings");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(new Dimension(640, 480));
		frame.setBackground(Color.green);
		txt.setPreferredSize(new Dimension(200, 25));
		frame.add(main);
		main.add(txt, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		TanksServer server = new TanksServer(9000);
		new Thread(server).start();

		/*try {
		    Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		System.out.println("Stopping Server");
		server.stop();*/
	}
	
}
