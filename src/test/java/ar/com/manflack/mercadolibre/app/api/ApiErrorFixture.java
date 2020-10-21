package ar.com.manflack.mercadolibre.app.api;

import org.springframework.http.HttpStatus;

public class ApiErrorFixture
{
	public static String code = "CODIGO";
	public static String message = "MENSAJE DE ERROR";
	public static String debugMessage = "DEBUG MENSAJE DE ERROR";

	private ApiError build()
	{
		return new ApiError(HttpStatus.OK, code, message, new Exception("EXCEPTION"));
	}

	public static ApiError withDefaults()
	{
		return new ApiErrorFixture().build();
	}
}
