package com.scu.backend.service;

import static com.scu.backend.service.BlockchainUtil.readFromChain;

import org.web3j.protocol.core.RemoteCall;

import com.scu.backend.service.model.ServiceContract;
import com.scu.backend.service.model.ServiceOffer;
import com.scu.contract.generated.SCUServiceContract;
import com.scu.contract.generated.SCUServiceContractCreator;

public class ServiceContractService {

	private ServiceOfferService serviceOfferService;
	private Web3jConnection web3jConnection;

	public ServiceContractService(ServiceOfferService argServiceOfferService, Web3jConnection web3jConnection) {
		this.serviceOfferService = argServiceOfferService;
		this.web3jConnection = web3jConnection;
	}

	public ServiceContract readServiceContract(String argAddress) throws Exception {
		SCUServiceContract myServiceContractContract = SCUServiceContract.load(argAddress, web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		ServiceContract myServiceContract = new ServiceContract();
		myServiceContract.setContractAddress(myServiceContractContract.getContractAddress());
		myServiceContract.setActive(readFromChain(myServiceContractContract.active()));
		myServiceContract.setBeginTime(readFromChain(myServiceContractContract.beginTime()));
		myServiceContract.setClientAddress(readFromChain(myServiceContractContract.clientAddress()));
		String serviceOfferAddress = readFromChain(myServiceContractContract.serviceOffer());
		ServiceOffer serviceOffer = serviceOfferService.loadServiceOffer(serviceOfferAddress);
		myServiceContract.setServiceOffer(serviceOffer);
		return myServiceContract;
	}

	public String createServiceContract(ServiceOffer argServiceOffer) throws Exception {
		RemoteCall<SCUServiceContractCreator> creatorDeployCall = SCUServiceContractCreator.deploy(
				web3jConnection.getWeb3j(), web3jConnection.getCredentials(), web3jConnection.getGasPrice(),
				web3jConnection.getGasLimit());
		SCUServiceContractCreator myCreator = creatorDeployCall.send();
		myCreator.createServiceContract(argServiceOffer.getServiceOfferAddress()).send();
		return readFromChain(myCreator.serviceContract());
	}

}
