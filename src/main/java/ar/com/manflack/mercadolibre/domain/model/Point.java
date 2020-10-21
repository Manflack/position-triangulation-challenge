package ar.com.manflack.mercadolibre.domain.model;

public class Point
{
	private double x;

	private double y;

	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Point(Circle circle)
	{
		this.x = circle.getX();
		this.y = circle.getY();
	}

	public Point()
	{
	}

	public Point sub(Point p2)
	{
		return new Point(x - p2.x, y - p2.y);
	}

	public Point add(Point p2)
	{
		return new Point(x + p2.x, y + p2.y);
	}

	public double distance(Point p2)
	{
		return Math.sqrt((x - p2.x) * (x - p2.x) + (y - p2.y) * (y - p2.y));
	}

	public Point normal()
	{
		double length = Math.sqrt(x * x + y * y);
		return new Point(x / length, y / length);
	}

	public Point scale(double s)
	{
		return new Point(x * s, y * s);
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "Point [x=" + x + ", y=" + y + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

}
