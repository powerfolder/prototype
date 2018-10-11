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


import "./Ownable.sol";

contract SCUServiceOffer is Ownable {

	string public serviceProvider;
	uint256 public pricePerMonth;
	string public description;
	string public location;
	
	constructor(string _serviceProvider, uint256 _pricePerMonth, string _description, string _location) public {
		owner = msg.sender;
		serviceProvider=_serviceProvider;
		pricePerMonth=_pricePerMonth;
		description=_description;
		location=_location;
	}

}

contract SCUServiceContract is Ownable {
	
	address public clientAddress;
	uint256 public beginTime;
	bool public active;

	address public serviceOffer;
	address public serviceProviderAddress;
	uint256 public pricePerMonth;
	string public serviceProviderName;

	constructor(SCUServiceOffer _serviceOffer) public {
		clientAddress=msg.sender;
		beginTime=now;
		active=true;
		// Attributes copied from offer:
		serviceOffer = _serviceOffer;
		serviceProviderAddress=_serviceOffer.owner();
		serviceProviderName=_serviceOffer.serviceProvider();
		pricePerMonth=_serviceOffer.pricePerMonth();
	}

}
contract SCUMarketplace is Ownable {

	address[] public activeOfferArray;

    event SCUServiceOfferCreated(SCUServiceOffer serviceOffer, address serviceCreator);
	event SCUServiceContractCreated(SCUServiceContract serviceContract);

	constructor() public {
		owner=msg.sender;
	}
	
	function getNrOfActiveOffers() public constant returns(uint count) {
	    return activeOfferArray.length;
	}

	function createServiceOffer(string _serviceProvider, uint256 _pricePerMonth, string _description, string _location) public {
		SCUServiceOffer serviceOffer = new SCUServiceOffer(_serviceProvider, _pricePerMonth, _description, _location);
		activeOfferArray.push(address(serviceOffer));
		emit SCUServiceOfferCreated(serviceOffer, msg.sender);
	}
    
	function createServiceContract(SCUServiceOffer _serviceOffer) public {
		SCUServiceContract serviceContract = new SCUServiceContract(_serviceOffer);
		emit SCUServiceContractCreated(serviceContract); 
	}

}
