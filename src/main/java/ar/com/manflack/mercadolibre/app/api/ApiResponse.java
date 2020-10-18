package ar.com.manflack.mercadolibre.app.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonRootName(value = "ApiResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "ApiResponse data object")
public class ApiResponse
{
	@JsonProperty
	private String message;

	@JsonProperty
	private MyVectorApi position;

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

	public MyVectorApi getPosition()
	{
		return position;
	}

	public void setPosition(MyVectorApi position)
	{
		this.position = position;
	}

}
