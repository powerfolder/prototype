package com.scu.backend.service.model;

import java.math.BigInteger;

public class ServiceContract {

	private String contractAddress;
	private String clientAddress;
	private BigInteger beginTime;
	private boolean active;
	private ServiceOffer serviceOffer;

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
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

	public ServiceOffer getServiceOffer() {
		return serviceOffer;
	}

	public void setServiceOffer(ServiceOffer serviceOffer) {
		this.serviceOffer = serviceOffer;
	}

}
