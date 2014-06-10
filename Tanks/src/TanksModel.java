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

/**
 * 
 */

/**
 * @author justin
 *
 */
public class TanksModel 
{
	private ActionListener tickRate = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent a_e) 
		{
			
			System.out.println("sent");
			try
			{
				outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
				for(Tank t : m_tanks)
				{
					System.out.println(t);
					outToServer.writeObject(t);
				}
			}
			catch(Exception e)
			{
				System.out.println("hij doet het niet | " + e.getLocalizedMessage());
			}
		}
	};
	
	Timer t = new Timer(1000, tickRate);
	
	private ArrayList<Tank> m_tanks;
	private Socket clientSocket;
	
	public ObjectOutputStream getToServer() {
		return outToServer;
	}

	private ObjectInputStream fromServer;
	TanksController m_controller;
	private ObjectOutputStream outToServer;
	
	public TanksModel(TanksController a_controller) throws Exception
	{
		setTanks(new ArrayList<Tank>());
		m_controller = a_controller;
		m_tanks.add(new Tank(100, 100, 75, 50));
		InetAddress locIP = InetAddress.getByName("localhost");
		System.out.println(locIP);
		clientSocket = new Socket(locIP, 9000);
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

	public ObjectInputStream getFromServer() 
	{
		return fromServer;
	}
}
