package ar.com.manflack.mercadolibre.domain.model;

import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.INVALID_RADII;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.INVALID_RADII_MSG;

import java.util.Arrays;
import java.util.List;

import ar.com.manflack.mercadolibre.domain.exception.UtilityException;

public class Circle
{
	private double x;

	private double y;

	private double r;

	public Circle(double x, double y, double r) throws UtilityException
	{
		if (!(r > 0))
			throw new UtilityException(INVALID_RADII, INVALID_RADII_MSG);
		this.x = x;
		this.y = y;
		this.r = r;
	}

	public Circle(Point point, double r) throws UtilityException
	{
		if (!(r > 0))
			throw new UtilityException(INVALID_RADII, INVALID_RADII_MSG);
		this.x = point.getX();
		this.y = point.getY();
		this.r = r;
	}

	public List<Point> intersections(Circle c)
	{
		Point P0 = new Point(x, y);
		Point P1 = new Point(c.x, c.y);

		double d, a, h;

		d = P0.distance(P1);
		a = (r * r - c.r * c.r + d * d) / (2 * d);
		h = Math.sqrt(r * r - a * a);

		Point P2 = P1.sub(P0).scale(a / d).add(P0);

		double x3, y3, x4, y4;

		x3 = P2.getX() + h * (P1.getY() - P0.getY()) / d;
		y3 = P2.getY() - h * (P1.getX() - P0.getX()) / d;
		x4 = P2.getX() - h * (P1.getY() - P0.getY()) / d;
		y4 = P2.getY() + h * (P1.getX() - P0.getX()) / d;

		Point P3 = new Point(x3, y3);
		Point P4 = new Point(x4, y4);

		return Arrays.asList(P3, P4);
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

	public double getR()
	{
		return r;
	}

	public void setR(double r)
	{
		this.r = r;
	}

}
