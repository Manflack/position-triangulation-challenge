package ar.com.manflack.mercadolibre.domain.service.impl;

import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION_MSG;

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
	private final Log logger = LogFactory.getLog(getClass());

	private static final double EPSILON = 0.00001;

	private Circle kenobi = new Circle(-400, 0, 300);
	private Circle skywalker = new Circle(100, 0, 200);
	private Circle sato = new Circle(-100, -50, 50);

	@Override
	public ApiResponse obtenerCoordenadas(List<SatelliteApi> satellites) throws UtilityException
	{
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("");
		apiResponse.setPosition(null);

		CircleCircleIntersection m = new CircleCircleIntersection(kenobi, skywalker);

		List<MyVector> intersections = m.getIntersectionPoints();

		if (null == intersections)
		{
			throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
		}
		else if (null != intersections.get(0))
		{
			// Solo hay una interseccion
			MyVector intersectionAB = intersections.get(0);

			// si la distancia al punto C es aproximadamente igual, entonces hay
			// intersección de A B y C
			if (!validateToRadiiC(intersectionAB))
			{
				throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
			}
			apiResponse.setPosition(new MyVectorApi(intersectionAB));
			return apiResponse;
		}
		else if (null != intersections.get(1) && null != intersections.get(2))
		{
			// hay 2 intersecciones, debe coincidir un punto con la distancia C
			MyVector intersectionAB1 = intersections.get(1);
			MyVector intersectionAB2 = intersections.get(2);

			if (validateToRadiiC(intersectionAB1))
			{
				apiResponse.setPosition(new MyVectorApi(intersectionAB1));
			}
			else if (validateToRadiiC(intersectionAB2))
			{
				apiResponse.setPosition(new MyVectorApi(intersectionAB2));
			}
			else
				throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
		}

		return apiResponse;
	}

	private boolean validateToRadiiC(MyVector intersectionAB)
	{
		MyVector vectorC1cC2c = intersectionAB.sub(sato.getC());
		// Distance between circle centers:
		double distanceC1cC2c = vectorC1cC2c.mod();
		logger.info(distanceC1cC2c);

		return Math.abs(distanceC1cC2c - sato.getR()) > EPSILON ? false : true;
	}
}
