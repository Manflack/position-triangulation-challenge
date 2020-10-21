package ar.com.manflack.mercadolibre.domain.model;

import ar.com.manflack.mercadolibre.domain.exception.UtilityException;

public class CircleFixture
{
	public static final double x = 1;
	public static final double y = 1;
	public static final double r = 1;

	public static Circle withDefaults() throws UtilityException
	{
		return new Circle(x, y, r);
	}
}
