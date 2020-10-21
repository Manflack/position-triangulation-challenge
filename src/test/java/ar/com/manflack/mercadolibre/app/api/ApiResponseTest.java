package ar.com.manflack.mercadolibre.app.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ApiResponseTest
{
	private ApiResponse api;

	@Test
	public void testInit()
	{
		api = ApiResponseFixture.withDefaults();
		assertNotNull(api);
		assertNotNull(api.getMessage());
		assertNotNull(api.getPosition());
	}

	@Test
	public void testSet()
	{
		api = new ApiResponse();
		api.setMessage(ApiResponseFixture.message);
		api.setPosition(ApiResponseFixture.position);

		assertNotNull(api);

		assertEquals(ApiResponseFixture.message, api.getMessage());
		assertEquals(ApiResponseFixture.position, api.getPosition());
	}
}
