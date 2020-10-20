package ar.com.manflack.mercadolibre.app.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import ar.com.manflack.mercadolibre.domain.model.Point;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonRootName(value = "ApiResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "ApiResponse data object")
public class ApiResponse
{
	@JsonProperty
	private String message;

	@JsonProperty
	private Point position;

	public ApiResponse(String message)
	{
		this.message = message;
	}

	public ApiResponse()
	{

	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Point getPosition()
	{
		return position;
	}

	public void setPosition(Point position)
	{
		this.position = position;
	}

	@Override
	public String toString()
	{
		return "ApiResponse [message=" + message + ", position=" + position + "]";
	}

}
