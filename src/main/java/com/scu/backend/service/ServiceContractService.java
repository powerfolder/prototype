package com.scu.backend.service;

import static com.scu.backend.service.BlockchainUtil.readFromChain;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;

import com.scu.backend.service.model.ServiceContract;
import com.scu.contract.generated.SCUServiceContract;
import com.scu.contract.generated.SCUServiceContractCreator;

public class ServiceContractService {

	private Web3j web3j;
	private Credentials credentials;
	private BigInteger gasPrice;
	private BigInteger gasLimit;

	public ServiceContractService(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		this.web3j = web3j;
		this.credentials = credentials;
		this.gasPrice = gasPrice;
		this.gasLimit = gasLimit;
	}

	public ServiceContract readServiceContract(String argAddress) throws Exception {
		SCUServiceContract myServiceContractContract = SCUServiceContract.load(argAddress, web3j, credentials, gasPrice,
				gasLimit);
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
 
	public String createServiceContract(String argId, ServiceContract contract, String contractIdentity)
			throws Exception {
		String offerAddress = contract.getContractAddress();
		RemoteCall<SCUServiceContractCreator> creatorDeployCall = SCUServiceContractCreator.deploy(web3j, credentials,
				gasPrice, gasLimit);
		SCUServiceContractCreator myCreator = creatorDeployCall.send();
		myCreator.createServiceContract(argId, offerAddress, contractIdentity).send();
		return readFromChain(myCreator.serviceContract());
	}

}
