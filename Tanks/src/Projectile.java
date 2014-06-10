import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.Serializable;


public class Projectile implements Serializable
{
	private double m_angle;
	private GeneralPath body;
	private Point2D m_pos;
	private Point2D m_origin;
	private double m_speed;
	private boolean m_alive;
	
	public double getAngle() {
		return m_angle;
	}

	public void setAngle(double a_angle) {
		m_angle = a_angle;
	}

	public GeneralPath getBody() {
		return body;
	}

	public void setBody(GeneralPath a_body) {
		body = a_body;
	}

	public Projectile(double a_angle, Point2D a_pos, Point2D a_origin)
	{
		m_alive = true;
		m_angle = a_angle;
		m_speed = 5;
		setOrigin(new Point2D.Double(a_origin.getX() + (75/2), a_origin.getY() + 25));
		m_pos = new Point2D.Double(a_pos.getX() + 27, a_pos.getY() - 55);;
		body = new GeneralPath();
		body.moveTo(m_pos.getX(), m_pos.getY() - 3);
		body.lineTo(m_pos.getX() + 10, m_pos.getY() - 3);
		body.lineTo(m_pos.getX() + 20, m_pos.getY());
		body.lineTo(m_pos.getX() + 10, m_pos.getY() + 3);
		body.lineTo(m_pos.getX(),  m_pos.getY() + 3);
		body.closePath();
	}

	private void recalcPath()
	{
		body = new GeneralPath();
		body.moveTo(m_pos.getX(), m_pos.getY() - 3);
		body.lineTo(m_pos.getX() + 10, m_pos.getY() - 3);
		body.lineTo(m_pos.getX() + 20, m_pos.getY());
		body.lineTo(m_pos.getX() + 10, m_pos.getY() + 3);
		body.lineTo(m_pos.getX(),  m_pos.getY() + 3);
		body.closePath();
	}
	
	public void update()
	{
		if(m_alive)
		{
			m_speed -= 0.05;
			
			if(m_speed <= 0)
			{
				m_speed = 0;
				setAlive(false);
			}
			
			
			double vX = Math.cos(Math.toRadians(m_angle - 90)) * m_speed;
			double vY = Math.sin(Math.toRadians(m_angle - 90)) * m_speed;
			
			double x = m_pos.getX() + vX;
			double y = m_pos.getY() + vY;
			
			m_pos.setLocation(x, y);
			recalcPath();
		}
	}
	
	public Point2D getPos() {
		return m_pos;
	}

	public void setPos(Point2D a_pos) {
		m_pos = a_pos;
	}

	public Point2D getOrigin() {
		return m_origin;
	}

	public void setOrigin(Point2D a_origin) {
		m_origin = a_origin;
	}

	public boolean isAlive() {
		return m_alive;
	}

	public void setAlive(boolean a_alive) {
		m_alive = a_alive;
	}
}
