import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class Tank implements Serializable
{
	private Point2D m_pos;
	private double m_degrees;
	private double m_aimDir;
	private Point2D m_size;
	private Shape m_bodyShape;
	private Shape m_gunShape;
	private transient ArrayList<Projectile> projectiles;
	private ArrayList<Track> tracks;
	private int frame = 0;
	
	public ArrayList<Track> getTracks() {
		return tracks;
	}

	public String toString()
	{
		String s = " pos = " + m_pos + "\n degrees = " + m_degrees + "\n aimDir = " + m_aimDir;
		
		return s;
	}
	
	public void update()
	{
		Iterator<Track> it = tracks.iterator();
		while(it.hasNext())
		{
			Track s = it.next();
			s.update();
			if(tracks.size() > 10)
			{
				it.remove();
			}
		}
		
		Iterator<Projectile> shells = projectiles.iterator();
		while(shells.hasNext())
		{
			Projectile p = shells.next();
			if(!p.isAlive())
			{
				shells.remove();
			}
		}
	}
	
	public Tank(Point2D a_pos, Point2D a_size)
	{
		setPos(a_pos);
		setSize(a_size);
		m_degrees = 0;
		m_aimDir = 0;
		setBodyShape(new Rectangle2D.Double(a_pos.getX(), a_pos.getY(), a_size.getX(), a_size.getY()));
		setGunShape(new Rectangle2D.Double(a_pos.getX() + (a_size.getX() / 2) - 5, a_pos.getY() + (a_size.getY() / 2) + 20, 10, 50));
		projectiles = new ArrayList<Projectile>();
		tracks = new ArrayList<Track>();
	}
	
	public Tank(int a_X, int a_Y, int a_width, int a_height)
	{
		setPos(new Point2D.Double(a_X, a_Y));
		setSize(new Point2D.Double(a_width, a_height));
		m_degrees = 0;
		m_aimDir = 0;
		setBodyShape(new Rectangle2D.Double(a_X, a_Y, a_width, a_height));
		setGunShape(new Rectangle2D.Double(m_pos.getX() + (m_size.getX() / 2) - 5, m_pos.getY() + (m_size.getY() / 2) + 20, 10, 50));
		projectiles = new ArrayList<Projectile>();
		tracks = new ArrayList<Track>();
	}

	public void move(Point2D movement)
	{
		double degX = (Math.cos(Math.toRadians(m_degrees)) * movement.getY());
		double degY = (Math.sin(Math.toRadians(m_degrees)) * movement.getY());
		double x = m_pos.getX() + degX;
		double y = m_pos.getY() + degY;
		m_pos.setLocation(x, y);
		recalcShape();
		if(frame > 20)
		{
			tracks.add(new Track(m_degrees, m_pos));
			frame = 0;
		}
		frame++;
	}
	
	private void recalcShape()
	{
		setBodyShape(new Rectangle2D.Double(m_pos.getX(), m_pos.getY(), m_size.getX(), m_size.getY()));
		setGunShape(new Rectangle2D.Double(m_pos.getX() + (m_size.getX() / 2) - 5, m_pos.getY() + (m_size.getY() / 2) + 20, 10, 50));
	}
	
	public Point2D getPos() 
	{
		return m_pos;
	}

	public void setPos(Point2D a_pos) 
	{
		m_pos = a_pos;
	}

	public double getDegrees() 
	{
		return m_degrees;
	}

	public void setDegrees(double a_degrees)
	{
		m_degrees = a_degrees;
	}

	public Point2D getSize() 
	{
		return m_size;
	}

	public void setSize(Point2D a_size) 
	{
		m_size = a_size;
	}

	public Shape getBodyShape() 
	{
		return m_bodyShape;
	}

	public void setBodyShape(Shape a_bodyShape) 
	{
		m_bodyShape = a_bodyShape;
	}

	public Shape getGunShape() 
	{
		return m_gunShape;
	}

	public void setGunShape(Shape a_gunShape) 
	{
		m_gunShape = a_gunShape;
	}

	/**
	 * @param a_mouse
	 */
	public void fireProjectile(Point2D a_mouse) 
	{
		double angle = Math.atan2(a_mouse.getY() - (getPos().getY() + getSize().getY() / 2), a_mouse.getX() - (getPos().getX() + getSize().getX() / 2));
		angle += Math.toRadians(90);
		projectiles.add(new Projectile(angle, this.getPos(), this.getPos()));
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectile> a_projectiles) {
		projectiles = a_projectiles;
	}
}
