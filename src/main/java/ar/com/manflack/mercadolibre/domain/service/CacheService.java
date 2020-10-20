package ar.com.manflack.mercadolibre.domain.service;

import com.google.common.cache.LoadingCache;

import ar.com.manflack.mercadolibre.app.api.SatelliteApi;

public interface CacheService
{
	LoadingCache<String, SatelliteApi> getCache();
}
