package ar.com.manflack.mercadolibre.app.api;

import java.util.ArrayList;
import java.util.List;

public class SatelliteApiFixture
{
	public static final String name = "name";
	public static final double distance = 1;
	public static final List<String> message = new ArrayList<>();

	public static SatelliteApi withDefaults()
	{
		return new SatelliteApi(name, distance, message);
	}
}
