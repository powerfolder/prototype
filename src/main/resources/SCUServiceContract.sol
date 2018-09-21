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
	
	string public ID;
	address public clientAddress;
	string public contractIdentity;
	uint256 public beginTime;
	bool public active;

	string public offerID;
	string public serviceProviderName;
	address public serviceProviderAddress;
	uint256 public pricePerMonth;

	constructor(string _ID, SCUServiceOffer _offer, string _contractIdentity) public {
		clientAddress=msg.sender;
		beginTime=now;
		active=true;
		contractIdentity=_contractIdentity;
		ID=_ID;
		// Attributes copied from offer:
		offerID=_offer.ID();
		serviceProviderAddress=_offer.owner();
		serviceProviderName=_offer.serviceProvider();
		pricePerMonth=_offer.pricePerMonth();
	}

}

contract SCUServiceContractCreator {

	SCUServiceContract public serviceContract;

	event SCUServiceContractCreated(SCUServiceContract serviceContract);
    
	function createServiceContract(string _ID, SCUServiceOffer _offer, string _contractIdentity) public {
		serviceContract = new SCUServiceContract(_ID, _offer, _contractIdentity);
		emit SCUServiceContractCreated(serviceContract); 
	}

}
