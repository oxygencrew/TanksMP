import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TanksGUI extends JPanel
{
	private TanksModel m_model;
	private TanksController m_controller;
	private Image bg;
	
	public TanksGUI(final TanksModel a_model, TanksController a_controller)
	{
		try
		{
			bg = ImageIO.read(new File("SUNNY-Assorted-Ground.bmp"));
		}
		catch(Exception e)
		{
			
		}
		m_model = a_model;
		m_controller = a_controller;
		JFrame main = new JFrame();
		main.setSize(512, 512);
		main.add(this);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		main.addWindowListener(new java.awt.event.WindowAdapter() {
//			public void windowClosing(WindowEvent winEvt)
//			{
//				try 
//				{
//					a_model.getClientSocket().close();
//				} 
//				catch (IOException e) 
//				{
//					
//				}
//				System.exit(0);
//			}
//		});
		
		
		main.addMouseMotionListener(a_controller);
		main.addKeyListener(a_controller);
		main.addMouseListener(a_controller);
	}
	
	@Override
	protected void paintComponent(Graphics a_g) 
	{
		super.paintComponent(a_g);
		Graphics2D g2 = (Graphics2D) a_g;
		
		for(int x = 0; x <= 1920 / 512; x++)
		{
			g2.drawImage(bg, x * 512, 0, null);
			g2.drawImage(bg, x * 512, 512, null);
		}
		
		//draw tank body
		for(Tank t : m_model.getTanks())
		{
			
			AffineTransform at = new AffineTransform();
			AffineTransform old = g2.getTransform();
			for(Track s : t.getTracks())
			{
				g2.setColor(s.getColor());
//				at.setToRotation(Math.toRadians(t.getDegrees()), t.getPos().getX() + (t.getSize().getX() / 2), t.getPos().getY() + (t.getSize().getY() / 2));
				at.setToRotation(Math.toRadians(s.getAngle() + 90), s.getPos().getX() + 10, s.getPos().getY() + 5);
				g2.setTransform(at);
				g2.draw(s.getTrack());
			}
			at.setToRotation(Math.toRadians(t.getDegrees()), t.getPos().getX() + (t.getSize().getX() / 2), t.getPos().getY() + (t.getSize().getY() / 2));
			g2.setTransform(at);
			g2.draw(t.getBodyShape());
			g2.setColor(new Color(49, 122, 62));
			g2.fill(t.getBodyShape());
			double angle = Math.atan2(m_controller.getMouse().getY() - (t.getPos().getY() + t.getSize().getY() / 2), m_controller.getMouse().getX() - (t.getPos().getX() + t.getSize().getX() / 2));
			angle -= Math.toRadians(90);
			at.setToRotation(angle, t.getPos().getX() + (t.getSize().getX() / 2), t.getPos().getY() + (t.getSize().getY() / 2));
			g2.setTransform(at);
			g2.fill(t.getGunShape());
			g2.fill(new Ellipse2D.Double(t.getPos().getX() + (t.getSize().getX() / 2) - 20, t.getPos().getY() + (t.getSize().getY() / 2) - 20, 40, 40));
			g2.setColor(Color.black);
			g2.draw(t.getGunShape());
			g2.draw(new Ellipse2D.Double(t.getPos().getX() + (t.getSize().getX() / 2) - 20, t.getPos().getY() + (t.getSize().getY() / 2) - 20, 40, 40));
			for(Projectile p : t.getProjectiles())
			{
				if(p.isAlive())
				{
					at.setToRotation(p.getAngle(),  p.getOrigin().getX(), p.getOrigin().getY());
					at.rotate(Math.toRadians(-90), p.getPos().getX() + 10, p.getPos().getY());
					g2.setTransform(at);
					g2.draw(p.getBody());
					g2.setColor(Color.darkGray);
					g2.fill(p.getBody());
				}
			}
			g2.setTransform(old);
		}
	}

	/**
	 * 
	 */
	public void update() {
		updateUI();
		m_controller.update();
		m_model.update();
	}
}
