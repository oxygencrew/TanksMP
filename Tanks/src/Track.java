import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * 
 */

/**
 * @author justin
 *
 */
public class Track 
{
	private Area track;
	private double m_angle;
	private double m_origin;
	private Point2D m_pos = new Point2D.Double();
	private Color m_color;
	private int m_alpha = 255;
	
	public Track(double a_angle, Point2D a_pos)
	{
		Area a = new Area();
		a.add(new Area(new Rectangle2D.Double(a_pos.getX(), a_pos.getY(), 20, 10)));
		a.add(new Area(new Rectangle2D.Double(a_pos.getX() + 30, a_pos.getY(), 20, 10)));
		track = a;
		setAngle(a_angle);
		setOrigin(a_angle);
		setPos(a_pos);
		setColor(new Color(0, 0, 0, 255));
	}

	public Point2D getPos() {
		return m_pos;
	}

	public void setPos(Point2D a_pos) {
		double x = a_pos.getX();
		double y = a_pos.getY();
		
		m_pos.setLocation(x, y);
	}

	public Area getTrack() {
		return track;
	}

	public void setTrack(Area a_track) {
		track = a_track;
	}

	public double getAngle() {
		return m_angle;
	}

	public void setAngle(double a_angle) {
		m_angle = a_angle;
	}
	
	public void update()
	{
		if(m_alpha > 0)
		{
			m_alpha--;
		}
		setColor(new Color(0, 0, 0, m_alpha));
	}

	public Color getColor() {
		return m_color;
	}

	public void setColor(Color a_color) {
		m_color = a_color;
	}

	public double getOrigin() {
		return m_origin;
	}

	public void setOrigin(double a_origin) {
		m_origin = a_origin;
	}
}
