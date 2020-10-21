package ar.com.manflack.mercadolibre.app.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PointApiTest
{
	private PointApi api;

	@Test
	public void testInit()
	{
		api = PointApiFixture.withDefaults();
		assertNotNull(api.toString());
	}

	@Test
	public void testSet()
	{
		api = new PointApi();
		api.setX(PointApiFixture.x);
		api.setY(PointApiFixture.y);

		assertNotNull(api);

		assertEquals(PointApiFixture.x, api.getX(), 0);
		assertEquals(PointApiFixture.y, api.getY(), 0);
	}
}
