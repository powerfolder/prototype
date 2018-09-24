package com.scu.backend.service;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

public class Web3jConnection {

	private Web3j web3j;
	private Credentials credentials;
	private BigInteger gasPrice;
	private BigInteger gasLimit;

	public Web3jConnection(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		super();
		this.web3j = web3j;
		this.credentials = credentials;
		this.gasPrice = gasPrice;
		this.gasLimit = gasLimit;
	}

	public Web3j getWeb3j() {
		return web3j;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public BigInteger getGasPrice() {
		return gasPrice;
	}

	public BigInteger getGasLimit() {
		return gasLimit;
	}

}
