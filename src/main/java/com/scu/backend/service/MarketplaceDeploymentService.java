package com.scu.backend.service;

import org.web3j.protocol.core.RemoteCall;

import com.scu.contract.generated.SCUMarketplace;

public class MarketplaceDeploymentService {

	private Web3jConnection web3jConnection;

	public MarketplaceDeploymentService(Web3jConnection web3jConnection) {
		this.web3jConnection = web3jConnection;
	}
 
	public String deployMarketplace() throws Exception {
		RemoteCall<SCUMarketplace> marketPlaceDeploymentCall = SCUMarketplace.deploy(web3jConnection.getWeb3j(),
				web3jConnection.getCredentials(), web3jConnection.getGasPrice(), web3jConnection.getGasLimit());
		SCUMarketplace myMarketplace = marketPlaceDeploymentCall.send();
		return myMarketplace.getContractAddress();
	}

}
