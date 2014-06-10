import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class TanksController implements MouseMotionListener, KeyListener, MouseListener
{
	private TanksModel m_model;
	private Point2D m_mouse;
	private boolean rightPressed;
	private boolean leftPressed;
	private boolean downPressed;
	private boolean upPressed;
	private Clip clip;
	
	public TanksController(TanksModel a_model)
	{
		setModel(a_model);
		m_mouse = new Point2D.Double(0, 0);
		try
		{
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("driving.wav"));
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public TanksModel getModel() {
		return m_model;
	}

	public Point2D getMouse() {
		return m_mouse;
	}

	public void update()
	{		
		if(upPressed)
		{
			m_model.getTanks().get(0).move(new Point2D.Double(0, -1));
		}
		if(downPressed)
		{
			m_model.getTanks().get(0).move(new Point2D.Double(0, 1));
		}
		if(leftPressed)
		{
			m_model.getTanks().get(0).setDegrees((m_model.getTanks().get(0).getDegrees() - 1)%360);
		}
		if(rightPressed)
		{
			m_model.getTanks().get(0).setDegrees((m_model.getTanks().get(0).getDegrees() + 1)%360);
		}
		for(Tank t : m_model.getTanks())
		{
			for(Projectile p : t.getProjectiles())
			{
				p.update();
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent a_e) 
	{
		
		
		
		int key = a_e.getKeyCode();
		
		if(key == KeyEvent.VK_W)
		{
			upPressed = true;
		}
		if(key == KeyEvent.VK_S)
		{
			downPressed = true;
		}
		if(key == KeyEvent.VK_A)
		{
			leftPressed = true;
		}
		if(key == KeyEvent.VK_D)
		{
			rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent a_e) 
	{
		int key = a_e.getKeyCode();
		
		if(key == KeyEvent.VK_W)
		{
			upPressed = false;
		}
		if(key == KeyEvent.VK_S)
		{
			downPressed = false;
		}
		if(key == KeyEvent.VK_A)
		{
			leftPressed = false;
		}
		if(key == KeyEvent.VK_D)
		{
			rightPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent a_e)
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent a_e) 
	{
		
	}

	@Override
	public void mouseMoved(MouseEvent a_e) 
	{
		setMouse(new Point2D.Double(a_e.getXOnScreen() - 9, a_e.getYOnScreen() - 31));
		
	}

	public void setModel(TanksModel a_model) {
		m_model = a_model;
	}

	public void setMouse(Point2D a_mouse) {
		m_mouse = a_mouse;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent a_e) 
	{
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
		public void mouseEntered(MouseEvent a_e) 
		{
			
		}
	
		@Override
		public void mouseExited(MouseEvent a_e) 
		{
			
		}
		
		@Override
		public void mousePressed(MouseEvent a_e) 
		{
			if(a_e.getButton() == 1)
			{
				m_model.getTanks().get(0).fireProjectile(m_mouse);
				try
			{
					m_model.getToServer().writeBytes("fire" + '\n');
				Clip clip = AudioSystem.getClip();
				AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("blast.wav"));
				clip.open(inputStream);
				clip.start();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent a_e) 
	{
		
	}
	
}
