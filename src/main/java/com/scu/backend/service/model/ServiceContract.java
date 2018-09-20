package com.scu.backend.service.model;

import java.math.BigInteger;

public class ServiceContract {

	private String contractAddress;
	private String id;
	private String clientAddress;
	private String contractIdentity;
	private BigInteger beginTime;
	private boolean active;
	private String offerId;
	private String serviceProviderName;
	private String serviceProviderAddress;
	private BigInteger pricePerMonth;

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getContractIdentity() {
		return contractIdentity;
	}

	public void setContractIdentity(String contractIdentity) {
		this.contractIdentity = contractIdentity;
	}

	public BigInteger getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(BigInteger beginTime) {
		this.beginTime = beginTime;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getServiceProviderName() {
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName) {
		this.serviceProviderName = serviceProviderName;
	}

	public String getServiceProviderAddress() {
		return serviceProviderAddress;
	}

	public void setServiceProviderAddress(String serviceProviderAddress) {
		this.serviceProviderAddress = serviceProviderAddress;
	}

	public BigInteger getPricePerMonth() {
		return pricePerMonth;
	}

	public void setPricePerMonth(BigInteger pricePerMonth) {
		this.pricePerMonth = pricePerMonth;
	}

}
