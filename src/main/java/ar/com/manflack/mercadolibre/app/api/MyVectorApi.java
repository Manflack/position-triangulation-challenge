package ar.com.manflack.mercadolibre.app.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyVectorApi {
	@JsonProperty
	private double x;

	@JsonProperty
	private double y;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
