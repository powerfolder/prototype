package com.scu.backend.service.model;

import java.math.BigInteger;

public class ServiceOffer {

	private String id;
	private String serviceProvider;
	private BigInteger pricePerMonth;
	private String description;
	private String location;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	public BigInteger getPricePerMonth() {
		return pricePerMonth;
	}

	public void setPricePerMonth(BigInteger pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
