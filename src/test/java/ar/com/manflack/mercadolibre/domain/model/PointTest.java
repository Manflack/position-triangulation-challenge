package ar.com.manflack.mercadolibre.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import ar.com.manflack.mercadolibre.app.api.PointApiFixture;

public class PointTest
{
	private Point point;

	@Test
	public void testInit()
	{
		point = PointFixture.withDefaults();
		assertNotNull(point.toString());
	}

	@Test
	public void testSet()
	{
		point = new Point();
		point.setX(PointApiFixture.x);
		point.setY(PointApiFixture.y);

		assertNotNull(point);

		assertEquals(PointApiFixture.x, point.getX(), 0);
		assertEquals(PointApiFixture.y, point.getY(), 0);
	}
}
