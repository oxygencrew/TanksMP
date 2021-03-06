import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.Timer;


public class TanksModel 
{
	
	
	private ArrayList<Tank> m_tanks;
	private Socket clientSocket;
	
	private BufferedReader fromServer;
	TanksController m_controller;
	private ObjectOutputStream outToServer;
	
	private Tank player;
	
	private ActionListener tickRate = new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent a_e) 
		{
			
			System.out.println("sent");
			try
			{
				outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
					System.out.println(player);
					outToServer.writeObject(player);
			}
			catch(Exception e)
			{
				System.out.println("hij doet het niet | " + e.getLocalizedMessage());
			}
		}
	};
	
	Timer t = new Timer(5000, tickRate);
	
	public TanksModel(TanksController a_controller) throws Exception
	{
		setTanks(new ArrayList<Tank>());
		m_controller = a_controller;
		player = new Tank(100, 100, 75, 50);
		m_tanks.add(player);
		InetAddress locIP = InetAddress.getByName("localhost");
		System.out.println(locIP);
		clientSocket = new Socket(locIP, 9000);
		fromServer = new BufferedReader(new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
		t.start();
	}

	public void sendSomething(String test)
	{
		try {
			outToServer.writeBytes(test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Tank> getTanks() {
		return m_tanks;
	}

	public void setTanks(ArrayList<Tank> a_tanks) {
		m_tanks = a_tanks;
	}

	/**
	 * @param a_controller
	 */
	public void setController(TanksController a_controller) 
	{
		m_controller = a_controller;
	}
	
	public void update()
	{
		try
		{
			String sentence = fromServer.readLine();
			System.out.println(sentence);
		}
		catch(Exception e)
		{
			
		}
		
		ArrayList<Projectile> tests = new ArrayList<Projectile>();
		for(Tank t : m_tanks)
		{
			tests.addAll(t.getProjectiles());
			t.update();
		}
		
		for(Projectile p : tests)
		{
			if(p.isAlive())
			{
				for(Projectile p1 : tests)
				{
					if (p1.isAlive()) {
						if (!p1.equals(p)) {
							if (p.getPos().distance(p1.getPos()) < 10) {
								p.setAlive(false);
								p1.setAlive(false);
								System.out.println("Collisions");
							}
						}
					}
				}
			}
		}
	}

	public Socket getClientSocket() 
	{
		return clientSocket;
	}

	public BufferedReader getFromServer() 
	{
		return fromServer;
	}

	public ObjectOutputStream getToServer() {
		return outToServer;
	}

}
