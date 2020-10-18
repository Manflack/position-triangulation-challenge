package ar.com.manflack.mercadolibre.app.api;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonRootName(value = "satellite")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
@Schema(description = "Address information.")
public class SatelliteApi
{
	@JsonProperty
	@Schema(description = "Name of Satellite")
	private String name;

	@JsonProperty
	@Schema(description = "Distance between Nave and Satellite")
	private double distance;

	@JsonProperty
	@Schema(description = "Message received from Nave")
	private List<String> message;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getDistance()
	{
		return distance;
	}

	public void setDistance(double distance)
	{
		this.distance = distance;
	}

	public List<String> getMessage()
	{
		return message;
	}

	public void setMessage(List<String> message)
	{
		this.message = message;
	}

}
