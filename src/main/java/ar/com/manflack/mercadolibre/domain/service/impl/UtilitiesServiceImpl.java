package ar.com.manflack.mercadolibre.domain.service.impl;

import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION;
import static ar.com.manflack.mercadolibre.domain.exception.UtilityException.NO_INTERSECTION_MSG;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.com.manflack.mercadolibre.app.api.ApiResponse;
import ar.com.manflack.mercadolibre.app.api.SatelliteApi;
import ar.com.manflack.mercadolibre.domain.exception.UtilityException;
import ar.com.manflack.mercadolibre.domain.model.Circle;
import ar.com.manflack.mercadolibre.domain.model.MyVector;
import ar.com.manflack.mercadolibre.domain.service.UtilitiesService;
import ar.com.manflack.mercadolibre.domain.util.CircleCircleIntersection;

@Service
public class UtilitiesServiceImpl implements UtilitiesService
{
	@Override
	public ApiResponse obtenerCoordenadas(List<SatelliteApi> satellites) throws UtilityException
	{
		Circle A = new Circle(-400, 0, 300);
		Circle B = new Circle(100, 0, 200);
		Circle C = new Circle(-100, -50, 50);

		CircleCircleIntersection m = new CircleCircleIntersection(A, B);

		List<MyVector> intersections = m.getIntersectionPoints();

		if (null == intersections)
		{
			throw new UtilityException(NO_INTERSECTION, NO_INTERSECTION_MSG);
		}
		else if (null != intersections.get(0))
		{
			// Solo hay una interseccion

		}
		else if (null != intersections.get(1) && null != intersections.get(2))
		{
			// hay intersecciones, entonces corroboramos con pitágoras
			MyVector tCircle = C.c;
			double h = Math.sqrt(Math.pow(tCircle.getX(), 2) + Math.pow(tCircle.getY(), 2));
			System.out.println(h);
		}

		System.out.println(m);
		return null;
	}
}
