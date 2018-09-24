package com.scu.backend.service;

import static com.scu.backend.service.BlockchainUtil.readFromChain;

import org.web3j.protocol.core.RemoteCall;

import com.scu.backend.service.model.ServiceContract;
import com.scu.contract.generated.SCUServiceContract;
import com.scu.contract.generated.SCUServiceContractCreator;

public class ServiceContractService {

	private Web3jConnection web3jConnection;

	public ServiceContractService(Web3jConnection web3jConnection) {
		this.web3jConnection = web3jConnection;
	}

	public ServiceContract readServiceContract(String argAddress) throws Exception {
		SCUServiceContract myServiceContractContract = SCUServiceContract.load(argAddress, web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		ServiceContract myServiceContract = new ServiceContract();
		myServiceContract.setContractAddress(myServiceContractContract.getContractAddress());
		myServiceContract.setId(readFromChain(myServiceContractContract.ID()));
		myServiceContract.setPricePerMonth(readFromChain(myServiceContractContract.pricePerMonth()));
		myServiceContract.setActive(readFromChain(myServiceContractContract.active()));
		myServiceContract.setBeginTime(readFromChain(myServiceContractContract.beginTime()));
		myServiceContract.setClientAddress(readFromChain(myServiceContractContract.clientAddress()));
		myServiceContract.setContractIdentity(readFromChain(myServiceContractContract.contractIdentity()));
		myServiceContract.setOfferId(readFromChain(myServiceContractContract.offerID()));
		myServiceContract.setServiceProviderAddress(readFromChain(myServiceContractContract.serviceProviderAddress()));
		myServiceContract.setServiceProviderName(readFromChain(myServiceContractContract.serviceProviderName()));
		return myServiceContract;
	}

	public String createServiceContract(String argId, String argOfferAddress, String contractIdentity)
			throws Exception {
		RemoteCall<SCUServiceContractCreator> creatorDeployCall = SCUServiceContractCreator.deploy(
				web3jConnection.getWeb3j(), web3jConnection.getCredentials(), web3jConnection.getGasPrice(),
				web3jConnection.getGasLimit());
		SCUServiceContractCreator myCreator = creatorDeployCall.send();
		myCreator.createServiceContract(argId, argOfferAddress, contractIdentity).send();
		return readFromChain(myCreator.serviceContract());
	}

}
