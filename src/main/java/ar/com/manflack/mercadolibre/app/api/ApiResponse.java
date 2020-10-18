package ar.com.manflack.mercadolibre.app.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {
	@JsonProperty
	private String message;

	@JsonProperty
	private MyVectorApi position;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MyVectorApi getPosition() {
		return position;
	}

	public void setPosition(MyVectorApi position) {
		this.position = position;
	}

}
