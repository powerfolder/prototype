package com.scu.backend.service;

import static com.scu.backend.service.BlockchainUtil.readFromChain;

import com.scu.backend.service.model.ServiceContract;
import com.scu.backend.service.model.ServiceOffer;
import com.scu.contract.generated.SCUMarketplace;
import com.scu.contract.generated.SCUServiceContract;

public class ServiceContractService {

	private Web3jConnection web3jConnection;
	private String marketplaceContractAddress;

	public ServiceContractService(Web3jConnection web3jConnection, String marketplaceContractAddress) {
		super();
		this.web3jConnection = web3jConnection;
		this.marketplaceContractAddress = marketplaceContractAddress;
	}

	public ServiceContract readServiceContract(String argAddress) throws Exception {
		SCUServiceContract myServiceContractContract = SCUServiceContract.load(argAddress, web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		ServiceContract myServiceContract = new ServiceContract();
		myServiceContract.setContractAddress(myServiceContractContract.getContractAddress());
		myServiceContract.setActive(readFromChain(myServiceContractContract.active()));
		myServiceContract.setBeginTime(readFromChain(myServiceContractContract.beginTime()));
		myServiceContract.setClientAddress(readFromChain(myServiceContractContract.clientAddress()));
		myServiceContract.setServiceProviderAddress(readFromChain(myServiceContractContract.serviceProviderAddress()));
		myServiceContract.setServiceProviderName(readFromChain(myServiceContractContract.serviceProviderName()));
		myServiceContract.setPricePerMonth(readFromChain(myServiceContractContract.pricePerMonth()));
		return myServiceContract;
	}

	public String createServiceContract(ServiceOffer argServiceOffer)
			throws Exception {
		SCUMarketplace myMarketplace = SCUMarketplace.load(marketplaceContractAddress, web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		myMarketplace.createServiceContract(argServiceOffer.getServiceOfferAddress()).send();
		return "";
	}

}
