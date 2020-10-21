package ar.com.manflack.mercadolibre.app.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class SatelliteApiTest
{
	private SatelliteApi api;

	@Test
	public void testInit()
	{
		api = SatelliteApiFixture.withDefaults();
		assertNotNull(api.getName());
		assertNotNull(api.getMessage());
		assertNotNull(api.toString());
	}

	@Test
	public void testSet()
	{
		api = new SatelliteApi();
		api.setDistance(SatelliteApiFixture.distance);
		api.setName(SatelliteApiFixture.name);
		api.setMessage(SatelliteApiFixture.message);

		assertNotNull(api.toString());

		assertEquals(SatelliteApiFixture.distance, api.getDistance(), 0);
		assertEquals(SatelliteApiFixture.name, api.getName());
		assertEquals(SatelliteApiFixture.message, api.getMessage());
	}
}
