package com.scu.backend.service;

import static com.scu.backend.service.BlockchainUtil.readFromChain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.scu.backend.service.model.ServiceOffer;
import com.scu.contract.generated.SCUMarketplace;
import com.scu.contract.generated.SCUMarketplace.SCUServiceOfferCreatedEventResponse;
import com.scu.contract.generated.SCUServiceOffer;

public class ServiceOfferService {

	private Web3j web3j;
	private Credentials credentials;
	private BigInteger gasPrice;
	private BigInteger gasLimit;
	private String marketplaceContractAddress;

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
		SCUMarketplace myMarketplace = SCUMarketplace.load(marketplaceContractAddress, web3j, credentials, gasPrice,
				gasLimit);
		TransactionReceipt transactionReceipt = myMarketplace.createServiceOffer(argServiceOffer.getId(),
				argServiceOffer.getServiceProvider(), argServiceOffer.getPricePerMonth(),
				argServiceOffer.getDescription(), argServiceOffer.getLocation()).send();
		List<SCUServiceOfferCreatedEventResponse> responseList = myMarketplace
				.getSCUServiceOfferCreatedEvents(transactionReceipt);
		// Assume that there is an offer:
		return responseList.get(0).serviceOffer;
	}

	public List<ServiceOffer> loadServiceOfferList() throws Exception {
		SCUMarketplace myMarketplace = SCUMarketplace.load(marketplaceContractAddress, web3j, credentials, gasPrice,
				gasLimit);
		BigInteger myNrOfActiveOffers = readFromChain(myMarketplace.getNrOfActiveOffers());
		List<ServiceOffer> myServiceOfferList = new ArrayList<>(myNrOfActiveOffers.intValue());
		for (long myCounter = 0; myCounter < myNrOfActiveOffers.longValue(); myCounter++) {
			BigInteger myOfferIndex = BigInteger.valueOf(myCounter);
			String myOfferAddress = readFromChain(myMarketplace.activeOfferArray(myOfferIndex));
			myServiceOfferList.add(loadServiceOffer(myOfferAddress));
		}
		return new ArrayList<>();
	}
 
}
