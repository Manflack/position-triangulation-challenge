package ar.com.manflack.mercadolibre.domain.model;

public class PointFixture
{
	public static final double x = 0;
	public static final double y = 0;

	public static Point withDefaults()
	{
		return new Point(x, y);
	}
}
