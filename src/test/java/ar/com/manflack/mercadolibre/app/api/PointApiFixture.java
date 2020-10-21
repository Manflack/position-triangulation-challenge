package ar.com.manflack.mercadolibre.app.api;

public class PointApiFixture
{
	public static final double x = 0;
	public static final double y = 0;

	public static PointApi withDefaults()
	{
		return new PointApi(x, y);
	}
}
