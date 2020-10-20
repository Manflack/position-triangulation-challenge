package ar.com.manflack.mercadolibre.domain.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.domain.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService
{
	private LoadingCache<String, SatelliteApi> objectCache = CacheBuilder
			.newBuilder()
			.maximumSize(100)
			.expireAfterAccess(1, TimeUnit.MINUTES)
			.build(new CacheLoader<String, SatelliteApi>()
			{
				@Override
				public SatelliteApi load(String key) throws Exception
				{
					return null;
				}
			});

	@Override
	public LoadingCache<String, SatelliteApi> getCache()
	{
		return objectCache;
	}
}
