package ar.com.manflack.mercadolibre.domain.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ar.com.manflack.mercadolibre.domain.exception.UtilityException;

public class CircleTest
{
	private Circle circle;

	@Test
	public void testInit() throws UtilityException
	{
		circle = CircleFixture.withDefaults();
		assertNotNull(circle.toString());
	}
}
