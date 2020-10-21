package ar.com.manflack.mercadolibre.domain.service.impl;

import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.INFINITE_INTERSECTION;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.INFINITE_INTERSECTION_MSG;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.MISSING_INFORMATION_TO_COMPUTE;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.MISSING_INFORMATION_TO_COMPUTE_MSG;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION_CIRCLE_IN_CIRCLE;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION_CIRCLE_IN_CIRCLE_MSG;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION_MSG;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.OVERINFORMATION_TO_COMPUTE;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.OVERINFORMATION_TO_COMPUTE_MSG;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.INVALID_MESSAGE_TO_BUILD;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.INVALID_MESSAGE_TO_BUILD_MSG;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.LoadingCache;

import ar.com.manflack.mercadolibre.app.api.ApiResponse;
import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.domain.exception.UtilityException;
import ar.com.manflack.mercadolibre.domain.model.Circle;
import ar.com.manflack.mercadolibre.domain.model.Point;
import ar.com.manflack.mercadolibre.domain.service.CacheService;
import ar.com.manflack.mercadolibre.domain.service.UtilitiesService;

@Service
public class UtilitiesServiceImpl implements UtilitiesService
{
	@Autowired
	private CacheService cacheService;

	private final Log logger = LogFactory.getLog(getClass());

	private static final String KENOBI_NAME = "KENOBI";
	private static final String SKYWALKER_NAME = "SKYWALKER";
	private static final String SATO_NAME = "SATO";

	private static final double EPSILON = 0.00001;

	private static final double KENOBI_X = -500;
	private static final double KENOBI_Y = -200;

	private static final double SKYWALKER_X = 100;
	private static final double SKYWALKER_Y = -100;

	private static final double SATO_X = 500;
	private static final double SATO_Y = 100;

	private LoadingCache<String, SatelliteApi> getCache()
	{
		return cacheService.getCache();
	}

	@Override
	public ApiResponse getLocation(@NotNull List<SatelliteApi> satellites) throws UtilityException, ExecutionException
	{
		ApiResponse apiResponse = new ApiResponse();

		if (getCache().size() != 3)
			satellites.forEach(satellite -> {
				getCache().put(satellite.getName().toUpperCase(), satellite);
			});

		/*
		 * Instantiate the Circles with the data provided
		 */
		Circle kenobi = new Circle(KENOBI_X, KENOBI_Y, getCache().get(KENOBI_NAME).getDistance());
		Circle skywalker = new Circle(SKYWALKER_X, SKYWALKER_Y, getCache().get(SKYWALKER_NAME).getDistance());
		Circle sato = new Circle(SATO_X, SATO_Y, getCache().get(SATO_NAME).getDistance());

		getCache().invalidateAll();

		/*
		 * Check if exists intersections
		 */
		validateIntersections(kenobi, skywalker);

		/*
		 * Obtain a List of intersections which can result in OneIntersection or
		 * Two Intersection
		 */
		List<Point> intersections = kenobi.intersections(skywalker);

		if (intersections.get(0).equals(intersections.get(1)))
		{
			// One intersection
			Point intersectionAB = intersections.get(0);

			// If the distance between obtained point and C, are "equals", then
			// be have intersection between A, B and C
			if (validateToRadiiC(intersectionAB, sato))
			{
				apiResponse.setPosition(intersectionAB);
			}
			else
				throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
		}
		else
		{
			// In this case we have two intersections, almost one must be
			// coincide with Nave and C distance
			Point intersectionAB1 = intersections.get(0);
			Point intersectionAB2 = intersections.get(1);

			if (validateToRadiiC(intersectionAB1, sato))
			{
				apiResponse.setPosition(intersectionAB1);
			}
			else if (validateToRadiiC(intersectionAB2, sato))
			{
				apiResponse.setPosition(intersectionAB2);
			}
			else
				throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
		}

		List<List<String>> arrayMessages = new ArrayList<>();
		arrayMessages.add(satellites.get(0).getMessage());
		arrayMessages.add(satellites.get(1).getMessage());
		arrayMessages.add(satellites.get(2).getMessage());
		apiResponse.setMessage(getMessage(arrayMessages));

		return apiResponse;
	}

	@Override
	public void setSatellite(SatelliteApi satelliteApi) throws UtilityException
	{
		if (null == satelliteApi.getMessage())
			throw new UtilityException(OVERINFORMATION_TO_COMPUTE, OVERINFORMATION_TO_COMPUTE_MSG);

		if (StringUtils.equalsIgnoreCase(KENOBI_NAME, satelliteApi.getName())
				|| StringUtils.equalsIgnoreCase(SKYWALKER_NAME, satelliteApi.getName())
				|| StringUtils.equalsIgnoreCase(SATO_NAME, satelliteApi.getName()))
		{
			getCache().put(satelliteApi.getName().toUpperCase(), satelliteApi);
		}
		else
			throw new UtilityException(OVERINFORMATION_TO_COMPUTE, OVERINFORMATION_TO_COMPUTE_MSG);
	}

