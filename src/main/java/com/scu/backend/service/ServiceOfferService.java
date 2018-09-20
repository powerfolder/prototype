package com.scu.backend.service;

import static com.scu.backend.service.BlockchainUtil.readFromChain;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;

import com.scu.backend.service.model.ServiceOffer;
import com.scu.contract.generated.SCUServiceOffer;
import com.scu.contract.generated.SCUServiceOfferCreator;

public class ServiceOfferService {

	private Web3j web3j;
	private Credentials credentials;
	private BigInteger gasPrice;
	private BigInteger gasLimit;

	public ServiceOfferService(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		this.web3j = web3j;
		this.credentials = credentials;
		this.gasPrice = gasPrice;
		this.gasLimit = gasLimit;
	}

	public ServiceOffer loadServiceOffer(String argContractAddress) throws Exception {
		SCUServiceOffer myServiceOfferContract = SCUServiceOffer.load(argContractAddress, web3j, credentials, gasPrice,
				gasLimit);
		ServiceOffer myServiceOffer = new ServiceOffer();
		myServiceOffer.setContractAddress(myServiceOfferContract.getContractAddress());
		myServiceOffer.setId(readFromChain(myServiceOfferContract.ID()));
		myServiceOffer.setDescription(readFromChain(myServiceOfferContract.description()));
		myServiceOffer.setLocation(readFromChain(myServiceOfferContract.location()));
		myServiceOffer.setPricePerMonth(readFromChain(myServiceOfferContract.pricePerMonth()));
		myServiceOffer.setServiceProvider(readFromChain(myServiceOfferContract.serviceProvider()));
		return myServiceOffer;
	}

	public String createServiceOffer(ServiceOffer argServiceOffer) throws Exception {
		RemoteCall<SCUServiceOfferCreator> offerDeployCall = SCUServiceOfferCreator.deploy(web3j, credentials,
				gasPrice, gasLimit);
		SCUServiceOfferCreator myCreator = offerDeployCall.send();
		myCreator.createServiceOffer(argServiceOffer.getId(), argServiceOffer.getServiceProvider(), argServiceOffer.getPricePerMonth(),
				argServiceOffer.getDescription(), argServiceOffer.getLocation()).send();
		return readFromChain(myCreator.serviceOffer());
	}

}
