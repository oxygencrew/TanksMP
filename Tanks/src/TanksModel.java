import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author justin
 *
 */
public class TanksModel 
{
	private ArrayList<Tank> m_tanks;
	private static Socket clientSocket = null;
	private DataOutputStream toServer;
	public DataOutputStream getToServer() {
		return toServer;
	}

	public void setToServer(DataOutputStream a_toServer) {
		toServer = a_toServer;
	}

	private DataInputStream fromServer;
	TanksController m_controller;
	
	public TanksModel(TanksController a_controller) throws Exception
	{
		setTanks(new ArrayList<Tank>());
		m_controller = a_controller;
		m_tanks.add(new Tank(100, 100, 75, 50));
		clientSocket = new Socket("localhost", 9000);
		toServer = new DataOutputStream(clientSocket.getOutputStream());
		setFromServer(new DataInputStream(clientSocket.getInputStream()));
//		toServer = new ObjectOutputStream(clientSocket.getOutputStream());
//		fromServer = new ObjectInputStream(clientSocket.getInputStream());
		toServer.writeBytes("test");
		System.out.println("aoiefjAEFF");
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
//			try 
//			{
//				toServer.writeObject(t);
//			} 
//			catch (IOException e) 
//			{
//				
//			}
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

	public Socket getClientSocket() {
		return clientSocket;
	}

	public DataInputStream getFromServer() {
		return fromServer;
	}

	public void setFromServer(DataInputStream a_fromServer) {
		fromServer = a_fromServer;
	}
}