	@Override
	public ApiResponse obtainIntersectionByStep() throws UtilityException, ExecutionException
	{
		if (getCache().size() < 3)
		{
			/*
			 * If the user try to compute the Nave position's, and don't
			 * provided enough information, don't clean the data provided
			 * before.
			 */
			throw new UtilityException(MISSING_INFORMATION_TO_COMPUTE, MISSING_INFORMATION_TO_COMPUTE_MSG);
		}

		/*
		 * Make a relationship as SATELLITE_NAME -> DISTANCE
		 */

		List<SatelliteApi> satellites = new ArrayList<>();
		satellites.add(getCache().get(KENOBI_NAME));
		satellites.add(getCache().get(SKYWALKER_NAME));
		satellites.add(getCache().get(SATO_NAME));

		return getLocation(satellites);
	}

	/*
	 * I don't call validateIntersections because I need to check two cases, and
	 * for avoid try catch logic, I don't call it.
	 */
	private boolean validateToRadiiC(Point intersectionAB, Circle sato)
	{
		Point pointSato = new Point(sato);
		/*
		 * Log distance between circle centers:
		 */
		double distanceAB = intersectionAB.distance(pointSato);

		logger.info(intersectionAB.toString() + " ->: " + distanceAB);

		/*
		 * assert the result with a EPSILON error compute
		 */
		return Math.abs(distanceAB - sato.getR()) > EPSILON ? false : true;
	}

	private void validateIntersections(Circle circle1, Circle circle2) throws UtilityException
	{
		Point kenobiPoint = new Point(circle1);
		Point skywalkerPoint = new Point(circle2);
		double distance = kenobiPoint.distance(skywalkerPoint);

		if (distance > circle1.getR() + circle2.getR())
		{
			/*
			 * If the distance between A and B are less that distance between
			 * sum of A Radii and B Radii, then doesn't exists intersection
			 */
			throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
		}
		else if (distance < Math.abs(circle1.getR() - circle2.getR()))
		{
			/*
			 * If the distance between A and b, are less that absolute
			 * difference between A Radii and B Radii, then the Circle are in
			 * the other Circle, and doesn't have intersections
			 */
			throw new UtilityException(NO_INTERSECTION_CIRCLE_IN_CIRCLE, NO_INTERSECTION_CIRCLE_IN_CIRCLE_MSG);
		}
		else if (distance == 0 && circle1.getR() == circle2.getR())
		{
			/*
			 * If the distance between A and B are 0 and has the same Radii,
			 * then are equals Circles, who contains infinite intersections
			 */
			throw new UtilityException(INFINITE_INTERSECTION, INFINITE_INTERSECTION_MSG);
		}
	}

	private String getMessage(List<List<String>> arrayMessages) throws UtilityException
	{
		List<String> satellite1 = arrayMessages.get(0);
		List<String> satellite2 = arrayMessages.get(1);
		List<String> satellite3 = arrayMessages.get(2);

		Integer gap = getGap(satellite1, satellite2);
		if (gap == null)
		{
			/*
			 * Try to find a coincidence or GAP instead of array 1 and 2, with 1
			 * and 3. For that purpose we swap the variables and call as above.
			 */
			List<String> aux = satellite1;
			satellite1 = satellite2;
			satellite2 = aux;
			gap = getGap(satellite1, satellite2);
		}

		/*
		 * At this point, we ensure that was checked all the posibilities, if
		 * are not GAP defined, then means that are no solution for solve
		 * message
		 */
		if (gap == null)
			throw new UtilityException(INVALID_MESSAGE_TO_BUILD, INVALID_MESSAGE_TO_BUILD_MSG);

		List<String> auxSatellite = getFixedArray(satellite1, satellite2, gap);

		gap = getGap(auxSatellite, satellite3);
		if (gap == null)
			throw new UtilityException(INVALID_MESSAGE_TO_BUILD, INVALID_MESSAGE_TO_BUILD_MSG);

		List<String> arrayMessageFinal = getFixedArray(auxSatellite, satellite3, gap);
		StringBuilder sb = new StringBuilder();
		arrayMessageFinal.forEach(m -> sb.append(m).append(" "));

		return sb.toString().trim();
	}

	private Integer getGap(List<String> satellite1, List<String> satellite2)
	{
		/*
		 * Iterate the gap by for iterator to avoid redundant code to compute
		 * gap
		 */
		for (int desfase = -1; desfase < 2; desfase++)
		{
			/*
			 * Iterate all possibilities according to defined maximum gap (1)
			 */
			for (int i = 0; i < satellite1.size(); i++)
			{
				/*
				 * Check if we don't access to invalid location of array,
				 * avoiding empty submessages, only if a word that are not
				 * empty, we consider that was is a gap, a coincidence
				 */
				if (i + desfase >= 0 && StringUtils.equals(satellite1.get(i), satellite2.get(i + desfase))
						&& StringUtils.isNotBlank(satellite1.get(i)))
				{
					return desfase;
				}
			}
		}
		return null;
	}

	private List<String> getFixedArray(List<String> message1, List<String> message2, int desfase)
	{
		/*
		 * Given the gap, and the two arrays, we can build the "common" message
		 */
		List<String> aux = new ArrayList<>();
		for (int i = 0; i < message1.size(); i++)
		{
			/*
			 * Avoid invalid location of array
			 */
			if (i + desfase >= 0)
			{
				String subMessage = message1.get(i).trim();

				/*
				 * We have the gap, but some location of array are empty, if the
				 * first array is Blank, then we use the other one, doesn't
				 * matter if the other one is blank too
				 */
				if (StringUtils.isNotBlank(subMessage))
					aux.add(subMessage);
				else
					aux.add(message2.get(i + desfase).trim());
			}
		}

		return aux;
	}
}
