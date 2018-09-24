package com.scu.backend.service.model;

import java.math.BigInteger;

public class ServiceOffer {

	private String serviceProviderName;
	private BigInteger pricePerMonth;
	private String description;
	private String location;
	private String serviceOfferAddress;
	private String serviceProviderAddress;

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

	public String getServiceOfferAddress() {
		return serviceOfferAddress;
	}

	public void setServiceOfferAddress(String serviceOfferAddress) {
		this.serviceOfferAddress = serviceOfferAddress;
	}

	public String getServiceProviderAddress() {
		return serviceProviderAddress;
	}

	public void setServiceProviderAddress(String serviceProviderAddress) {
		this.serviceProviderAddress = serviceProviderAddress;
	}

}
