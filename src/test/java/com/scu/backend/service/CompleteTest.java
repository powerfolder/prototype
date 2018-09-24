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

public class CompleteTest {

	private static final BigInteger TEST_PRICE_PER_MONTH = BigInteger.valueOf(1l);
	private static final String TEST_DESCRIPTION = "Test service offer";
	private static final String TEST_OFFER_ID = "ABCDEFG";
	private static final String TEST_CONTRACT_ID = "GFEDCBA";
	private static final String TEST_LOCATION = "Germany/Düsseldorf";
	private static final String TEST_SERVICE_PROVIDER = "Powerfolder";
	private static final String TEST_CONTRACT_IDENTITY = "IDFHGERGERER";
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
		ServiceOfferService myServiceOfferService = new ServiceOfferService(web3jConnection, marketPlaceAddress);
		ServiceOffer myServiceOffer = new ServiceOffer();
		myServiceOffer.setId(TEST_OFFER_ID);
		myServiceOffer.setDescription(TEST_DESCRIPTION);
		myServiceOffer.setPricePerMonth(TEST_PRICE_PER_MONTH);
		myServiceOffer.setServiceProvider(TEST_SERVICE_PROVIDER);
		myServiceOffer.setLocation(TEST_LOCATION);
		String myServiceOfferAddress = myServiceOfferService.createServiceOffer(myServiceOffer);
		ServiceOffer myLoadedServiceOffer = myServiceOfferService.loadServiceOffer(myServiceOfferAddress);
		checkServiceOffer(myLoadedServiceOffer);
		List<ServiceOffer> myServiceOfferList = myServiceOfferService.loadServiceOfferList();
		assertNotNull(myServiceOfferList);
		assertEquals(1, myServiceOfferList.size());
		checkServiceOffer(myServiceOfferList.get(0));
		ServiceContractService myServiceContractService = new ServiceContractService(web3jConnection);
		String myServiceContractAddress = myServiceContractService.createServiceContract(TEST_CONTRACT_ID,
				myServiceOfferAddress, TEST_CONTRACT_IDENTITY);
		assertNotNull(myServiceContractAddress);
		ServiceContract myContract = myServiceContractService.readServiceContract(myServiceContractAddress);
		assertNotNull(myContract);
		assertEquals(TEST_CONTRACT_IDENTITY, myContract.getContractIdentity());
		assertEquals(TEST_OFFER_ID, myContract.getOfferId());
		assertEquals(TEST_CONTRACT_ID, myContract.getId());
		assertEquals(TEST_PRICE_PER_MONTH, myContract.getPricePerMonth());
		assertEquals(TEST_SERVICE_PROVIDER, myContract.getServiceProviderName());
		assertEquals(true, myContract.isActive());
		assertNotNull(myContract.getBeginTime());
		assertEquals(TEST_SERVICE_PROVIDER_ADDRESS, myContract.getServiceProviderAddress());
		assertEquals(TEST_CLIENT_ADDRESS, myContract.getClientAddress());
		assertEquals(myServiceContractAddress, myContract.getContractAddress());
		System.out.println("Test ran successful for contract with address: " + myServiceContractAddress
				+ " and offer with address " + myServiceOfferAddress);
	}

	private void checkServiceOffer(ServiceOffer myLoadedServiceOffer) {
		assertNotNull(myLoadedServiceOffer);
		assertEquals(TEST_OFFER_ID, myLoadedServiceOffer.getId());
		assertEquals(TEST_DESCRIPTION, myLoadedServiceOffer.getDescription());
		assertEquals(TEST_PRICE_PER_MONTH, myLoadedServiceOffer.getPricePerMonth());
		assertEquals(TEST_SERVICE_PROVIDER, myLoadedServiceOffer.getServiceProvider());
		assertEquals(TEST_LOCATION, myLoadedServiceOffer.getLocation());
	}

}
