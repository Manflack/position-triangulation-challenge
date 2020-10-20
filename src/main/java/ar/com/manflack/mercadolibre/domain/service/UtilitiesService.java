package ar.com.manflack.mercadolibre.domain.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ar.com.manflack.mercadolibre.app.api.ApiResponse;
import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.domain.exception.UtilityException;

public interface UtilitiesService
{
	ApiResponse getLocation(List<SatelliteApi> satellites) throws UtilityException, ExecutionException;

	ApiResponse obtainIntersectionByStep() throws UtilityException, ExecutionException;

	void setSatellite(SatelliteApi satelliteApi) throws UtilityException;
}
