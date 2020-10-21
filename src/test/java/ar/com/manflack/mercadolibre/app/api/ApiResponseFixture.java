package ar.com.manflack.mercadolibre.app.api;

import ar.com.manflack.mercadolibre.domain.model.Point;
import ar.com.manflack.mercadolibre.domain.model.PointFixture;

public class ApiResponseFixture
{
	public static final String message = "message";
	public static final Point position = PointFixture.withDefaults();

	public static ApiResponse withDefaults()
	{
		return new ApiResponse(message, position);
	}
}
