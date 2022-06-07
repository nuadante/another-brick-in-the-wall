import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Ball extends GameObject{

	public Ball(int x, int y, int d)
	{
		super(x, y, d, d);
	}

	public void bounce(GameObject other, Point contactPoint)
	{
		Point[] pointCorners = new Point[4];
		pointCorners[0] = new Point(other.getPos().x, other.getPos().y);
		pointCorners[1] = new Point(other.getPos().x + other.width, other.getPos().y);
		pointCorners[2] = new Point(other.getPos().x, other.getPos().y + other.height);
		pointCorners[3] = new Point(other.getPos().x + width, other.getPos().y + other.height);

		Point corner = null;

		for(int i = 0; i < 4; i++)
		{
			if (contactPoint.equals(pointCorners[i]))
			{
				corner = pointCorners[i];
				break;
			}
		}

		if (corner == null)
		{
			if(contactPoint.y == other.getPos().y ||
					contactPoint.y == other.getPos().y + other.getHeight()){
				diffY *= -1;
			}
			else if(contactPoint.x == other.getPos().x + other.getWidth() ||
					contactPoint.x == other.getPos().x){
				diffX *= -1;
			}
		}
	}

	public Point overlapPoint(GameObject other)
	{
		Point overlapPoint = null;

		Point otherTopLeft = other.getPos();
		int otherLeft = otherTopLeft.x;
		int otherTop = otherTopLeft.y;
		int otherRight = otherLeft + other.width;
		int otherBottom = otherTop + other.height;



		if (other instanceof Ball)
		{

		}
		else
		{
			Point thisCenter = this.getCenter();
			double radius = this.getWidth() / 2;
			int closestX = (int) clamp(thisCenter.x, otherLeft, otherRight);
			int closestY = (int) clamp(thisCenter.y, otherTop, otherBottom);

			double distanceX = thisCenter.x - closestX;
			double distanceY = thisCenter.y - closestY;

			double distanceSquared = (distanceX * distanceX) + (distanceY * distanceY);

			if (distanceSquared <= (radius * radius)){
				overlapPoint = new Point(closestX, closestY);
			}
		}

		return overlapPoint;
	}

	private double clamp(double value, double min, double max){
		return Math.max(min, Math.min(max, value));
	}

	public void move(){
		x += diffX;
		y -= diffY;
	}

	public void paint(Graphics page)
	{
		page.setColor(Color.DARK_GRAY);
		page.fillOval(x, y, width, height);
	}
}
