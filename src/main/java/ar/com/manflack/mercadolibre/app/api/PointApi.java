package ar.com.manflack.mercadolibre.app.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import ar.com.manflack.mercadolibre.domain.model.Point;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonRootName(value = "PointApi")
@Schema(description = "Point in cartesian plane.")
public class PointApi
{
	@JsonProperty
	private double x;

	@JsonProperty
	private double y;

	public PointApi(Point point)
	{
		this.x = point.getX();
		this.y = point.getY();
	}

	public PointApi(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public PointApi()
	{

	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	@Override
	public String toString()
	{
		return "PointApi [x=" + x + ", y=" + y + "]";
	}

}
