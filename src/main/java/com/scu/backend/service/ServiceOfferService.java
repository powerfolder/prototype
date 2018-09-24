package com.scu.backend.service;

import static com.scu.backend.service.BlockchainUtil.readFromChain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.scu.backend.service.model.ServiceOffer;
import com.scu.backend.service.model.ServiceOfferRequest;
import com.scu.contract.generated.SCUMarketplace;
import com.scu.contract.generated.SCUMarketplace.SCUServiceOfferCreatedEventResponse;
import com.scu.contract.generated.SCUServiceOffer;

public class ServiceOfferService {

	private Web3jConnection web3jConnection;
	private String marketplaceContractAddress;

	public ServiceOfferService(Web3jConnection web3jConnection, String marketplaceContractAddress) {
		this.web3jConnection = web3jConnection;
		this.marketplaceContractAddress = marketplaceContractAddress;
	}

	public ServiceOffer loadServiceOffer(String argContractAddress) throws Exception {
		SCUServiceOffer myServiceOfferContract = SCUServiceOffer.load(argContractAddress, web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		ServiceOffer myServiceOffer = new ServiceOffer();
		myServiceOffer.setDescription(readFromChain(myServiceOfferContract.description()));
		myServiceOffer.setLocation(readFromChain(myServiceOfferContract.location()));
		myServiceOffer.setPricePerMonth(readFromChain(myServiceOfferContract.pricePerMonth()));
		myServiceOffer.setServiceProviderName(readFromChain(myServiceOfferContract.serviceProvider()));
		myServiceOffer.setServiceProviderAddress(readFromChain(myServiceOfferContract.owner()));
		myServiceOffer.setServiceOfferAddress(argContractAddress);
		return myServiceOffer;
	}

	public String createServiceOffer(ServiceOfferRequest argServiceOffer) throws Exception {
		SCUMarketplace myMarketplace = SCUMarketplace.load(marketplaceContractAddress, web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		TransactionReceipt transactionReceipt = myMarketplace.createServiceOffer(argServiceOffer.getServiceProviderName(),
				argServiceOffer.getPricePerMonth(), argServiceOffer.getDescription(), argServiceOffer.getLocation())
				.send();
		List<SCUServiceOfferCreatedEventResponse> responseList = myMarketplace
				.getSCUServiceOfferCreatedEvents(transactionReceipt);
		// TODO: Secure this code.
		// Assume that there is an offer:
		return responseList.get(0).serviceOffer;
	}

	public List<ServiceOffer> loadServiceOfferList() throws Exception {
		SCUMarketplace myMarketplace = SCUMarketplace.load(marketplaceContractAddress, web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		BigInteger myNrOfActiveOffers = readFromChain(myMarketplace.getNrOfActiveOffers());
		List<ServiceOffer> myServiceOfferList = new ArrayList<>(myNrOfActiveOffers.intValue());
		for (long myCounter = 0; myCounter < myNrOfActiveOffers.longValue(); myCounter++) {
			BigInteger myOfferIndex = BigInteger.valueOf(myCounter);
			String myOfferAddress = readFromChain(myMarketplace.activeOfferArray(myOfferIndex));
			myServiceOfferList.add(loadServiceOffer(myOfferAddress));
		}
		return myServiceOfferList;
	}

}
