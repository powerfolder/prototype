package com.scu.backend.service;

import org.web3j.protocol.core.RemoteCall;

public class BlockchainUtil {

	public static <T> T readFromChain(RemoteCall<T> argCall) throws Exception {
		return argCall.send();
	}

}
