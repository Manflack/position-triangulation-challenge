package ar.com.manflack.mercadolibre.app.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class ApiErrorTest
{
	private ApiError apiError;

	@Test
	public void testInit()
	{
		apiError = ApiErrorFixture.withDefaults();
		assertNotNull(apiError);
		assertNotNull(apiError.getStatus());
		assertNotNull(apiError.getCode());
		assertNotNull(apiError.getMessage());
		assertNotNull(apiError.toString());
	}

	@Test
	public void testSet()
	{
		apiError = new ApiError(HttpStatus.OK);
		apiError.setStatus(HttpStatus.BAD_REQUEST);
		apiError.setCode(ApiErrorFixture.code);
		apiError.setMessage(ApiErrorFixture.message);
		apiError.setDebugMessage(ApiErrorFixture.debugMessage);
		apiError.setTimestamp(LocalDateTime.now());
		assertNotNull(apiError);
		assertEquals(HttpStatus.BAD_REQUEST, apiError.getStatus());
		assertEquals(ApiErrorFixture.code, apiError.getCode());
		assertEquals(ApiErrorFixture.message, apiError.getMessage());
		assertEquals(ApiErrorFixture.debugMessage, apiError.getDebugMessage());
		assertNotNull(apiError.getTimestamp());

		apiError = new ApiError(HttpStatus.OK, ApiErrorFixture.code, ApiErrorFixture.message);
	}
}
