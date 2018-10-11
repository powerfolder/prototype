package com.scu.backend.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.scu.backend.service.model.ServiceContract;
import com.scu.backend.service.model.ServiceOffer;
import com.scu.backend.service.model.ServiceOfferRequest;

public class CompleteTest {

	private static final BigInteger TEST_PRICE_PER_MONTH = BigInteger.valueOf(1l);
	private static final String TEST_DESCRIPTION = "Test service offer";
	private static final String TEST_LOCATION = "Germany/Düsseldorf";
	private static final String TEST_SERVICE_PROVIDER = "Powerfolder";
	private static final String TEST_CLIENT_ADDRESS = "0xA8554C3Df0275E24bBEFf9D1A07F3fededC26989";
	private static final String TEST_SERVICE_PROVIDER_ADDRESS = "0xA8554C3Df0275E24bBEFf9D1A07F3fededC26989";
	private static final BigInteger GAS_PRICE = BigInteger.valueOf(1000000001l);
	private static final BigInteger GAS_LIMIT = BigInteger.valueOf(10000000000l);

	@Test
	public void dumbServiceOfferAndContractTest() throws Exception {
		Web3j web3j = Web3j.build(new HttpService());
		ClassLoader classLoader = getClass().getClassLoader();
		URL myWalletResource = classLoader.getResource("ethereum.private.wallet");
		assertNotNull(myWalletResource);
		Credentials credentials = WalletUtils.loadCredentials("di?#mewp362", myWalletResource.getFile());
		Web3jConnection web3jConnection = new Web3jConnection(web3j, credentials, GAS_PRICE, GAS_LIMIT);
		MarketplaceDeploymentService marketplaceDeployment = new MarketplaceDeploymentService(web3jConnection);
		String marketPlaceAddress = marketplaceDeployment.deployMarketplace();
		System.out.println("Deployed market place w/ address: " + marketPlaceAddress);
		ServiceOfferService myServiceOfferService = new ServiceOfferService(web3jConnection, marketPlaceAddress);
		ServiceOfferRequest myServiceOfferRequest = new ServiceOfferRequest();
		myServiceOfferRequest.setDescription(TEST_DESCRIPTION);
		myServiceOfferRequest.setPricePerMonth(TEST_PRICE_PER_MONTH);
		myServiceOfferRequest.setServiceProviderName(TEST_SERVICE_PROVIDER);
		myServiceOfferRequest.setLocation(TEST_LOCATION);
		String myServiceOfferAddress = myServiceOfferService.createServiceOffer(myServiceOfferRequest);
		System.out.println("Created service offer with address: " + myServiceOfferAddress);
		ServiceOffer myServiceOffer = myServiceOfferService.loadServiceOffer(myServiceOfferAddress);
		checkServiceOffer(myServiceOffer, myServiceOfferAddress);
		List<ServiceOffer> myServiceOfferList = myServiceOfferService.loadServiceOfferList();
		assertNotNull(myServiceOfferList);
		assertEquals(1, myServiceOfferList.size());
		checkServiceOffer(myServiceOfferList.get(0), myServiceOfferAddress);
		ServiceContractService myServiceContractService = new ServiceContractService(myServiceOfferService, web3jConnection);
		String myServiceContractAddress = myServiceContractService.createServiceContract(myServiceOffer);
		assertNotNull(myServiceContractAddress);
		System.out.println("Created service contract with address: " + myServiceContractAddress);
		ServiceContract myContract = myServiceContractService.readServiceContract(myServiceContractAddress);
		assertNotNull(myContract);
		assertEquals(TEST_PRICE_PER_MONTH, myContract.getServiceOffer().getPricePerMonth());
		assertEquals(TEST_SERVICE_PROVIDER, myContract.getServiceOffer().getServiceProviderName());
		assertEquals(true, myContract.isActive());
		assertNotNull(myContract.getBeginTime());
		assertEquals(myServiceContractAddress, myContract.getContractAddress());
		System.out.println("Test ran successful for contract with address: " + myServiceContractAddress
				+ " and offer with address " + myServiceOfferAddress);
	}

	private void checkServiceOffer(ServiceOffer argServiceOffer, String argServiceOfferAddress) {
		assertNotNull(argServiceOffer);
		assertEquals(TEST_DESCRIPTION, argServiceOffer.getDescription());
		assertEquals(TEST_PRICE_PER_MONTH, argServiceOffer.getPricePerMonth());
		assertEquals(TEST_SERVICE_PROVIDER, argServiceOffer.getServiceProviderName());
		assertEquals(argServiceOfferAddress, argServiceOffer.getServiceOfferAddress());
		assertEquals(TEST_LOCATION, argServiceOffer.getLocation());
	}

}
