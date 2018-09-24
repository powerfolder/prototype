pragma solidity ^0.4.24;

// ----------------------------------------------------------------------------
// 'SCU' 'Space.Cloud.Unit.Test v2 Token' token contract
//
// Symbol      : SCT
// Name        : Space.Cloud.Unit.Test.v2
// Total supply: 150,000,000.000000000000000000
// Decimals    : 18
//
// (c) openzepplin / Smart Contract Solutions, Inc 2016. The MIT Licence.
// (c) Max / SCU GmbH 2018. The MIT Licence.
// ----------------------------------------------------------------------------


import "./SCUServiceOffer.sol";

contract SCUServiceContract is Ownable {
	
	address public clientAddress;
	string public contractIdentity;
	uint256 public beginTime;
	bool public active;

	address public serviceOffer;
	address public serviceProviderAddress;
	uint256 public pricePerMonth;
	string public serviceProviderName;

	constructor(address _serviceOffer, string _contractIdentity, address _serviceProviderAddress, string _serviceProviderName, uint256 _pricePerMonth) public {
		clientAddress=msg.sender;
		beginTime=now;
		active=true;
		contractIdentity=_contractIdentity;
		// Attributes copied from offer:
		serviceOffer = _serviceOffer;
		serviceProviderAddress=_serviceProviderAddress;
		serviceProviderName=_serviceProviderName;
		pricePerMonth=_pricePerMonth;
	}

}

contract SCUServiceContractCreator {

	SCUServiceContract public serviceContract;

	event SCUServiceContractCreated(SCUServiceContract serviceContract);
    
	function createServiceContract(address _serviceOffer, string _contractIdentity, address _serviceProviderAddress, string _serviceProviderName, uint256 _pricePerMonth) public {
		serviceContract = new SCUServiceContract(_serviceOffer, _contractIdentity, _serviceProviderAddress, _serviceProviderName, _pricePerMonth);
		emit SCUServiceContractCreated(serviceContract); 
	}

}
