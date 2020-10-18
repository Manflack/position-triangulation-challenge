package ar.com.manflack.mercadolibre.domain.service.impl;

import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.MISSING_INFORMATION_TO_COMPUTE;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.MISSING_INFORMATION_TO_COMPUTE_MSG;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION_MSG;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.OVERINFORMATION_TO_COMPUTE;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.OVERINFORMATION_TO_COMPUTE_MSG;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import ar.com.manflack.mercadolibre.app.api.ApiResponse;
import ar.com.manflack.mercadolibre.app.api.MyVectorApi;
import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.domain.exception.UtilityException;
import ar.com.manflack.mercadolibre.domain.provider.CircleCircleIntersection;
import ar.com.manflack.mercadolibre.domain.provider.model.Circle;
import ar.com.manflack.mercadolibre.domain.provider.model.MyVector;
import ar.com.manflack.mercadolibre.domain.service.UtilitiesService;

@Service
public class UtilitiesServiceImpl implements UtilitiesService
{
	private static final String KENOBI_NAME = "KENOBI";
	private static final String SKYWALKER_NAME = "SKYWALKER";
	private static final String SATO_NAME = "SATO";

	private final Log logger = LogFactory.getLog(getClass());

	private static final double EPSILON = 0.00001;

	private static final double kenobiX = -400;
	private static final double kenobiY = 0;

	private static final double skywalkerX = 100;
	private static final double skywalkerY = 0;

	private static final double satoX = -100;
	private static final double satoY = -50;

	private List<SatelliteApi> localSatellitesInformation = new ArrayList<>();

	@Override
	public ApiResponse obtainIntersection(List<SatelliteApi> satellites) throws UtilityException
	{
		ApiResponse apiResponse = new ApiResponse();

		/*
		 * Make a relationship as SATELLITE_NAME -> DISTANCE
		 */
		Hashtable<String, Double> relationNames = new Hashtable<>();
		satellites.forEach(satellite -> {
			relationNames.put(satellite.getName().toUpperCase(), satellite.getDistance());
		});

		/*
		 * Instantiate the Circles with the data provided
		 */
		Circle kenobi = new Circle(kenobiX, kenobiY, relationNames.get(KENOBI_NAME));
		Circle skywalker = new Circle(skywalkerX, skywalkerY, relationNames.get(SKYWALKER_NAME));
		Circle sato = new Circle(satoX, satoY, relationNames.get(SATO_NAME));

		CircleCircleIntersection m = new CircleCircleIntersection(kenobi, skywalker);

		/*
		 * Obtain a List of intersections which can result in NULL,
		 * OneIntersection or Two Intersection
		 */
		List<MyVector> intersections = m.getIntersectionPoints();

		if (null == intersections)
		{
			// No exist intersection
			throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
		}
		else if (null != intersections.get(0))
		{
			// One intersection
			MyVector intersectionAB = intersections.get(0);

			// if the distance between obtained point and C, are "equals", then
			// be have intersection between A, B and C
			if (!validateToRadiiC(intersectionAB, sato))
			{
				throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
			}
			apiResponse.setPosition(new MyVectorApi(intersectionAB));
			return apiResponse;
		}
		else if (null != intersections.get(1) && null != intersections.get(2))
		{
			// in this case we have two intersections, almost one must be
			// coincide with Nave and C distance
			MyVector intersectionAB1 = intersections.get(1);
			MyVector intersectionAB2 = intersections.get(2);

			if (validateToRadiiC(intersectionAB1, sato))
			{
				apiResponse.setPosition(new MyVectorApi(intersectionAB1));
			}
			else if (validateToRadiiC(intersectionAB2, sato))
			{
				apiResponse.setPosition(new MyVectorApi(intersectionAB2));
			}
			return apiResponse;
		}
		throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
	}

	@Override
	public void setSatellite(SatelliteApi satelliteApi) throws UtilityException
	{
		if (localSatellitesInformation.size() == 3)
		{
			throw new UtilityException(OVERINFORMATION_TO_COMPUTE, OVERINFORMATION_TO_COMPUTE_MSG);
		}
		localSatellitesInformation.add(satelliteApi);
	}

	@Override
	public ApiResponse obtainIntersectionByStep() throws UtilityException
	{
		if (localSatellitesInformation.size() < 3)
		{
			/*
			 * If the user try to compute the Nave position's, and don't
			 * provided enough information, don't clean the data provided
			 * before.
			 */
			throw new UtilityException(MISSING_INFORMATION_TO_COMPUTE, MISSING_INFORMATION_TO_COMPUTE_MSG);
		}

		/*
		 * No matters what, always restart the data
		 */
		List<SatelliteApi> satelites = localSatellitesInformation;
		localSatellitesInformation = new ArrayList<>();
		return obtainIntersection(satelites);
	}

	private boolean validateToRadiiC(MyVector intersectionAB, Circle sato)
	{
		MyVector vectorC1cC2c = intersectionAB.sub(sato.getC());
		/*
		 * Log distance between circle centers:
		 */
		double distanceC1cC2c = vectorC1cC2c.mod();
		logger.info(vectorC1cC2c.toString() + " ->: " + distanceC1cC2c);

		return Math.abs(distanceC1cC2c - sato.getR()) > EPSILON ? false : true;
	}
}
