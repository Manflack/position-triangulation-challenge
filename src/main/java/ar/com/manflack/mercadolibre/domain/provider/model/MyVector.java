package ar.com.manflack.mercadolibre.domain.provider.model;

import static java.lang.Math.*;

import java.io.Serializable;

public final class MyVector implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final MyVector NULL = new MyVector(0, 0);
	public static final MyVector X = new MyVector(1, 0);
	public static final MyVector Y = new MyVector(0, 1);

	private final double x;
	private final double y;

	public MyVector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public MyVector add(MyVector a)
	{
		return new MyVector(x + a.x, y + a.y);
	}

	public MyVector sub(MyVector a)
	{
		return new MyVector(x - a.x, y - a.y);
	}

	public MyVector neg()
	{
		return new MyVector(-x, -y);
	}

	public MyVector scale(double a)
	{
		return new MyVector(a * x, a * y);
	}

	public double dot(MyVector a)
	{
		return x * a.x + y * a.y;
	}

	public double modSquared()
	{
		return dot(this);
	}

	public double mod()
	{
		return sqrt(modSquared());
	}

	public MyVector normalize()
	{
		return scale(1 / mod());
	}

	public MyVector rotPlus90()
	{
		return new MyVector(-y, x);
	}

	public MyVector rotMinus90()
	{
		return new MyVector(y, -x);
	}

	public double angle()
	{
		return atan2(y, x);
	}

	public static MyVector fromAngle(double ang)
	{
		return new MyVector(cos(ang), sin(ang));
	}

	public static MyVector fromPolar(double ang, double mod)
	{
		return new MyVector(mod * cos(ang), mod * sin(ang));
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
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
		MyVector other = (MyVector) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "(" + x + ", " + y + ")";
	}

}