package com.scu.backend.service.model;

import java.math.BigInteger;

public class ServiceOfferRequest {

	private String serviceProviderName;
	private BigInteger pricePerMonth;
	private String description;
	private String location;

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
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
