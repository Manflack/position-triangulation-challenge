package ar.com.manflack.mercadolibre.app.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.com.manflack.mercadolibre.domain.provider.model.MyVector;

public class MyVectorApi {
	@JsonProperty
	private double x;

	@JsonProperty
	private double y;

	public MyVectorApi(MyVector myVector)
	{
		this.x = myVector.getX();
		this.y = myVector.getY();
	}
	
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
