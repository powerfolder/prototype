package com.scu.backend.service;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.scu.backend.service.model.ServiceOffer;

public class ServiceOfferServiceTest {

	private static final BigInteger GAS_PRICE = BigInteger.valueOf(100l);
	private static final BigInteger GAS_LIMIT = BigInteger.valueOf(1000l);
	
	public void dumpServiceOfferTest() throws Exception {
		Web3j web3j = Web3j.build(new HttpService()); 
		Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");
		Web3jConnection web3jConnection = new Web3jConnection(web3j, credentials, GAS_PRICE, GAS_LIMIT);
		MarketplaceDeploymentService marketplaceDeployment = new MarketplaceDeploymentService(web3jConnection);
		String marketPlaceAddress = marketplaceDeployment.deployMarketplace();
		ServiceOfferService myServiceOfferService = new ServiceOfferService(web3jConnection, marketPlaceAddress);
		ServiceOffer myServiceOffer = new ServiceOffer();
		myServiceOffer.setId("ABCDEFG");
		myServiceOffer.setDescription("Test service offer");
		myServiceOffer.setPricePerMonth(BigInteger.valueOf(1l));
		myServiceOffer.setServiceProvider("Powerfolder");
		myServiceOffer.setLocation("Germany/Düsseldorf");
		myServiceOffer.setContractAddress();
		myServiceOfferService.createServiceOffer(myServiceOffer);
	}
	
}
