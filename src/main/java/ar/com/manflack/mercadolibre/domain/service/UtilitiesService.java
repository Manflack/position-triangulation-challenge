package ar.com.manflack.mercadolibre.domain.service;

import java.util.List;

import ar.com.manflack.mercadolibre.app.api.ApiResponse;
import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.domain.exception.UtilityException;

public interface UtilitiesService
{
	ApiResponse obtenerCoordenadas(List<SatelliteApi> satellites) throws UtilityException;
}
